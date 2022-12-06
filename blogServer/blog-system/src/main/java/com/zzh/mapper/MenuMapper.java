package com.zzh.mapper;

import com.zzh.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-03-06
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据角色id获取菜单列表
     * @param role 角色
     * @return 返回list菜单
     */
    List<Menu> listMenuByRoleId(@Param("role") String role);

    /**
     * 查找一级菜单的孩子
     * @param id 一级菜单id
     * @return 返回二级菜单id list
     */
    List<Integer> getChildrenId(Integer id);
}
