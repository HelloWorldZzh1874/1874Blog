package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author zzh
 * @Date 2021/8/2 22:33
 * @Version 0.1
 * @Description TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationLogDTO {
    /**
     * 日志id
     */
    private Integer id;

    /**
     * 操作模块
     */
    private String optModule;

    /**
     * 操作路径
     */
    private String optUrl;

    /**
     * 操作类型
     */
    private String optType;

    /**
     * 操作方法
     */
    private String optMethod;

    /**
     * 操作描述
     */
    private String optDesc;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 返回数据
     */
    private String responseData;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户登录ip
     */
    private String ipAddr;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 创建时间
     */
    private Date createTime;
}
