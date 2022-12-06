package com.zzh.common.exception;

import com.zzh.common.base.BaseErrorInfoInterface;
import com.zzh.common.exception.baseException.CommonWriteException;

/**
 * @author zzh
 * @description 定时任务相关报错
 * @date 2022/4/7 19:30
 */
public class QuartzJobWriteException extends CommonWriteException {
    public QuartzJobWriteException() {
        super();
    }

    public QuartzJobWriteException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface);
    }

    public QuartzJobWriteException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface, cause);
    }

    public QuartzJobWriteException(String errorMsg) {
        super(errorMsg);
    }

    public QuartzJobWriteException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public QuartzJobWriteException(String errorCode, String errorMsg, Throwable cause) {
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
