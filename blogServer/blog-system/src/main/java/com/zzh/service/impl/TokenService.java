package com.zzh.service.impl;

import cn.hutool.core.lang.UUID;
import com.zzh.common.constant.RedisConstant;
import com.zzh.common.utils.JwtUtil;
import com.zzh.common.utils.RedisUtils;
import com.zzh.entity.LoginUser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.zzh.common.constant.RedisConstant.LOGIN_TOKEN;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * @author zzh
 * @description 解析与操作token相关
 * @date 2022/3/5 11:30
 */

@Service
public class TokenService {

    @Autowired
    RedisUtils redisUtils;


    /**
     * token密匙
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * token过期时间
     */
    @Value("${token.expireTime}")
    private long expireTime;

    /**
     * 记住我令牌过期时间
     */
    @Value("${token.rememberMe}")
    private long rememberMeExpireTime;


    @Value("${token.refreshTime}")
    private long refreshTime;

    /**
     * 从token中解析用户信息
     *
     * @param token 请求中的token
     * @return 返回用户数据
     */
    public LoginUser parseLoginUser(String token) {
        // 如果token不为空,则可以解析出token主体
        Claims claims = JwtUtil.parseJwt(secret, token);
        // 得到uuid
        String uuid = (String) claims.get(LOGIN_TOKEN);
        // 通过uuid从redis中获取用户信息
        return (LoginUser) redisUtils.get(getTokenKey(uuid));
    }

    /**
     * @return java.lang.String 返回token
     * @description 根据登录用户生成token
     * @date 2022/1/28
     */
    public String createToken(LoginUser user) {
        // 生成唯一uuid
        String uuid = String.valueOf(UUID.fastUUID());
        String token = JwtUtil.createJwt(secret, uuid);
        user.setToken(uuid);
        // 存入redis，设置过期时间
        if (user.getRememberMe()) {
            // 如果记住我则保存七天--redis是用uuid作为键存储，需要用过token获得uuid才能从redis获取用户信息
            redisUtils.set(getTokenKey(uuid), user, rememberMeExpireTime, MINUTES);
        } else {
            // 否则保存1天
            redisUtils.set(getTokenKey(uuid), user, expireTime, MINUTES);
        }
        return token;
    }

    /**
     * @param uuid 解析token得到的uuid
     * @return java.lang.String
     * @description 获取redis中存储的key
     * @date 2022/1/28
     */
    private String getTokenKey(String uuid) {
        return (RedisConstant.TOKEN_PRE + uuid);
    }

    /**
     * 移除相关用户token
     *
     * @param user 用户对象
     */
    public void removeToken(LoginUser user) {
        String uuid = user.getToken();
        // 从redis中移除相关用户信息
        redisUtils.remove(getTokenKey(uuid));
    }

    /**
     * 根据移除相关用户token
     *
     * @param token 用户对象
     */
    public void removeToken(String token) {
        // 如果token不为空,则可以解析出token主体
        Claims claims = JwtUtil.parseJwt(secret, token);
        // 得到uuid
        String uuid = (String) claims.get(LOGIN_TOKEN);
        // 从redis中移除相关用户信息
        redisUtils.remove(getTokenKey(uuid));
    }


    /**
     * 验证token并刷新
     *
     * @param user user
     */
    public void verifyToken(LoginUser user) {
        String uuid = user.getToken();
        // 得到key还有多少秒过期
        Long keyExpire = redisUtils.getKeyExpire(getTokenKey(uuid));
        // 如果剩余时间小于十五分钟，则刷新redis
        if (TimeUnit.SECONDS.toMinutes(keyExpire) < refreshTime) {
            refreshToken(user);
        }
    }

    /**
     * 刷新token有效期
     *
     * @param user redis存储的用户数据
     */
    private void refreshToken(LoginUser user) {
        String key = getTokenKey(user.getToken());
        // 重新存储入redis
        redisUtils.set(key, user, expireTime, MINUTES);
    }
}
