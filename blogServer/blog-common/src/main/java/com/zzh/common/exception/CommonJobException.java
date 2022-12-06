package com.zzh.common.exception;

import com.zzh.common.base.BaseErrorInfoInterface;
import com.zzh.common.exception.baseException.MyException;

/**
 * @author zzh
 * @description QuartzJob异常
 * @date 2022/4/719:37
 */
public class CommonJobException extends MyException {
    public CommonJobException() {
        super();
    }

    public CommonJobException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface);
    }

    public CommonJobException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface, cause);
    }

    public CommonJobException(String errorMsg) {
        super(errorMsg);
    }

    public CommonJobException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public CommonJobException(String errorCode, String errorMsg, Throwable cause) {
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
