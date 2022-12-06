package com.zzh.security.handler;

import com.zzh.common.base.BaseErrorInfo;
import com.zzh.common.base.Result;
import com.zzh.common.utils.JsonUtil;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zzh
 * @description 未授权异常处理
 * @date 2022/2/11 2:31
 */

@Component
public class MyAuthenticationEntryPoint extends JsonUtil implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
       if(authException instanceof InsufficientAuthenticationException){
           Result result = Result.error(BaseErrorInfo.FORBIDDEN.getResultCode(),"没有访问权限！请重新登陆或联系管理员!");
           this.writeJson(response,result);
       }
        Result result = Result.error(BaseErrorInfo.SIGNATURE_NOT_MATCH.getResultCode(),"需要登陆!");
        this.writeJson(response,result);
    }
}
