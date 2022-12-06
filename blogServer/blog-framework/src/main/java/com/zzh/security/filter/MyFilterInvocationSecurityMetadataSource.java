package com.zzh.security.filter;

import com.zzh.dto.RoleUrlDTO;
import com.zzh.mapper.ResourceMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author zzh
 * @description 动态权限源数据
 * @date 2022/2/3 12:26
 */

@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ResourceMapper resourceMapper;

    // 角色权限集合
    private static List<RoleUrlDTO> roleUrlList;

    /**
     * 加载元数据
     * PostConstruct 在servlet初始化后执行,只执行一次
     */
    @PostConstruct
    private void loadMetaDateSource(){
        // 从数据库获取权限信息
        roleUrlList = resourceMapper.listRoleUrls();
    }


    /**
     * 清空接口角色信息
     */
    public void clearDataSource() {
        roleUrlList = null;
    }

    /**
     * 为 AccessDecisionManager.decide() 做准备
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 重新加载权限信息/权限信息为空时
        if(CollectionUtils.isEmpty(roleUrlList)){
            this.loadMetaDateSource();
        }

        // 获得请求
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for(RoleUrlDTO roleUrlDTO : roleUrlList) {
            // 如果本次请求路径和方法匹配则封装这个资源需要的角色列表，交给decide鉴权
            if(antPathMatcher.match(roleUrlDTO.getUrl(),request.getRequestURI()) && request.getMethod().equals(roleUrlDTO.getRequestMethod())){
                Set<String> roleList = roleUrlDTO.getRoleList();
                if (CollectionUtils.isEmpty(roleList)) {
                    return SecurityConfig.createList("disable");
                }
                return SecurityConfig.createList(roleList.toArray(new String[]{}));
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return  FilterInvocation.class.isAssignableFrom(clazz);
    }
}
