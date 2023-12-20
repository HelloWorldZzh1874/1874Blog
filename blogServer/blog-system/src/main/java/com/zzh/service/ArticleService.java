package com.zzh.service;

import com.github.pagehelper.PageInfo;
import com.zzh.dto.*;
import com.zzh.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.entity.EsArticle;
import com.zzh.vo.ArticleVO;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.DeleteVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  @description 文章业务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
public interface ArticleService extends IService<Article> {
    /**
     * 后台管理获得文章分页列表
     * @date 2022/3/10
     * @param conditionVO 分页信息
     * @return PageHelper的返回分页信息
     */
    PageInfo<ArticleBackDTO> getArticleBackPageList(ConditionVO conditionVO);

    /**
     * 保存或更新文章
     * @date 2022/3/10
     * @param articleVO 前端文章数据对象
     */
    void saveOrUpdateArticle(ArticleVO articleVO);

    /**
     * 后台通过id查找文章
     * @date 2022/3/10
     * @param articleId 文章id
     * @return  返回视图数据
     */
    ArticleVO getBackArticleById(Integer articleId);

    /**
     * 查找菜单标签和分类
     * @date 2022/3/10
     * @return 返回分类和标签列表
     */
    ArticleOptionDTO listArticleOption();

    /**
     * 文章逻辑删除
     * @date 2022/3/9
     * @param deleteVo 删除的id或id列表
     */
    void deleteOrRecArticle(DeleteVO deleteVo);

    /**
     * 物理删除文章
     * @date 2022/3/12
     * @param articleIdList 文章id列表
     */
    void deleteArticles(List<Long> articleIdList);

    /**
     * 获取首页文章
     * @param current 当前页码
     * @return 文章列表
     */
    List<ArticleHomeDTO> getHomeArticle(Long current);

    /**
     * 获得分页归档信息
     * @param current 当前页
     * @return
     */
    PageInfo<ArchiveDTO> getListArchive(int current);

    /**
     * 根据id查询特定文章
     * @param articleId 文章id
     * @return
     */
    ArticleDTO getArticleById(Integer articleId, HttpServletRequest request);

    /**
     * 查看最新文章
     * @return
     */
    List<ArticleRecommendDTO> getNewArticle();

    /**
     * 文章点赞 给文章点赞
     * @param articleId
     */
    void saveArticleLike(Integer articleId);

    /**
     * 根据条件查询文章列表--分类或标签
     * @param conditionVO 分类、标签、分页信息
     * @return
     */
    ArticlePreviewListDTO listArticlesByCondition(ConditionVO conditionVO);

}
