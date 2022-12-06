package com.zzh.security.filter;

import com.zzh.entity.LoginUser;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author zzh
 * @description 自定义权限决策
 * @date 2022/2/317:05
 */

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * @param authentication   方法调用者
     * @param object           被调用的对象
     * @param configAttributes 被调用对像需要的权限
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 如果该对象不存在权限，那么认定为匿名接口，可以访问
        if (null == configAttributes || configAttributes.size() <= 0) {
            return;
        }
        // 不是匿名用户则进行鉴权
        if (!"anonymousUser".equals(authentication.getPrincipal())) {
            // 获取用户权限列表
            String permission = ((LoginUser) authentication.getPrincipal()).getRole();
            // 遍历资源需要的权限
            for (ConfigAttribute item : configAttributes) {
                // 检查账户拥有权限是否包含此权限
                if (permission.equals(item.getAttribute())) {
                    return;
                }
            }
            // 遍历完没有操作权限，则抛出异常
            throw new AccessDeniedException("没有操作权限!");
        }

        // 匿名用户访问需要权限的接口，抛出异常
        throw new AccessDeniedException("请登陆后再操作!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
