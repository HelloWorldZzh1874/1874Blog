package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.BaseController;
import com.zzh.common.base.Result;
import com.zzh.service.TagService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.TagVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zzh.aop.OptTypeConst.REMOVE;
import static com.zzh.aop.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
@RestController
@Api(tags = "标签模块")
@RequestMapping("/tag")
public class TagController extends BaseController {

    @Autowired
    TagService tagService;

    /**
     * @param conditionVO 分页查询信息
     * @return com.zzh.common.base.Result
     * @description 查找后台标签
     * @date 2022/3/13
     */
    @ApiOperation(value = "查找标签")
    @PostMapping("/admin/selectTags")
    public Result findTags(ConditionVO conditionVO) {
        return Result.success(tagService.getBackTags(conditionVO));
    }

    /**
     * @param tagIdList id列表
     * @return com.zzh.common.base.Result
     * @description 根据id删除标签
     * @date 2022/3/13
     */
    @ApiOperation(value = "删除标签")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/deleteTag")
    public Result deleteTag(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return Result.success("操作成功");
    }

    /**
     * @param tagVO
     * @return com.zzh.common.base.Result
     * @description 添加或修改标签
     * @date 2022/3/13
     */
    @ApiOperation(value = "添加或修改标签")
    @PostMapping("/admin/saveOrUpdateTag")
    @OptLog(optType = SAVE_OR_UPDATE)
    public Result saveOrUpdateTag(@Validated @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return Result.success();
    }

    @ApiOperation(value = "查看标签列表")
    @GetMapping(value = "/getList")
    public Result getTagList(){
        return Result.success(tagService.getTagList());
    }
}

