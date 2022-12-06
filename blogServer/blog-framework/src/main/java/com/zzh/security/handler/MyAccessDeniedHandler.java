package com.zzh.security.handler;

import com.zzh.common.base.BaseErrorInfo;
import com.zzh.common.base.Result;
import com.zzh.common.utils.JsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zzh
 * @description 鉴权失败处理器
 * @date 2022/2/11 2:43
 */

@Component
public class MyAccessDeniedHandler extends JsonUtil implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        Result result = Result.error(BaseErrorInfo.SIGNATURE_NOT_MATCH.getResultCode(), "权限不足!"+accessDeniedException.getMessage());
        this.writeJson(response, result);
    }
}
