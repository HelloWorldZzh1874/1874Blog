package com.zzh.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author zzh
 * @description 实现Spring security  UserDetails接口
 * @date 2022/1/2514:06
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUser implements UserDetails {

    /**
     * 用户信息
     */
    private User user;

    /**
     * 用户token
     */
    private String token;

    /**
     * 用户角色列表
     */
    private String role;

    /**
     * 记住我
     */
    private Boolean rememberMe;

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        return user.getUsername().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "LoginUser(user=" + this.getUser() + ", token=" + this.getToken() + ", roles=" + this.getRole() + ")";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return user.getNotLock();
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
