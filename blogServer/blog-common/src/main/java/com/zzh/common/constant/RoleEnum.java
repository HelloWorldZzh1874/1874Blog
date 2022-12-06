package com.zzh.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色常量
 * @author zzh
 */

@Getter
@AllArgsConstructor
public enum RoleEnum {
    /**
     * 管理员
     */
    ADMIN(1, "ROLE_ADMIN", "管理员"),
    /**
     * 普通用户
     */
    USER(2, "ROLE_USER", "用户"),
    /**
     * 超级管理员
     */
    TEST(4, "ROLE_ROOT", "超级管理员");

    /**
     * 角色id
     */
    private final Integer roleId;

    /**
     * 描述
     */
    private final String name;

    /**
     * 权限标签
     */
    private final String label;
}
