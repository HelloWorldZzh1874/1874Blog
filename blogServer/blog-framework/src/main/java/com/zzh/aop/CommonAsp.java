package com.zzh.aop;

import com.zzh.security.filter.MyFilterInvocationSecurityMetadataSource;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zzh
 * @description TODO
 * @date 2022/3/2115:19
 */

@Aspect
@Component
public class CommonAsp {
    @Autowired
    MyFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    /**
     * 当角色信息修改时切入
     */
    @Pointcut("execution(* com.zzh.service.impl..RoleServiceImpl.*(com.zzh.vo.RoleVO))")
    public void pointCutRole() {
    }

    /**
     * 当资源信息修改时切入
     */
    @Pointcut("execution(* com.zzh.service.impl..ResourceServiceImpl.*(com.zzh.vo.ResourceVO))")
    public void pointCutRes() {
    }

    /**
     * 角色信息修改时切入此代码
     * 成功返回时重新加载权限资源
     */
    @AfterReturning("pointCutRole()")
    public void afterCutRole() {
        filterInvocationSecurityMetadataSource.clearDataSource();
    }

    /**
     * 资源信息修改时切入此代码
     * 成功返回时重新加载权限资源
     */
    @AfterReturning("pointCutRes()")
    public void afterCutRes() {
        filterInvocationSecurityMetadataSource.clearDataSource();
    }
}
