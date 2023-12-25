package com.zzh.controller;

import com.zzh.common.base.Result;
import com.zzh.common.utils.oshi.SystemInfoUtil;
import com.zzh.service.SystemInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzh
 * @description 服务器信息相关
 * @date 2022/4/7 13:26
 */
@RestController
@RequestMapping("/SystemInfo")
@Api(tags = "服务器信息")
public class SystemInfoController {
    @Autowired
    private SystemInfoService systemInfoService;

    /**
     * @description 获取服务器信息
     * @date 2022/4/7
     * @return
     */
    @GetMapping("/getSystemInfo")
    @ApiOperation("获取服务器信息")
    public Result getSysInfo(){
        SystemInfoUtil systemInfo = systemInfoService.getSystemInfo();
        return Result.success(systemInfo);
    }
}
