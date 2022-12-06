package com.zzh.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author zzh
 * @description 管理员添加实体
 * @date 2022/5/9 11:27
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddAdminVO {
    /**
     * 注册邮箱，也是用户名
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 角色名
     */
    private String role;
}
