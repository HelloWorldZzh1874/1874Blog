package com.zzh.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zzh
 * @description TODO
 * @date 2022/2/121:11
 */
public class MyAuthenticationException extends AuthenticationException {
    public MyAuthenticationException(String explanation) {
        super(explanation);
    }
}
