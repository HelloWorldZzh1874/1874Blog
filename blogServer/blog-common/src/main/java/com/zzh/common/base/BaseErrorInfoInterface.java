package com.zzh.common.base;

/**
 * @author zzh
 * @description 错误描述接口
 * @date 2022/1/27
 */
public interface BaseErrorInfoInterface {
    /**
     * description: 错误码
     * @date 2022/1/27
     * @return java.lang.String
     */
    String getResultCode();

    /**
     * description: 错误描述
     * @date 2022/1/27
     * @return java.lang.String
     */
    String getResultMsg();
}
