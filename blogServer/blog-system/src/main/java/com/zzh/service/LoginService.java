package com.zzh.service;

import com.zzh.dto.LoginUserDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  登录业务
 * </p>
 *
 * @author zzh
 * @since 2022-01-25
 */

public interface LoginService {

    /**
     * description: 检查用户信息
     * @date 2022/1/25
     * @param username 账号
     * @param password 密码
     * @return 返回正确与否
     */
    boolean checkUser(String username,String password);

    /**
     * description: 退出登录业务
     * @date 2022/1/27
     */
    void logout();

    /**
     * 检查账户状态
     * @param username 用户名
     * @return 返回锁定状态
     */
    Boolean checkAccountLock(String username);

    /**
     * 改变账户锁定状态
     * @param username 账户名
     * @param isNotLock 锁定布尔值
     */
    void changeLock(String username,Boolean isNotLock);

    /**
     * 记住密码的用户取出用户信息
     * @param request 本次请求
     * @return 返回用户信息
     */
    LoginUserDTO tokenLogin(HttpServletRequest request);
}
