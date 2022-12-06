package com.zzh.common.exception;

import com.zzh.common.base.BaseErrorInfoInterface;
import com.zzh.common.exception.baseException.MyException;

/**
 * @author zzh
 * @description 邮箱错误异常
 * @date 2022/2/720:25
 */
public class EmailUncorrectedException extends MyException {
    public EmailUncorrectedException(String errorMsg) {
        super(errorMsg);
    }

    public EmailUncorrectedException() {
        super();
    }

    public EmailUncorrectedException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface);
    }

    public EmailUncorrectedException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface, cause);
    }

    public EmailUncorrectedException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public EmailUncorrectedException(String errorCode, String errorMsg, Throwable cause) {
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
