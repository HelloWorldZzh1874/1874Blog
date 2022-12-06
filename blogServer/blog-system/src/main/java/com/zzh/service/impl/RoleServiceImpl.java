package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.common.base.BaseErrorInfo;
import com.zzh.common.constant.CommonConst;
import com.zzh.common.exception.SaveOrUpdateException;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.dto.RoleDTO;
import com.zzh.dto.UserRoleDTO;
import com.zzh.entity.ConRoleMenu;
import com.zzh.entity.ConRoleResource;
import com.zzh.entity.ConRoleUser;
import com.zzh.entity.Role;
import com.zzh.mapper.RoleMapper;
import com.zzh.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.AutomapConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zzh.common.constant.CommonConst.EDIT_MENU;
import static com.zzh.common.constant.CommonConst.EDIT_RES;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    ConRoleResourceService conRoleResourceService;
    @Autowired
    ConRoleMenuService conRoleMenuService;
    @Autowired
    ConRoleUserService conRoleUserService;

    @Override
    public List<UserRoleDTO> getUserRoles() {
        // 查询角色列表
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>()
                .select(Role::getId, Role::getRoleLabel));
        List<UserRoleDTO> userRoleDTOS = new ArrayList<>();
        for (Role role : roleList) {
            userRoleDTOS.add(UserRoleDTO.builder().id(role.getId()).roleName(role.getRoleLabel()).build());
        }
        return userRoleDTOS;
    }

    @Override
    public PageInfo<RoleDTO> getListRoles(ConditionVO conditionVO) {
        // 转换页码
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        // 查询角色列表
        List<RoleDTO> roleDTOS = roleMapper.listRoles(conditionVO);
        PageInfo<RoleDTO> pageInfo = new PageInfo<>(roleDTOS);
        // 查询总量
        Integer count = roleMapper.selectCount(new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Role::getRoleName, conditionVO.getKeywords()));
        pageInfo.setTotal(count);
        return pageInfo;
    }

    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        // 判断角色名重复
        Integer count = roleMapper.selectCount(new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleLabel, roleVO.getRoleLabel()));
        // 如果前端没有传id则此操作是保存(新增)操作。不允许角色名重复
        // 如果为传了id则此操作为更新
        if (count > 0 && Objects.isNull(roleVO.getId())) {
            throw new SaveOrUpdateException("角色名已存在");
        }
        // 保存更新或新建信息至对象
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .roleLabel(roleVO.getRoleLabel())
                .createTime(Objects.isNull(roleVO.getId()) ? new Date() : null)
                .updateTime(Objects.nonNull(roleVO.getId()) ? new Date() : null)
                .isDisable(CommonConst.FALSE)
                .build();
        // 更新至数据库
        this.saveOrUpdate(role);
        // 更新资源列表
        if (CollectionUtils.isNotEmpty(roleVO.getResourceIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                conRoleResourceService.remove(new LambdaQueryWrapper<ConRoleResource>().eq(ConRoleResource::getRoleId, roleVO.getId()));
            }
            List<ConRoleResource> roleResourceList = roleVO.getResourceIdList().stream()
                    .map(resourceId -> ConRoleResource.builder()
                            .roleId(role.getId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            conRoleResourceService.saveBatch(roleResourceList);
        }else if(Objects.nonNull(roleVO.getType()) && roleVO.getType().equals(EDIT_RES)){
            // 如果资源为空且操作类型为修改资源,则删除当前角色的资源权限
            if (Objects.nonNull(roleVO.getId())) {
                conRoleResourceService.remove(new LambdaQueryWrapper<ConRoleResource>().eq(ConRoleResource::getRoleId, roleVO.getId()));
            }
        }
        // 更新菜单列表
        if (CollectionUtils.isNotEmpty(roleVO.getMenuIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                conRoleMenuService.remove(new LambdaQueryWrapper<ConRoleMenu>().eq(ConRoleMenu::getRoleId, roleVO.getId()));
            }
            List<ConRoleMenu> roleMenuList = roleVO.getMenuIdList().stream()
                    .map(menuId -> ConRoleMenu.builder()
                            .roleId(role.getId())
                            .menuId(menuId)
                            .build())
                    .collect(Collectors.toList());
            conRoleMenuService.saveBatch(roleMenuList);
        }else if(roleVO.getType().equals(EDIT_MENU)){
            // 如果菜单为空且操作类型为修改菜单,则删除当前角色的菜单权限
            if (Objects.nonNull(roleVO.getId())) {
                conRoleMenuService.remove(new LambdaQueryWrapper<ConRoleMenu>().eq(ConRoleMenu::getRoleId, roleVO.getId()));
            }
        }
    }

    @Override
    public void deleteRole(List<Integer> roleIdList) {
        // 判断角色下是否有用户
        Integer count = conRoleUserService.count(new LambdaQueryWrapper<ConRoleUser>()
                .in(ConRoleUser::getRoleId, roleIdList));
        if (count > 0) {
            throw new SaveOrUpdateException("该角色还存在用户");
        }
        // 删除带单表对应信息
        conRoleMenuService.remove(new LambdaQueryWrapper<ConRoleMenu>()
                .in(ConRoleMenu::getRoleId,roleIdList));
        // 删除资源对应信息
        conRoleResourceService.remove(new LambdaQueryWrapper<ConRoleResource>()
                .in(ConRoleResource::getRoleId,roleIdList));
        // 删除角色
        roleMapper.deleteBatchIds(roleIdList);
    }
}
