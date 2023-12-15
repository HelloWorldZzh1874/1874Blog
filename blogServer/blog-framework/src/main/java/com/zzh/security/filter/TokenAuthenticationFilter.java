package com.zzh.security.filter;

import com.zzh.entity.User;
import com.zzh.utils.SecurityUtils;
import com.zzh.common.utils.StringUtils;
import com.zzh.entity.LoginUser;
import com.zzh.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zzh
 * @description spring security提供的过滤器基类，每次请求都会执行
 * @date 2022/1/26 19:58
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    /**
     * token标识
     */
    @Value("${token.header}")
    private String header;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求中得到token
        String token = request.getHeader(this.header);
        // 从token中获取user对象
        LoginUser loginUser;
        // 测试环境用
        if (StringUtils.isNotEmpty(token) && "root".equals(token)) {
            LoginUser testUser = LoginUser.builder().role("ROLE_ROOT")
                    .user(User.builder().id(1L).username("test").password("123").enabled(true).notLock(true).build()).build();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 如果token不为空 对象并且上下文没有值,并且此token能解析到用户
        else if (StringUtils.isNotEmpty(token)
                && Objects.isNull(SecurityUtils.getAuthentication())
                && !Objects.isNull(loginUser = tokenService.parseLoginUser(token))
        ) {
            // 检查是否需要刷新token
            tokenService.verifyToken(loginUser);
            // 使用 Authentication 存入 security context
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }




        // 放行请求
        filterChain.doFilter(request, response);
    }
}
