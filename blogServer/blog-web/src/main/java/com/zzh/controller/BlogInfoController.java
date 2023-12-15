package com.zzh.controller;

import com.zzh.aop.OptLog;
import com.zzh.common.base.Result;
import com.zzh.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zzh.aop.OptTypeConst.UPDATE;

/**
 * @author zzh
 * @description 博客信息相关
 * @date 2022/3/31 11:04
 */
@RestController
@Api(tags = "博客信息模块")
public class BlogInfoController {

    @Autowired
    private BlogService blogService;

    /**
     * @description 查看关于我信息
     * @date 2022/3/30
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public Result getAbout() {
        return Result.success().setData(blogService.getAbout());
    }

    /**
     * @description 修改关于我信息
     * @date 2022/3/29
     * @param aboutContent 信息
     * @return com.zzh.common.base.Result
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改关于我信息")
    @PutMapping("/admin/about")
    public Result updateAbout(String aboutContent) {
        blogService.updateAbout(aboutContent);
        return Result.success("修改成功");
    }

    /**
     * @description 获得公告
     * @date 2022/4/5
     * @param
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "获得公告")
    @GetMapping("/admin/notice")
    public Result getNotice() {
        Result result = Result.success();
        result.setData(blogService.getNotice());
        return result;
    }

    /**
     * @description 修改公告
     * @date 2022/4/5
     * @param notice 公告内容
     * @return com.zzh.common.base.Result
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改公告")
    @PutMapping("/admin/notice")
    public Result updateNotice(String notice) {
        blogService.updateNotice(notice);
        return Result.success("成功!");
    }

    /**
     * @description 查看首页信息，包括echarts数据
     * @date 2022/4/6
     * @param
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查看后台信息")
    @GetMapping("/admin/index")
    public Result getBlogBackInfo() {
        return Result.success(blogService.getBlogBackInfo());
    }

    @ApiOperation(value = "查看博客首页信息")
    @GetMapping("/")
    public Result getBlogHomeInfo() {
        return Result.success(blogService.getHomeBlog());
    }
}
