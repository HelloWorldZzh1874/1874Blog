package com.zzh.security.filter;

import cn.hutool.core.date.DateUtil;
import com.zzh.aysnc.AsyncManager;
import com.zzh.common.OnlineUserMap;
import com.zzh.common.constant.CommonConst;
import com.zzh.common.exception.MyAuthenticationException;
import com.zzh.common.exception.PasswordNotMatchException;
import com.zzh.common.utils.IpUtils;
import com.zzh.common.utils.RedisUtils;
import com.zzh.dto.EmailDTO;
import com.zzh.entity.OnlineUser;
import com.zzh.mapper.UserMapper;
import com.zzh.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.zzh.common.constant.CommonConst.PASSWD_URL;
import static com.zzh.common.constant.CommonConst.WRONG_PWD;
import static com.zzh.common.constant.RedisConstant.LOGIN_FAILURE_PRE;

/**
 * @author zzh
 * @description TODO
 * @date 2022/3/5 11:11
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    OnlineUserMap onlineUserMap;

    @Autowired
    AsyncManager asyncManager;

    @Autowired
    RedisUtils redisUtil;

    @Autowired
    LoginService loginService;

    @Autowired
    UserMapper userMapper;

    /**
     * 只允许post请求
     */
    private static final boolean POST_ONLY = true;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 判断是否是post请求
        if (POST_ONLY && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 从请求中获取账号密码
        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY).trim();
        String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY).trim();

        // 判断当前用户是否登录
        OnlineUser onlineUser = onlineUserMap.get(username);

        // 如果当前用户已登录
        if (Objects.nonNull(onlineUser) && DateUtil.compare(DateUtil.date(),onlineUser.getLogoutDate()) <= 0 ) {
            // 将登陆ip和相关信息发送给用户邮箱
            String ipSource = IpUtils.getIpSource(IpUtils.getIpAddr(request));
            EmailDTO emailDTO = EmailDTO.builder()
                    .email(onlineUser.getEmail())
                    .subject("HelloX")
                    .content("您的账户于" + DateUtil.date() + "在" + ipSource + "尝试登陆,如果不是本人操作请立即修改密码!")
                    .build();
            emailDTO.setWebUrl(PASSWD_URL);
            asyncManager.sendMail(emailDTO,WRONG_PWD);
            throw new MyAuthenticationException("该账户已经登录！如果不是本人操作请修改密码!");
        }

        // 如果输入与密码错误，则计数加一,则进行上锁检测
        Integer count = (Integer) redisUtil.get(LOGIN_FAILURE_PRE + username);
        // 如果redis没有存储用户错误次数 并且 该账户没有锁定，则初始化count
        Boolean isNonLock = loginService.checkAccountLock(username);
        try {
            // 检查用户数据
            if (loginService.checkUser(username, password)) {
                if (Objects.isNull(count) && !isNonLock) {
                    // redis登录错误记录已过期，且账户处于锁定状态，则解锁
                    loginService.changeLock(username, true);
                }
                //将账号、密码装入UsernamePasswordAuthenticationToken中
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } catch (Exception e) {
            if (e instanceof LockedException) {
                throw new MyAuthenticationException("输入错误过多！账户已被锁定一小时！");
            }
            if(e instanceof DisabledException){
                throw new MyAuthenticationException("账户已被禁止登陆！请稍后再试!");
            }
            // 密码错误检查锁定状态
            if (e instanceof PasswordNotMatchException) {
                if (Objects.isNull(count) && isNonLock) {
                    // 代表未锁定的第一次输入错误，1小时过期
                    redisUtil.set(LOGIN_FAILURE_PRE + username, 1, 1L, TimeUnit.HOURS);
                } else if (!Objects.isNull(count) && count >= CommonConst.ACCOUNT_LOCK) {
                    // 如果输入错误5次则锁定账户
                    if (isNonLock) {
                        loginService.changeLock(username, false);
                    }
                    String email = userMapper.selectUserEmailByUsername(username);
                    EmailDTO emailDTO = EmailDTO.builder()
                            .email(email)
                            .subject("HelloX")
                            .content("您的账户密码输入次数过多，已被锁定!如不是本人操作请求改密码!")
                            .build();
                    emailDTO.setWebUrl(PASSWD_URL);
                    asyncManager.sendMail(emailDTO,WRONG_PWD);
                    throw new MyAuthenticationException("输入错误过多！账户已被锁定一小时！");
                } else if (isNonLock) {
                    // redis有错误记录则在此基础上+1
                    count += 1;
                    redisUtil.set(LOGIN_FAILURE_PRE + username, count, 1L, TimeUnit.HOURS);
                }
            }
            throw new MyAuthenticationException(e.getMessage());
        }
        return null;
    }
}
