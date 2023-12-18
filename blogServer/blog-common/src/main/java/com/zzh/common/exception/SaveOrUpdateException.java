package com.zzh.common.exception;

import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.baseException.CommonWriteException;

/**
 * @author zzh
 * @description TODO
 * @date 2022/3/1022:51
 */
public class SaveOrUpdateException extends CommonWriteException {
    public SaveOrUpdateException() {
        super(HttpStatus.ERROR,"保存失败!服务器忙，请稍后再试!");
    }

    public SaveOrUpdateException(String message) {
        super(message);
    }

    public SaveOrUpdateException(String errorCode,String message) {
        super(errorCode,message);
    }
}
