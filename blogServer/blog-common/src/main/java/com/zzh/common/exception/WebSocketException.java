package com.zzh.common.exception;

import com.zzh.common.exception.baseException.MyException;

import static com.zzh.common.constant.HttpStatus.WEBSOCKET_ERROR;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/2513:55
 */
public class WebSocketException extends MyException {
    public WebSocketException(String errorMsg) {
        super(WEBSOCKET_ERROR,errorMsg);
    }
}
