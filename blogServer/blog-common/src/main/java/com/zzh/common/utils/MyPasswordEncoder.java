package com.zzh.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author zzh
 * @description 我的密码加密工具
 * @date 2022/2/117:52
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 使用户密码加密
     *
     * @param rawPassword 明文
     * @return 返回密文
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * 明文与密文是否匹配
     *
     * @param rawPassword     明文
     * @param encodedPassword 密文(数据库存储的密码)
     * @return 返回true或false
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
