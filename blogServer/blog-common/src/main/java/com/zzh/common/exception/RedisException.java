package com.zzh.common.exception;

import com.zzh.common.base.BaseErrorInfoInterface;
import com.zzh.common.exception.baseException.CommonWriteException;

/**
 * @author zzh
 * @description TODO
 * @date 2022/3/31 11:18
 */
public class RedisException extends CommonWriteException {
    public RedisException() {
        super();
    }

    public RedisException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface);
    }

    public RedisException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface, cause);
    }

    public RedisException(String errorMsg) {
        super(errorMsg);
    }

    public RedisException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public RedisException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }

    @Override
    public String getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public void setErrorCode(String errorCode) {
        super.setErrorCode(errorCode);
    }

    @Override
    public String getErrorMsg() {
        return super.getErrorMsg();
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        super.setErrorMsg(errorMsg);
    }
}
