package com.zzh.common.exception;

import com.zzh.common.base.BaseErrorInfoInterface;
import com.zzh.common.exception.baseException.CommonWriteException;

/**
 * @author zzh
 * @description 匿名用户访问需鉴权接口时抛出此异常
 * @date 2022/2/415:05
 */
public class RefuseAnonymousUserException extends CommonWriteException {
    public RefuseAnonymousUserException() {
        super();
    }

    public RefuseAnonymousUserException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface);
    }

    public RefuseAnonymousUserException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface, cause);
    }

    public RefuseAnonymousUserException(String errorMsg) {
        super(errorMsg);
    }

    public RefuseAnonymousUserException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public RefuseAnonymousUserException(String errorCode, String errorMsg, Throwable cause) {
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
