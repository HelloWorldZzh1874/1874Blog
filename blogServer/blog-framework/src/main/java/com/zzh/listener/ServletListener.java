package com.zzh.listener;

import com.zzh.common.utils.IpUtils;
import com.zzh.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;

import static com.zzh.common.constant.RedisConstant.BLOG_VIEWS_COUNT;
import static com.zzh.common.constant.RedisConstant.IP_SET;

/**
 * @author zzh
 * @description 请求监听，统计访问人数
 * @date 2022/4/19 22:59
 */
@Component
public class ServletListener implements ServletRequestListener {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        Set<Object> ip = redisUtils.getMembers(IP_SET);
        if (Objects.isNull(ip)) {
            return;
        }
        // 判断当前ip是否访问，增加访问量
        String ipAddr = IpUtils.getIpAddr(request);
        if (!ip.contains(ipAddr)) {
            // 将ip存入redis，统计每日用户量
            redisUtils.add(IP_SET, ipAddr);
            redisTemplate.boundValueOps(BLOG_VIEWS_COUNT).increment(1);
        }
    }
}
