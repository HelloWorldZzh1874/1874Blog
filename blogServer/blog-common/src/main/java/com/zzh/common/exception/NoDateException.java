package com.zzh.common.exception;

import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.baseException.MyException;

/**
 * @author 37436
 */
public class NoDateException extends MyException {
    public NoDateException() {
        super(HttpStatus.NO_CONTENT,"查询该数据为空！");
    }

    public NoDateException(String message) {
        super(HttpStatus.NO_CONTENT,message);
    }

    public NoDateException(String errorCode,String message) {
        super(errorCode,message);
    }
}
