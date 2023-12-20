package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.BaseController;
import com.zzh.common.base.FilePathEnum;
import com.zzh.common.base.Result;
import com.zzh.common.constant.HttpStatus;
import com.zzh.common.utils.OssUtil;
import com.zzh.entity.Article;
import com.zzh.service.ArticleService;
import com.zzh.service.impl.EsArticleServiceImpl;
import com.zzh.vo.ArticleVO;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.DeleteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.zzh.aop.OptTypeConst.REMOVE;
import static com.zzh.aop.OptTypeConst.UPDATE;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
@RestController
@Api(tags = "文章模块")
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EsArticleServiceImpl esArticleService;

    /**
     * @param file 图片文件
     * @return 返回上传后的全路径
     * @description 上传文章封面到存储服务器
     * @date 2022/3/9
     */
    @ApiOperation(value = "上传文章封面到cos")
    @PostMapping("/uploadCover")
    @Transactional(rollbackFor = Exception.class)
    public Result saveArticleCover(MultipartFile file) {
        String fullUrl = OssUtil.upload(file, FilePathEnum.ARTICLE_COVER.getPath());
        return Result.success("", fullUrl);
    }

    /**
     * @param file 图片文件
     * @return 返回图片全路径
     * @description 上传图片到oss
     * @date 2022/3/9
     */
    @ApiOperation(value = "上传文章图片到Cos")
    @PostMapping("/uploadImg")
    public Result saveArticleImages(MultipartFile file) {
        String fullUrl = OssUtil.upload(file, FilePathEnum.ARTICLE.getPath());
        return Result.success("", fullUrl);
    }

    /**
     * @param key 突破全路径
     * @description 从oss删除图片
     * @date 2022/3/10
     */
    @ApiOperation(value = "删除cos图片")
    @PostMapping("/deleteImg")
    public Result deleteImg(String key) {
        try {
            OssUtil.delete(key);
            return Result.success("操作成功!");
        } catch (Exception e) {
            if (Objects.isNull(key) || key.isEmpty()) {
                return Result.error(HttpStatus.BAD_REQUEST, "文件参数为空!");
            }
            return Result.error("服务器异常!");
        }
    }

    /**
     * @param articleVO 前端文章数据
     * @return com.zzh.common.base.Result
     * @description 添加或修改文章
     * @date 2022/3/10
     */
    @ApiOperation(value = "添加或修改文章")
    @PostMapping("/saveOrUpdateArticle")
    public Result saveAndUpdateArticle(@Valid @RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return Result.success("操作成功!");
    }


    /**
     * @param conditionVO 分页条件等
     * @return com.zzh.common.base.Result
     * @description 查询后台文章
     * @date 2022/3/11
     */
    @ApiOperation(value = "查询后台文章")
    @PostMapping("/admin/getArticles")
    public Result listArticleBackDTO(ConditionVO conditionVO) {
        return Result.success(articleService.getArticleBackPageList(conditionVO));
    }

    /**
     * @param articleId 文章id
     * @return 返回文章数据
     * @description 后台通过id查看后台文章
     * @date 2022/3/9
     */
    @ApiOperation(value = "后台通过id查看后台文章")
    @GetMapping("/admin/getArticlesById/{articleId}")
    public Result selectArticlesById(@PathVariable("articleId") Integer articleId) {
        return Result.success(articleService.getBackArticleById(articleId));
    }

    /**
     * @return com.zzh.common.base.Result
     * @description 获取文章分类和标签选项
     * @date 2022/3/11
     */
    @ApiOperation(value = "获取文章选项")
    @GetMapping("/admin/options")
    public Result getArticleOptions() {
        return Result.success(articleService.listArticleOption());
    }

    /**
     * @param articleId 文章id
     * @param isTop     顶置状态
     * @description 更改文章顶置状态
     * @date 2022/3/11
     */
    @ApiOperation(value = "更改文章顶置状态")
    @OptLog(optType = UPDATE)
    @PutMapping("/admin/top/{articleId}")
    public Result updateArticleTop(@PathVariable("articleId") Long articleId, Integer isTop) {
        if (isTop != 0 && isTop != 1) {
            return Result.error(HttpStatus.BAD_REQUEST, "参数错误！！");
        } else if (articleService.getById(articleId) == null) {
            return Result.error(HttpStatus.BAD_REQUEST, "文章id错误！");
        }
        Article article = Article.builder()
                .id(articleId)
                .isTop(isTop)
                .build();
        articleService.updateById(article);
        return Result.success();
    }

    /**
     * @param deleteVo 接收的删除条件
     * @description 文章逻辑删除或恢复
     * @date 2022/3/11
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "文章逻辑删除或恢复")
    @PutMapping("/admin/deleteOrRecArticle")
    public Result deleteOrRecArticle(@Valid DeleteVO deleteVo) {
        articleService.deleteOrRecArticle(deleteVo);
        return Result.success("操作成功!");
    }

    /**
     * 文章物理删除
     *
     * @param articleIdList 要删除的文章id，支持批量删除
     */
    @ApiOperation(value = "文章物理删除")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/deleteArticles")
    public Result deleteArticles(@RequestBody List<Long> articleIdList) {
        articleService.deleteArticles(articleIdList);
        return Result.success("操作成功!");
    }

    @ApiOperation(value = "查看首页文章")
    @GetMapping("/homeData")
    public Result listArticles(Long current) {
        return Result.success(articleService.getHomeArticle(current));
    }

    /**
     * 查看归档信息
     *
     * @param current 当前页
     * @return PageDTO 返回分页数据
     */
    @ApiOperation(value = "查看归档信息")
    @GetMapping("/archives")
    public Result getArchive(int current) {
        return Result.success(articleService.getListArchive(current));
    }

    /**
     * @param articleId 文章id
     * @return com.zzh.common.base.Result
     * @description 根据id查看文章
     * @date 2022/4/17
     */
    @ApiOperation(value = "根据id查看文章")
    @GetMapping("/{articleId}")
    public Result getArticleById(@PathVariable("articleId") Integer articleId, HttpServletRequest request) {
        return Result.success(articleService.getArticleById(articleId, request));
    }

    /**
     * @param
     * @return com.zzh.common.base.Result
     * @description 查看最新文章
     * @date 2022/4/17
     */
    @ApiOperation(value = "查看最新文章")
    @GetMapping("/newest")
    public Result getNewArticle() {
        return Result.success(articleService.getNewArticle());
    }

    /**
     * @param articleId 文章id
     * @return com.zzh.common.base.Result
     * @description 点赞文章
     * @date 2022/4/20
     */
    @ApiOperation(value = "点赞文章")
    @PutMapping("/like")
    public Result saveArticleLike(Integer articleId) {
        articleService.saveArticleLike(articleId);
        return Result.success();
    }

    /**
     * @param categoryId
     * @param current
     * @return com.zzh.common.base.Result
     * @description 查看分类下对应的文章
     * @date 2022/4/20
     */
    @ApiOperation(value = "查看分类下对应的文章")
    @GetMapping("/category/{categoryId}")
    public Result listArticlesByCategoryId(@PathVariable("categoryId") Integer categoryId, Integer current) {
        if (current <= 0) {
            return Result.error(HttpStatus.BAD_REQUEST, "分页参数有误！");
        }
        // 构造查询条件
        ConditionVO conditionVO = ConditionVO.builder()
                .categoryId(categoryId)
                .current(current)
                .build();
        return Result.success(articleService.listArticlesByCondition(conditionVO));
    }

    /**
     * @param tagId
     * @param current
     * @return com.zzh.common.base.Result
     * @description 查看标签下对应的文章
     * @date 2022/4/20
     */
    @ApiOperation(value = "查看标签下对应的文章")
    @GetMapping("/tag/{tagId}")
    public Result listArticlesByTagId(@PathVariable("tagId") Integer tagId, Integer current) {
        if (current <= 0) {
            return Result.error(HttpStatus.BAD_REQUEST, "分页参数有误！");
        }
        // 构造查询条件
        ConditionVO conditionVO = ConditionVO.builder()
                .tagId(tagId)
                .current(current)
                .build();
        return Result.success(articleService.listArticlesByCondition(conditionVO));
    }

    /**
     * @param conditionVO 搜索关键字
     * @return
     * @description es关键字搜索文章
     * @date 2022/4/23
     */
    @ApiOperation(value = "es关键字搜索文章")
    @GetMapping("/search")
    public Result searchArticle(ConditionVO conditionVO) {
        return Result.success(esArticleService.searchHighlights(conditionVO));
    }


}
