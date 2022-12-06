package com.zzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.dto.RoleDTO;
import com.zzh.entity.Role;
import com.zzh.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-03-15
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id返回用户角色
     * @param id 用户id
     * @return 用户角色
     */
    String selectRoleByUserId(@Param("id") Long id);

    /**
     * 查询角色列表
     *
     * @param conditionVO 条件
     * @return 角色列表
     */
    List<RoleDTO> listRoles(@Param("conditionVO") ConditionVO conditionVO);

}
