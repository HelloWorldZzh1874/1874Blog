package com.zzh.utils;

import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.RefuseAnonymousUserException;
import com.zzh.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.zzh.common.constant.CommonConst.ANONYMOUS_USER;

/**
 * @author zzh
 * @description Security相关工具
 * @date 2022/1/2821:38
 */
public class SecurityUtils {

    /**
     * 获取上下文的Authentication对象
     * @return Authentication
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获得当前登录用户
     * @return
     */
    public static LoginUser getCurUser(){
        // 从 SecurityContextHolder 获得当前登录对象
        Authentication authentication = SecurityUtils.getAuthentication();
        if (ANONYMOUS_USER.equals(authentication.getPrincipal())) {
            // 匿名用户无法访问此接口!
            throw new RefuseAnonymousUserException(HttpStatus.UNAUTHORIZED, "需要登陆!");
        }
        return  (LoginUser) authentication.getPrincipal();
    }
}
