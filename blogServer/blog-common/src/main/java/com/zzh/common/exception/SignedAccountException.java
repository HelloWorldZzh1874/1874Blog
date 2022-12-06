package com.zzh.common.exception;

import com.zzh.common.exception.baseException.CommonWriteException;

/**
 * @author zzh
 * @description 已登陆账号重复登陆异常
 * @date 2022/2/5 15:15
 */
public class SignedAccountException extends CommonWriteException {
    public SignedAccountException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }
}
