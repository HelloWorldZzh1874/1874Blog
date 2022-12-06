package com.zzh.common.exception.baseException;

import com.zzh.common.base.BaseErrorInfoInterface;

/**
 * @author zzh
 * @description 自定义异常
 * @date 2022/1/2719:24
 */
public class MyException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public MyException() {
        super();
    }

    public MyException(BaseErrorInfoInterface errorInfoInterface) {
        super(errorInfoInterface.getResultMsg());
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMsg = errorInfoInterface.getResultMsg();
    }

    public MyException(BaseErrorInfoInterface errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getResultMsg(), cause);
        this.errorCode = errorInfoInterface.getResultCode();
        this.errorMsg = errorInfoInterface.getResultMsg();
    }

    public MyException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public MyException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public MyException(String errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
