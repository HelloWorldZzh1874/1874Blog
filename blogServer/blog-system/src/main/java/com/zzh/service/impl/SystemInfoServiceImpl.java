package com.zzh.service.impl;

import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.common.utils.oshi.SystemInfoUtil;
import com.zzh.service.SystemInfoService;
import org.springframework.stereotype.Service;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/713:24
 */
@Service
public class SystemInfoServiceImpl implements SystemInfoService {
    @Override
    public SystemInfoUtil getSystemInfo() {
        SystemInfoUtil systemInfoUtil = new SystemInfoUtil();
        try {
            systemInfoUtil.copyTo();
        }catch (Exception e){
            throw new CommonWriteException("服务器异常，无法获取信息!");
        }
        return systemInfoUtil;
    }
}
