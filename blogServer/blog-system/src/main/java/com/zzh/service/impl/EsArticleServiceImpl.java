package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zzh.common.exception.baseException.MyException;
import com.zzh.entity.Article;
import com.zzh.entity.EsArticle;
import com.zzh.mapper.EsArticleMapper;
import com.zzh.service.ArticleService;
import com.zzh.service.ElasticService;
import com.zzh.vo.ConditionVO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zzh.common.constant.CommonConst.FALSE;

/**
 * 实现es查询相关方法
 *
 * @author 37436
 */
@Service("EsArticleService")
public class EsArticleServiceImpl implements ElasticService<EsArticle> {
    @Autowired
    private EsArticleMapper esArticleMapper;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void saveEntity(EsArticle entity) {
        try {
            esArticleMapper.save(entity);
        } catch (Exception e) {
            if (!e.getMessage().contains("200 OK") && !e.getMessage().contains("Unable to parse response body for Response")) {
                throw new MyException(e.getMessage());
            }
        }
    }

    @Override
    public void saveBatch(List<EsArticle> list) {
        try {
            esArticleMapper.saveAll(list);
        } catch (Exception e) {
            if (!e.getMessage().contains("200 OK")) {
                throw new MyException(e.getMessage());
            }
        }
    }

    @Override
    public List<EsArticle> searchHighlights(ConditionVO conditionVO) {
        // 条件构造器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 根据关键词搜索文章标题或内容
        if (Objects.nonNull(conditionVO.getKeywords())) {
            boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("articleTitle", conditionVO.getKeywords()))
                            .should(QueryBuilders.matchQuery("articleContent", conditionVO.getKeywords())))
                    .must(QueryBuilders.termQuery("isDraft", FALSE)).must(QueryBuilders.termQuery("isLogicDel", FALSE));
        }
        // 查询
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
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

    @Override
    public EsArticle searchById(Long id) {
        return esArticleMapper.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        try {
            esArticleMapper.deleteById(id);
        } catch (Exception e) {
            if (!e.getMessage().contains("200 OK")) {
                throw new MyException(e.getMessage());
            }
        }
    }

    @Override
    public List<EsArticle> findAll() {
        List<EsArticle> collect = new ArrayList<>();
        Iterable<EsArticle> all = esArticleMapper.findAll();
        all.forEach(collect::add);
        return collect;
    }

    @Override
    public void initArticleData() {
        List<Article> articleList = articleService.list();
        saveBatch(articleList.stream().map(
                        item -> EsArticle.builder().
                                id(item.getId()).
                                articleContent(item.getArticleContent()).
                                articleTitle(item.getArticleTitle()).
                                isDraft(item.getIsDraft()).
                                isLogicDel(item.getIsDelete()).
                                build())
                .collect(Collectors.toList()));
    }

    @Override
    public boolean update(EsArticle esArticle) {
        Document document = Document.create();
        Class<? extends EsArticle> type = esArticle.getClass();
        Field[] fields =  type.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(esArticle);
                if (value != null) {
                    document.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        UpdateQuery updateQuery = UpdateQuery.builder(Long.toString(esArticle.getId()))
                //不加默认false。true表示更新时不存在就插入
                .withDocAsUpsert(false)
                .withDocument(document)
                .build();
        try {
            elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("article_index"));
        } catch (Exception e) {
            if (!e.getMessage().contains("200 OK") && !e.getMessage().contains("Unable to parse response body for Response")) {
                throw new MyException(e.getMessage());
            }
        }
        return true;
    }
}
