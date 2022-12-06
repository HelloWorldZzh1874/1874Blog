package com.zzh.service;

import com.zzh.common.utils.oshi.SystemInfoUtil;

/**
 * <p>
 * 系统信息服务
 * </p>
 *
 * @author zzh
 * @since 2022-04-03
 */
public interface SystemInfoService {
    /**
     * 获取系统信息对象
     * @date 2022/4/7
     * @return com.zzh.common.utils.oshi.SystemInfoUtil
     */
    SystemInfoUtil getSystemInfo();
}
