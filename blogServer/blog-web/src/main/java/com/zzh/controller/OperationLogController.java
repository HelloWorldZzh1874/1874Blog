package com.zzh.controller;

import com.zzh.aop.OptLog;
import com.zzh.common.base.Result;
import com.zzh.service.OperationLogService;
import com.zzh.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zzh.aop.OptTypeConst.REMOVE;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/11 1:52
 */
@Api(tags = "日志模块")
@RestController
@RequestMapping("/OperationLog")
public class OperationLogController {
    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation(value = "查看操作日志")
    @PostMapping("/admin/logs")
    public Result listOperationLogs(ConditionVO conditionVO) {
        return Result.success(operationLogService.listLogs(conditionVO));
    }

    @ApiOperation(value = "删除操作日志")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/logs")
    public Result deleteLogs(@RequestBody List<Integer> logIdList){
        operationLogService.removeByIds(logIdList);
        return Result.success();
    }
}
