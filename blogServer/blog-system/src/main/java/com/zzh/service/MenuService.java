package com.zzh.service;

import com.zzh.dto.BackMenuDTO;
import com.zzh.dto.MenuDTO;
import com.zzh.dto.labelOptionDTO;
import com.zzh.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.MenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-06
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据前端用户角色id获取菜单列表
     * @param role 角色
     * @return 返回封装菜单
     */
    List<MenuDTO> getMenuListByRole(String role);

    /**
     * 获取角色菜单选项
     * @return 返回菜单选项列表
     */
    List<labelOptionDTO> getRoleMenuOption();

    /**
     * 查询后台菜单列表
     * @param conditionVO 查询条件
     * @return 菜单封装
     */
    List<BackMenuDTO> getBackMenuList(ConditionVO conditionVO);

    /**
     * 保存或更改菜单
     * @param menuVo 菜单数据
     */
    void updateOrSaveMenu(MenuVO menuVo);

    /**
     * 删除菜单
     * @param id 菜单id
     */
    void deleteMenu(Integer id);
}
