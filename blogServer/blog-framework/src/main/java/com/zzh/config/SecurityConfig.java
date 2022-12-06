package com.zzh.config;

import com.zzh.common.utils.MyPasswordEncoder;
import com.zzh.security.filter.MyAccessDecisionManager;
import com.zzh.security.filter.MyFilterInvocationSecurityMetadataSource;
import com.zzh.security.filter.MyUsernamePasswordAuthenticationFilter;
import com.zzh.security.filter.TokenAuthenticationFilter;
import com.zzh.security.handler.MyAccessDeniedHandler;
import com.zzh.security.handler.MyAuthenticationEntryPoint;
import com.zzh.security.handler.MyAuthenticationFailureHandler;
import com.zzh.security.handler.MyAuthenticationSuccessHandler;
import com.zzh.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zzh
 * @description TODO
 * @date 2022/3/5 10:47
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * token过滤器
     */
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    /**
     * 实现UserDetailsService
     */
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    /**
     * 密码编码器
     */
    @Autowired
    MyPasswordEncoder passwordEncoder;

    /**
     * 未授权异常
     */
    @Autowired
    private MyAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 鉴权失败异常
     */
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    /**
     * 认证成功处理
     */
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    /**
     * 认证失败处理
     */
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置登录注销路径
        http.formLogin()
                .loginProcessingUrl("/login");
        http
                // 关闭csrf
                .csrf().disable()
                // 允许跨域
                .cors()
                .and()
                // 关闭从session获取spring context
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 接口认证配置
                .authorizeRequests()
                // 放行login接口
                .antMatchers("/login").permitAll()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        // 注入自定义权限决策和权限数据
                        fsi.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());
                        fsi.setAccessDecisionManager(accessDecisionManager());
                        return fsi;
                    }
                }) // 其他接口需要认证
                .anyRequest().permitAll();


        // 在 UsernamePasswordAuthenticationFilter 过滤器之前添加 token filter
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 拦截账号、密码。覆盖 UsernamePasswordAuthenticationFilter过滤器,自定义认证过程
        http.addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 异常处理
        http.exceptionHandling()
                // 未授权异常
                .authenticationEntryPoint(authenticationEntryPoint)
                // 没有此权限异常
                .accessDeniedHandler(accessDeniedHandler);
    }

    /**
     * 从容器中取出 AuthenticationManagerBuilder，执行方法里面的逻辑之后，放回容器
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // 使用自定义加密与UserDetails
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * 手动注入账号、密码拦截器
     */
    @Bean
    MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        // 自定义认证过滤器
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        // 成功后处理
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        // 失败后处理
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        // 注入 authenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }


    /**
     * 暴露 authenticationManager 来实现自定义认证
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 自定义权限决策
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new MyAccessDecisionManager();
    }

    /**
     * 权限数据
     */
    @Bean
    public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
        return new MyFilterInvocationSecurityMetadataSource();
    }
}
