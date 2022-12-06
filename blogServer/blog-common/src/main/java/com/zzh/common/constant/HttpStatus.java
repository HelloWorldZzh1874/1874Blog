package com.zzh.common.constant;

/**
 * @author zzh
 * @description http状态码
 * @date 2022/1/25
 */
public class HttpStatus
{
    /**
     * 操作成功
     */
    public static final String SUCCESS = "200";

    /**
     * 对象创建成功
     */
    public static final String CREATED = "201";

    /**
     * 请求已经被接受
     */
    public static final String ACCEPTED = "202";

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    public static final String NO_CONTENT = "204";

    /**
     * 资源已被移除
     */
    public static final String MOVED_PERM = "301";

    /**
     * 重定向
     */
    public static final String SEE_OTHER = "303";

    /**
     * 资源没有被修改
     */
    public static final String NOT_MODIFIED = "304";

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    public static final String BAD_REQUEST = "400";

    /**
     * 未授权
     */
    public static final String UNAUTHORIZED = "401";

    /**
     * 访问受限，授权过期
     */
    public static final String FORBIDDEN = "403";

    /**
     * 资源，服务未找到
     */
    public static final String NOT_FOUND = "404";

    /**
     * 不允许的http方法
     */
    public static final String BAD_METHOD = "405";

    /**
     * 资源冲突，或者资源被锁
     */
    public static final String CONFLICT = "409";

    /**
     * 不支持的数据，媒体类型
     */
    public static final String UNSUPPORTED_TYPE = "415";

    /**
     * 系统内部错误
     */
    public static final String ERROR = "500";

    /**
     * 接口未实现
     */
    public static final String NOT_IMPLEMENTED = "501";

    /**
     * 服务器忙
     */
    public static final String SERVER_BUSY = "503";

    /**
     * 用户未找到
     */
    public static final String USER_NOT_FOUND = "512";

    /**
     * 密码错误
     */
    public static final String PWD_NOT_MATCH = "513";

    /**
     * 参数值不允许
     */
    public static final String PARMA_VALUE_ERROR = "514";

    /**
     * 邮箱格式错误
     */
    public static final String UNCORRECTED = "515";

    /**
     * WebSocket连接错误
     */
    public static final String WEBSOCKET_ERROR = "516";
}
