package com.zzh.common.utils.oshi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zzh
 * @description Oshi系统信息---系统信息
 * @date 2022/4/7 12:58
 */
@Data
public class Sys implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
}
