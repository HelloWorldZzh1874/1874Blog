package com.zzh.common.exception;

import com.zzh.common.base.BaseErrorInfo;
import com.zzh.common.exception.baseException.MyException;

/**
 * @author zzh
 * @description TODO
 * @date 2022/2/120:12
 */
public class UserNotFoundException extends MyException {
    public UserNotFoundException() {
        super(BaseErrorInfo.USER_NOT_FOUND);
    }
}
