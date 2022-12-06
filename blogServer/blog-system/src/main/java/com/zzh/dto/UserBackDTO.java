package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author zzh
 * @Date 2022/3/8 13:14
 * @Version 0.1
 * @Description 后台用户列表
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBackDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户信息id
     */
    private Integer userInfoId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 用户登录ip
     */
    private String ipAddr;

    /**
     * 用户登录地址
     */
    private String ipSource;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;

    /**
     * 用户状态
     */
    private Integer enabled;

    /**
     * 状态
     */
    private Integer status;
}
