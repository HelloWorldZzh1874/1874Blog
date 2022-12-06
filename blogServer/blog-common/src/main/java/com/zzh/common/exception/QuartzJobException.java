package com.zzh.common.exception;

import com.zzh.common.base.BaseErrorInfoInterface;
import com.zzh.common.exception.baseException.MyException;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/719:33
 */
public class QuartzJobException extends MyException {
    public QuartzJobException() {
        super();
    }

    public QuartzJobException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface);
    }

    public QuartzJobException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface, cause);
    }

    public QuartzJobException(String errorMsg) {
        super(errorMsg);
    }

    public QuartzJobException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public QuartzJobException(String errorCode, String errorMsg, Throwable cause) {
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
