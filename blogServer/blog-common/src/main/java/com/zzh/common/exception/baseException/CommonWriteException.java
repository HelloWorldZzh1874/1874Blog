package com.zzh.common.exception.baseException;

import com.zzh.common.base.BaseErrorInfoInterface;

/**
 * @author zzh
 * @description 主要用于输出页面的异常
 * @date 2022/2/4 14:59
 */
public class CommonWriteException extends MyException {
    public CommonWriteException() {
        super();
    }

    public CommonWriteException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface);
    }

    public CommonWriteException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface, cause);
    }

    public CommonWriteException(String errorMsg) {
        super(errorMsg);
    }

    public CommonWriteException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public CommonWriteException(String errorCode, String errorMsg, Throwable cause) {
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
