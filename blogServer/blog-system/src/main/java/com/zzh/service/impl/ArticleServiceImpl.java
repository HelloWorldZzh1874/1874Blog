package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.aysnc.AsyncManager;
import com.zzh.common.exception.NoDateException;
import com.zzh.common.exception.SaveOrUpdateException;
import com.zzh.common.utils.BeanCopyUtil;
import com.zzh.common.utils.OssUtil;
import com.zzh.common.utils.RedisUtils;
import com.zzh.dto.*;
import com.zzh.entity.*;
import com.zzh.mapper.*;
import com.zzh.service.ArticleService;
import com.zzh.service.ConArticleTagService;
import com.zzh.service.ElasticService;
import com.zzh.utils.SecurityUtils;
import com.zzh.vo.ArticleVO;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.DeleteVO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.zzh.common.constant.CommonConst.FALSE;
import static com.zzh.common.constant.CommonConst.TRUE;
import static com.zzh.common.constant.RedisConstant.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ConArticleTagMapper conArticleTagMapper;

    @Autowired
    private ConArticleTagService conArticleTagService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AsyncManager asyncManager;

    @Autowired
    private EsArticleServiceImpl esArticleService;

    @Override
    public PageInfo<ArticleBackDTO> getArticleBackPageList(ConditionVO conditionVO) {
        // 设置PageHelper的分页信息
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        // 根据条件查询后台的文章以及对应的标签分类
        List<ArticleBackDTO> articleBackDTOList = articleMapper.getArticles(conditionVO);
        // 从redis查询文章点赞量和浏览量的map
        Map<String, Integer> viewsCountMap = redisUtils.getMapValue(ARTICLE_VIEWS_COUNT);
        Map<String, Integer> likeCountMap = redisUtils.getMapValue(ARTICLE_LIKE_COUNT);
        // 分组各文章的点赞量和浏览量
        articleBackDTOList.forEach(item -> {
            item.setViewsCount(Objects.requireNonNull(viewsCountMap).get(item.getId().toString()));
            item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString()));
        });
        PageInfo<ArticleBackDTO> pageInfo = new PageInfo<>(articleBackDTOList);
        pageInfo.setTotal(articleMapper.countArticles(conditionVO));
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        // 构造数据库终端文章实体类
        Article article = Article.builder()
                .id(articleVO.getId())
                .userId(((LoginUser) SecurityUtils.getAuthentication().getPrincipal()).getUser().getId())
                .categoryId(articleVO.getCategoryId())
                .articleCover(articleVO.getArticleCover())
                .articleTitle(articleVO.getArticleTitle())
                .articleContent(articleVO.getArticleContent())
                .createTime(Objects.isNull(articleVO.getId()) ? new Date() : null)
                .updateTime(Objects.nonNull(articleVO.getId()) ? new Date() : null)
                .isTop(articleVO.getIsTop())
                .isDraft(articleVO.getIsDraft())
                .build();
        if (!saveOrUpdate(article)) {
            // 保存失败抛出异常
            throw new SaveOrUpdateException();
        }
        // 编辑文章则删除文章所有标签
        if (Objects.nonNull(articleVO.getId())) {
            conArticleTagMapper.delete(new LambdaQueryWrapper<ConArticleTag>().eq(ConArticleTag::getArticleId, articleVO.getId()));
        }
        // 添加文章标签
        if (!articleVO.getTagIdList().isEmpty()) {
            List<ConArticleTag> articleTagList = articleVO.getTagIdList().stream().map(tagId -> ConArticleTag.builder()
                            .articleId(article.getId())
                            .tagId(tagId)
                            .build())
                    .collect(Collectors.toList());
            conArticleTagService.saveBatch(articleTagList);
        }
        if (!article.getIsDraft().equals(TRUE)) {
            // 将文章添加到搜索库
            EsArticle esArticle = EsArticle.builder()
                    .id(article.getId())
                    .articleContent(articleVO.getArticleContent())
                    .articleTitle(articleVO.getArticleTitle())
                    .isDraft(articleVO.getIsDraft())
                    .build();
            // 存储信息
            esArticleService.saveEntity(esArticle);
        }
    }

    @Override
    public ArticleVO getBackArticleById(Integer articleId) {
        // 查询文章信息
        Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleContent, Article::getArticleCover, Article::getCategoryId, Article::getIsTop, Article::getIsDraft)
                .eq(Article::getId, articleId));
        if(Objects.isNull(article)){
            throw new NoDateException();
        }
        // 查询文章标签
        List<Integer> tagIdList = conArticleTagMapper.selectList(new LambdaQueryWrapper<ConArticleTag>()
                        .select(ConArticleTag::getTagId)
                        .eq(ConArticleTag::getArticleId, article.getId())
                )
                .stream()
                .map(ConArticleTag::getTagId).collect(Collectors.toList());
        // 打包成视图层数据返回
        return ArticleVO.builder()
                .id(article.getId())
                .articleTitle(article.getArticleTitle())
                .articleContent(article.getArticleContent())
                .articleCover(article.getArticleCover())
                .categoryId(article.getCategoryId())
                .isTop(article.getIsTop())
                .tagIdList(tagIdList)
                .isDraft(article.getIsDraft())
                .build();
    }

    @Override
    public ArticleOptionDTO listArticleOption() {
        // 只xu需要查询id和name
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>().select(Tag::getId, Tag::getTagName));
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>().select(Category::getId, Category::getCategoryName));
        // 将标签list和分类list装入DTO返回类型
        return ArticleOptionDTO.builder()
                .categoryList(categoryList)
                .tagList(tagList)
                .build();
    }

    @Override
    public void deleteOrRecArticle(DeleteVO deleteVo) {
        // 修改文章逻辑删除状态
        // 根据条件生成要操作的对象，批量操作使用list
        List<Article> articleList = deleteVo.getIdList().stream().map(id -> Article.builder()
                        .id(id)
                        .isTop(FALSE)
                        .isDelete(deleteVo.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(articleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticles(List<Long> articleIdList) {
        // 删除文章标签
        conArticleTagMapper.delete(new LambdaQueryWrapper<ConArticleTag>().in(ConArticleTag::getArticleId, articleIdList));
        // 删除封面资源
        List<String> coverLists = articleMapper.selectCoverByList(articleIdList);
        if (!coverLists.isEmpty()) {
            OssUtil.deleteList(coverLists);
        }
        // 删除文章
        articleMapper.deleteBatchIds(articleIdList);
        // 删除搜索库文章
        for (Long i : articleIdList) {
            esArticleService.deleteById(i);
        }
    }

    @Override
    public List<ArticleHomeDTO> getHomeArticle(Long current) {
        return articleMapper.listArticles((current - 1) * 10);
    }

    @Override
    public PageInfo<ArchiveDTO> getListArchive(int current) {
        // 构造分页对象 每页十个数据
        PageHelper.startPage(current, 10);
        // 获得分页数据
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getCreateTime)
                .orderByDesc(Article::getCreateTime)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getIsDraft, FALSE));
        // 放到DTO集合
        List<ArchiveDTO> archiveDTOS = BeanCopyUtil.copyList(articles, ArchiveDTO.class);
        PageInfo<ArchiveDTO> pageInfo = new PageInfo<>(archiveDTOS);
        pageInfo.setTotal(articleMapper.selectCount(null));
        return pageInfo;
    }

    @Override
    public ArticleDTO getArticleById(Integer articleId, HttpServletRequest request) {
        // 更新浏览量
        asyncManager.updateArticleViewsCount(articleId, request);
        // 查询id对应的文章
        ArticleDTO article = articleMapper.getArticleById(articleId);
        // 查询上一篇下一篇文章
        Article lastArticle = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getIsDraft, FALSE)
                .lt(Article::getId, articleId)
                .orderByDesc(Article::getId)
                .last("limit 1"));
        Article nextArticle = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getIsDraft, FALSE)
                .gt(Article::getId, articleId)
                .orderByAsc(Article::getId)
                .last("limit 1"));
        // 封装上一篇下一篇
        article.setLastArticle(BeanCopyUtil.copyObject(lastArticle, ArticlePaginationDTO.class));
        article.setNextArticle(BeanCopyUtil.copyObject(nextArticle, ArticlePaginationDTO.class));
        // 查询相关推荐文章
        article.setArticleRecommendList(articleMapper.listArticleRecommends(articleId));
        // 封装点赞量和浏览量
        article.setViewsCount((Integer) redisTemplate.boundHashOps(ARTICLE_VIEWS_COUNT).get(articleId.toString()));
        article.setLikeCount((Integer) redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).get(articleId.toString()));
        return article;
    }

    @Override
    public List<ArticleRecommendDTO> getNewArticle() {
        // 查询id最后五个文章 即最后添加的五个文章
        List<Article> articleList = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover, Article::getCreateTime)
                .eq(Article::getIsDraft, FALSE)
                .eq(Article::getIsDelete, FALSE)
                .orderByDesc(Article::getId)
                .last("limit 5"));
        // 封装成DTO 返回
        return BeanCopyUtil.copyList(articleList, ArticleRecommendDTO.class);
    }

    @Override
    public void saveArticleLike(Integer articleId) {
        LoginUser loginUser = SecurityUtils.getCurUser();
        //查询当前用户点赞过的文章id集合
        Set<Integer> articleLikeSet = (Set<Integer>) redisTemplate.boundHashOps(ARTICLE_USER_LIKE).get(loginUser.getUser().getId().toString());
        // 第一次点赞则创建
        if (CollectionUtils.isEmpty(articleLikeSet)) {
            articleLikeSet = new HashSet<>();
        }
        // 判断是否点赞
        if (articleLikeSet.contains(articleId)) {
            // 点过赞则删除文章id
            articleLikeSet.remove(articleId);
            // 文章点赞量-1
            redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).increment(articleId.toString(), -1);
        } else {
            // 未点赞则增加文章id
            articleLikeSet.add(articleId);
            // 文章点赞量+1
            redisTemplate.boundHashOps(ARTICLE_LIKE_COUNT).increment(articleId.toString(), 1);
        }
        // 保存点赞记录
        redisTemplate.boundHashOps(ARTICLE_USER_LIKE).put(loginUser.getUser().getId().toString(), articleLikeSet);
    }

    @Override
    public ArticlePreviewListDTO listArticlesByCondition(ConditionVO conditionVO) {
        // 转换页码 每页9个
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * 9);
        // 搜索条件对应数据
        List<ArticlePreviewDTO> articlePreviewDTOList = articleMapper.listArticlesByCondition(conditionVO);
        // 搜索条件对应名(标签或分类名)
        String name;
        if (Objects.nonNull(conditionVO.getCategoryId())) {
            name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                            .select(Category::getCategoryName)
                            .eq(Category::getId, conditionVO.getCategoryId()))
                    .getCategoryName();
        } else {
            name = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                            .select(Tag::getTagName)
                            .eq(Tag::getId, conditionVO.getTagId()))
                    .getTagName();
        }
        return ArticlePreviewListDTO.builder()
                .articlePreviewDTOList(articlePreviewDTOList)
                .name(name)
                .build();
    }

    @Override
    public List<EsArticle> listSearchArticles(ConditionVO conditionVO) {
          /*
        buildQuery()根据条件构造搜索语句
         */
        /*
        searchArticle()真正的搜素逻辑
         */
        return searchArticle(buildQuery(conditionVO));
    }

    /**
     * 搜索文章构造
     *
     * @param condition 条件
     * @return es条件构造器
     */
    private NativeSearchQueryBuilder buildQuery(ConditionVO condition) {
        // 条件构造器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 根据关键词搜索文章标题或内容
        if (Objects.nonNull(condition.getKeywords())) {
            boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("articleTitle", condition.getKeywords()))
                            .should(QueryBuilders.matchQuery("articleContent", condition.getKeywords())))
                    .must(QueryBuilders.termQuery("isDraft", FALSE));
        }
        // 查询
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        return nativeSearchQueryBuilder;
    }

    /**
     * 文章搜索结果
     *
     * @param nativeSearchQueryBuilder es条件构造器
     * @return 搜索结果
     */
    private List<EsArticle> searchArticle(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        // 添加文章标题高亮
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("articleTitle");
        titleField.preTags("<span style='color:#f47466'>");
        titleField.postTags("</span>");
        // 添加文章内容高亮
        HighlightBuilder.Field contentField = new HighlightBuilder.Field("articleContent");
        contentField.preTags("<span style='color:#f47466'>");
        contentField.postTags("</span>");
        contentField.fragmentSize(200);
        nativeSearchQueryBuilder.withHighlightFields(titleField, contentField);
        // 搜索
        SearchHits<EsArticle> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), EsArticle.class);
        return search.getSearchHits().stream().map(hit -> {
            EsArticle article = hit.getContent();
            // 获取文章标题高亮数据
            List<String> titleHighLightList = hit.getHighlightFields().get("articleTitle");
            if (CollectionUtils.isNotEmpty(titleHighLightList)) {
                // 替换标题数据
                article.setArticleTitle(titleHighLightList.get(0));
            }
            // 获取文章内容高亮数据
            List<String> contentHighLightList = hit.getHighlightFields().get("articleContent");
            if (CollectionUtils.isNotEmpty(contentHighLightList)) {
                // 替换内容数据
                article.setArticleContent(contentHighLightList.get(0));
            }
            return article;
        }).collect(Collectors.toList());
    }
}
