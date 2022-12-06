package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.common.constant.CommonConst;
import com.zzh.common.utils.RedisUtils;
import com.zzh.dto.*;
import com.zzh.entity.Article;
import com.zzh.entity.UserInfo;
import com.zzh.mapper.ArticleMapper;
import com.zzh.mapper.CategoryMapper;
import com.zzh.mapper.TagMapper;
import com.zzh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zzh.common.constant.CommonConst.FALSE;
import static com.zzh.common.constant.RedisConstant.*;

/**
 * @author zzh
 * @description TODO
 * @date 2022/3/3111:06
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UniqueViewService uniqueViewService;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    TagMapper tagMapper;

    @Override
    public String getAbout() {
        Object value = redisUtils.get(ABOUT_ME);
        return Objects.nonNull(value) ? value.toString() : "";
    }

    @Override
    public String getNotice() {
        Object value = redisUtils.get(NOTICE);
        return Objects.nonNull(value) ? value.toString() : "发布你的第一篇公告吧";
    }

    @Override
    public void updateNotice(String notice) {
        redisUtils.set(NOTICE,notice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAbout(String aboutContent) {
        redisUtils.set(ABOUT_ME,aboutContent);
    }

    @Override
    public BlogBackInfoDTO getBlogBackInfo() {
        // 查询访问量
        Integer viewsCount = (Integer) redisUtils.get(BLOG_VIEWS_COUNT);
        // 查询留言量
        Integer messageCount = messageService.count(null);
        // 查询用户量
        Integer userCount = userService.count(null);
        // 查询文章量
        Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getIsDraft, FALSE));
        // 查询一周用户量
        List<UniqueViewDTO> uniqueViewList = uniqueViewService.listUniqueViews();
        // 查询分类数据
        List<CatalogDTO> categoryDTOList = categoryMapper.listCategoryDTO();
        // 查询redis访问量前五的文章
        Map<String, Integer> articleViewsMap = redisUtils.getMapValue(ARTICLE_VIEWS_COUNT);
        // 将文章进行倒序排序
        List<Integer> articleIdList = Objects.requireNonNull(articleViewsMap).entrySet().stream()
                // 使用反向排序，从大到小
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(item -> Integer.valueOf(item.getKey()))
                .collect(Collectors.toList());
        // 提取前五篇文章,不足5篇全部返回
        int index = Math.min(articleIdList.size(), 5);
        articleIdList = articleIdList.subList(0, index);
        // 文章为空直接返回
        if (articleIdList.isEmpty()) {
            return BlogBackInfoDTO.builder()
                    .viewsCount(viewsCount)
                    .messageCount(messageCount)
                    .userCount(userCount)
                    .articleCount(articleCount)
                    .categoryDTOList(categoryDTOList)
                    .uniqueViewDTOList(uniqueViewList)
                    .build();
        }
        // 查询文章标题
        List<Article> articleList = articleMapper.listArticleRank(articleIdList);
        // 封装浏览量
        List<ArticleRankDTO> articleRankDTOList = articleList.stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleViewsMap.get(article.getId().toString()))
                        .build())
                .collect(Collectors.toList());
        return BlogBackInfoDTO.builder()
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOList(categoryDTOList)
                .uniqueViewDTOList(uniqueViewList)
                .articleRankDTOList(articleRankDTOList)
                .build();
    }

    @Override
    public BlogHomeInfoDTO getHomeBlog() {
        // 查询博主信息
        UserInfo userInfo =userInfoService.getOne(new LambdaQueryWrapper<UserInfo>()
                .select(UserInfo::getAvatar,UserInfo::getNickname,UserInfo::getIntro)
                .eq(UserInfo::getId, CommonConst.BLOGGER_ID));
        //查询文章数量
        Integer articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDraft,FALSE)
                .eq(Article::getIsDelete,FALSE));
        // 查询分类数量
        Integer categoryCount = categoryMapper.selectCount(null);
        // 查询标签数量
        Integer tagCount = tagMapper.selectCount(null);
        // 查询公告
        Object value = redisUtils.get(NOTICE);
        String notice = Objects.nonNull(value)?value.toString():"发布你的第一篇公告!";
        // 查询访问量
        // 使用requireNonNull使Object为null使及时抛出异常而不是执行到使才抛出
        String viewCount;
        try {
            viewCount = Objects.requireNonNull(redisUtils.get(BLOG_VIEWS_COUNT)).toString();
        }catch (NullPointerException e) {
            // 防止redis此变量为空
            viewCount = String.valueOf(0);
        }
        // 封装数据
        return BlogHomeInfoDTO.builder()
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .intro(userInfo.getIntro())
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .notice(notice)
                .viewsCount(viewCount)
                .build();
    }
}
