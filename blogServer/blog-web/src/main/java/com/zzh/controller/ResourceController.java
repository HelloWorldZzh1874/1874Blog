package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.Result;
import com.zzh.service.ResourceService;
import com.zzh.vo.ResourceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.zzh.common.base.BaseController;

import static com.zzh.aop.OptTypeConst.REMOVE;
import static com.zzh.aop.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
@Api(tags = "资源模块")
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    ResourceService resourceService;

    /**
     * @description 查看角色资源选项
     * @date 2022/3/21
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("/admin/resources")
    public Result listResourceOption() {
        return Result.success(resourceService.listResourceOption());
    }

    /**
     * @description 后台查看资源列表
     * @date 2022/3/28
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resourceList")
    public Result listResources() {
        return Result.success(resourceService.listResources());
    }

    /**
     * @description 修改或更新资源
     * @param resourceVO 资源数据
     * @return
     */
    @ApiOperation(value = "添加或更新资源")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/admin/addOrUpdate")
    public Result addOrUpdateResource(@Validated @RequestBody ResourceVO resourceVO){
        resourceService.addOrUpdate(resourceVO);
        return Result.success("操作成功!");
    }

    /**
     * @description 删除资源
     * @date 2022/3/29
     * @param id 资源或模块id
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "删除资源")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/delete")
    public Result deleteResource(@RequestParam Integer id){
        resourceService.deleteResource(id);
        return Result.success();
    }
}

