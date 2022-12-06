package com.zzh.security.handler;

import com.zzh.common.base.Result;
import com.zzh.common.constant.HttpStatus;
import com.zzh.common.utils.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zzh
 * @description TODO
 * @date 2022/2/12 0:38
 */

@Component
public class MyAuthenticationFailureHandler extends JsonUtil implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 返回到客户端
        Result result = Result.error(HttpStatus.UNAUTHORIZED,exception.getLocalizedMessage());
        this.writeJson(response,result);
    }
}
