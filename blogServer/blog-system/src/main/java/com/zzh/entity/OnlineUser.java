package com.zzh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzh
 * @description 登录用户
 * @date 2022/2/520:36
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlineUser {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nikeName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 登录时间
     */
    private Date lastLogin;

    /**
     * 登陆过期时间：取决于是否记住密码
     */
    private Date logoutDate;

    /**
     * ip
     */
    private String ipAddr;

    /**
     * 登录地址
     */
    private String ipSource;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * token
     */
    private String token;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 邮箱
     */
    private String email;
}
