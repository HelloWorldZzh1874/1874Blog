package com.zzh.common.exception;

import com.zzh.common.base.BaseErrorInfo;
import com.zzh.common.exception.baseException.MyException;

/**
 * @author zzh
 * @description 密码不匹配异常
 * @date 2022/2/120:15
 */
public class PasswordNotMatchException extends MyException {
    public PasswordNotMatchException() {
        super(BaseErrorInfo.PWD_NOT_MATCH);
    }
}
