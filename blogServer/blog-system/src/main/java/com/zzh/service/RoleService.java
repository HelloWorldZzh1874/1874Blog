package com.zzh.service;

import com.github.pagehelper.PageInfo;
import com.zzh.dto.RoleDTO;
import com.zzh.dto.UserRoleDTO;
import com.zzh.entity.ConRoleUser;
import com.zzh.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.RoleVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-15
 */
public interface RoleService extends IService<Role> {
    /**
     * 获取角色信息
     * @return 返回角色list
     */
    List<UserRoleDTO> getUserRoles();

    /**
     * 获取后台角色列表
     * @param conditionVO 分页添加
     * @return 返回分页数据
     */
    PageInfo<RoleDTO> getListRoles(ConditionVO conditionVO);

    /**
     * 保存或更新角色
     * @param roleVO 前端角色信息
     */
    void saveOrUpdateRole(RoleVO roleVO);

    /**
     * 根据id列表删除角色
     * @param roleIdList id
     *
     */
    void deleteRole(List<Integer> roleIdList);
}
