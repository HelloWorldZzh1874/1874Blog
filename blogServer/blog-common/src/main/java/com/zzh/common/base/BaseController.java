package com.zzh.common.base;

/**
 * @author zzh
 * @description 所有Controller的父类
 * @date 2022/1/2022:36
 */
public class BaseController {

    /**
     * 返回成功
     */
    public Result success()
    {
        return Result.success();
    }

    /**
     * 返回失败消息
     */
    public Result error()
    {
        return Result.error();
    }

    /**
     * 返回成功消息
     */
    public Result success(String message)
    {
        return Result.success(message);
    }

    /**
     * 返回失败消息
     */
    public Result error(String message)
    {
        return Result.error(message);
    }

}
