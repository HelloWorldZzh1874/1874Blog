package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.BaseController;
import com.zzh.common.base.BaseErrorInfo;
import com.zzh.common.base.Result;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.utils.SecurityUtils;
import com.zzh.dto.MenuDTO;
import com.zzh.entity.LoginUser;
import com.zzh.service.MenuService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.zzh.aop.OptTypeConst.REMOVE;
import static com.zzh.aop.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 * 菜单前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-06
 */
@Api(tags = "菜单模块")
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    MenuService menuService;

    /**
     * @return com.zzh.common.base.Result
     * @description 根据角色返回相关菜单路由数据
     * @date 2022/3/1
     */
    @GetMapping("/admin/getMenus")
    public Result getMenus() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getAuthentication().getPrincipal();
        if (Objects.isNull(loginUser)) {
            throw new CommonWriteException(BaseErrorInfo.SERVER_BUSY);
        }
        List<MenuDTO> menuList = menuService.getMenuListByRole(loginUser.getRole());
        return Result.success(menuList);
    }

    /**
     * @return com.zzh.common.base.Result
     * @description 获取角色菜单选项
     * @date 2022/3/21
     */
    @ApiOperation(value = "获取角色菜单选项")
    @GetMapping("/admin/role/menus")
    public Result getRoleMenuOption() {
        return Result.success(menuService.getRoleMenuOption());
    }

    /**
     * @param conditionVO
     * @return com.zzh.common.base.Result
     * @description 获取后台菜单项
     * @date 2022/3/28
     */
    @ApiOperation(value = "获取后台菜单项")
    @GetMapping("/admin/menuList")
    public Result getMenuList(ConditionVO conditionVO) {
        return Result.success(menuService.getBackMenuList(conditionVO));
    }

    /**
     * @param menuVO
     * @return com.zzh.common.base.Result
     * @description 更新或保存菜单
     * @date 2022/3/28
     */
    @ApiOperation(value = "更新或保存菜单")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/admin/updateOrSaveMenu")
    public Result updateOrSaveMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.updateOrSaveMenu(menuVO);
        return Result.success("修改成功！请退出后重新登录!");
    }

    /**
     * @description 删除菜单
     * @date 2022/3/28
     * @param id
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "删除菜单")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/deleteMenu")
    public Result deleteMenu(@RequestParam Integer id){
        menuService.deleteMenu(id);
        return Result.success("删除成功，请退出后重新登陆!");
    }
}

