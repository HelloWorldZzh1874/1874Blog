package com.zzh.common.exception;

import com.zzh.common.base.BaseErrorInfoInterface;
import com.zzh.common.exception.baseException.MyException;

/**
 * @author zzh
 * @description corn表达式异常
 * @date 2022/4/7 19:35
 */
public class CornException extends MyException {
    public CornException() {
        super();
    }

    public CornException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface);
    }

    public CornException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface, cause);
    }

    public CornException(String errorMsg) {
        super(errorMsg);
    }

    public CornException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public CornException(String errorCode, String errorMsg, Throwable cause) {
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
