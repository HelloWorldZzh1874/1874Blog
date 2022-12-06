package com.zzh.common.base;

import com.zzh.common.constant.HttpStatus;

/**
 * @author zzh
 * @description 错误码
 * @date 2022/1/27
 */

public enum BaseErrorInfo implements BaseErrorInfoInterface{

    // 数据操作错误定义
    SUCCESS(HttpStatus.SUCCESS, "成功!"),
    CREATED(HttpStatus.CREATED,"对象创建成功!"),
    ACCEPT(HttpStatus.ACCEPTED,"请求已被接受!"),
    NO_CONTENT(HttpStatus.NO_CONTENT,"操作成功!无返回数据"),
    MOVED_RES(HttpStatus.MOVED_PERM,"资源已被移除!"),
    BODY_NOT_MATCH(HttpStatus.BAD_REQUEST,"请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(HttpStatus.UNAUTHORIZED,"未授权!"),
    FORBIDDEN(HttpStatus.FORBIDDEN,"授权已过期!"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "未找到该资源!"),
    BAD_METHOD(HttpStatus.BAD_METHOD,"不允许的HTTP方法!"),
    RES_LOCKED(HttpStatus.CONFLICT,"资源已经锁定!"),
    INTERNAL_SERVER_ERROR(HttpStatus.ERROR, "服务器内部错误!"),
    SERVER_BUSY(HttpStatus.SERVER_BUSY,"服务器正忙，请稍后再试!"),
    USER_NOT_FOUND(HttpStatus.USER_NOT_FOUND,"没有此用户!"),
    PWD_NOT_MATCH(HttpStatus.PWD_NOT_MATCH,"密码错误")
    ;

    /** 错误码 */
    private final String errorCode;

    /** 错误描述 */
    private final String errorMsg;

    BaseErrorInfo(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getResultCode() {
        return errorCode;
    }

    @Override
    public String getResultMsg() {
        return errorMsg;
    }
}
