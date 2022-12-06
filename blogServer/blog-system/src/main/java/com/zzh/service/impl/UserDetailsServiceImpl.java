package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.entity.LoginUser;
import com.zzh.entity.User;
import com.zzh.mapper.RoleMapper;
import com.zzh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author zzh
 * @description 实现security UserDetailsService
 * @date 2022/3/5 13:26
 */

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库查出用户，通过之前的过滤器到达了这个方法，说明该用户认证已经通过
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));

        // 注入用户角色
        String role = roleMapper.selectRoleByUserId(user.getId());
        return LoginUser.builder()
                .user(user)
                .role(role)
                .build();
    }
}
