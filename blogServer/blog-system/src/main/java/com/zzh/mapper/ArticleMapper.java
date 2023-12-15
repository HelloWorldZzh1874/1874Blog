package com.zzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.dto.*;
import com.zzh.entity.Article;
import com.zzh.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询后台文章
     * @date 2022/3/9
     * @param condition 条件
     * @return 后台文章集合
     */
    List<ArticleBackDTO> getArticles(@Param("condition") ConditionVO condition);

    /**
     * 查询后台文章总量
     * @date 2022/3/11
     * @param condition 条件
     * @return 文章总量
     */
    Integer countArticles(@Param("condition") ConditionVO condition);

    /**
     * 查询封面列表
     * @date 2022/3/12
     * @param articleIdList 文章id
     * @return 返回封面list
     */
    List<String> selectCoverByList(List<Long> articleIdList);

    /**
     * 查询文章排行
     *
     * @param articleIdList
     * @return
     */
    List<Article> listArticleRank(@Param("articleIdList") List<Integer> articleIdList);

    /**
     * 查询首页文章
     *
     * @param current 当前页码
     * @return 首页文章集合
     */
    List<ArticleHomeDTO> listArticles(Long current);

    /**
     * 根据id查询文章
     *
     * @param articleId 文章id
     * @return 文章
     */
    ArticleDTO getArticleById(Integer articleId);

    /**
     * 查看文章的推荐文章
     * @param articleId 文章id
     * @return 推荐文章
     */
    List<ArticleRecommendDTO> listArticleRecommends(@Param("articleId") Integer articleId);

    /**
     * 根据条件查询文章
     *
     * @param condition 条件
     * @return 文章集合
     */
    List<ArticlePreviewDTO> listArticlesByCondition(@Param("condition") ConditionVO condition);
}
