package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author zzh
 * @Date 2021/7/30 21:55
 * @Version 0.1
 * @Description 角色列表数据
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色标签
     */
    private String roleLabel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否禁用
     */
    private Integer isDisable;

    /**
     * 资源id列表
     */
    private List<Integer> resourceIdList;

    /**
     * 菜单id列表
     */
    private List<Integer> menuIdList;
}
