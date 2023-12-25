package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.Result;
import com.zzh.service.RoleService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zzh.common.base.BaseController;

import javax.validation.Valid;

import java.util.List;

import static com.zzh.aop.OptTypeConst.REMOVE;
import static com.zzh.aop.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色模块")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * @description 获取角色信息
     * @date 2022/3/9
     */
    @ApiOperation(value = "获取用户角色选项")
    @GetMapping("/admin/roleList")
    public Result getUserRoles() {
        return Result.success(roleService.getUserRoles());
    }


    /**
     * @description 获取后台角色列表
     * @date 2022/3/18
     * @param conditionVO 分页条件
     * @return 返回角色数据
     */
    @ApiOperation(value = "获取角色列表")
    @PostMapping("/admin/roles")
    public Result getRoleList(ConditionVO conditionVO){
        return Result.success(roleService.getListRoles(conditionVO));
    }


    /**
     * @description 保存或更新角色
     * @date 2022/3/21
     * @param roleVO 角色信息
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "保存或更新角色")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/admin/saveOrUpdateRole")
    public Result saveOrUpdateRole(@RequestBody @Valid RoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return Result.success();
    }

    /**
     * @description 根据id列表删除角色
     * @date 222/3/22
     * @param roleIdList id
     * @return
     */
    @ApiOperation(value = "删除角色")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/delete")
    public Result deleteRole(@RequestBody List<Integer> roleIdList){
        roleService.deleteRole(roleIdList);
        return Result.success();
    }
}

