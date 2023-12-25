package com.zzh.controller;

import com.zzh.aop.OptLog;
import com.zzh.common.base.Result;
import com.zzh.entity.SysQuartz;
import com.zzh.service.QuartzService;
import com.zzh.service.SysQuartzService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.SysQuartzVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.zzh.aop.OptTypeConst.*;

/**
 * @author zzh
 * @description 定时任务
 * @date 2022/4/7 20:29
 */
@RestController
@RequestMapping("/quartz")
@Api(tags = "定时任务模块")
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private SysQuartzService sysQuartzService;

    /**
     * @param sysQuartzVo 前端数据
     * @return com.zzh.common.base.Result
     * @description 添加一个定时任务
     * @date 2022/4/8
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加一个定时任务")
    @OptLog(optType = SAVE)
    public Result addSysQuartz(@RequestBody SysQuartzVo sysQuartzVo) {
        SysQuartz sysQuartz = SysQuartz.builder()
                .jobName(sysQuartzVo.getJobName())
                .jobClassname(sysQuartzVo.getJobClassname())
                .jobGroup(sysQuartzVo.getJobGroup())
                .cronExpression(sysQuartzVo.getCronExpression()).build();
        quartzService.addJob(sysQuartz);
        return Result.success();
    }

    /**
     * @param id 任务id
     * @return com.zzh.common.base.Result
     * @description TODO
     * @date 2022/4/8
     */
    @ApiOperation(value = "暂停一个任务")
    @OptLog(optType = UPDATE)
    @PutMapping("/pauseJob/{id}")
    public Result pauseQuartz(@PathVariable("id") Integer id) {
        quartzService.pauseJob(id);
        return Result.success();
    }

    /**
     * @param id 任务id
     * @return com.zzh.common.base.Result
     * @description 恢复一个任务
     * @date 2022/4/8
     */
    @ApiOperation(value = "恢复一个任务")
    @OptLog(optType = UPDATE)
    @PutMapping("/resumeJob/{id}")
    public Result resumeQuartz(@PathVariable("id") Integer id) {
        quartzService.resumeJob(id);
        return Result.success();
    }

    /**
     * @param id 任务id
     * @return com.zzh.common.base.Result
     * @description 删除一个任务
     * @date 2022/4/8
     */
    @DeleteMapping("/deleteJob")
    @ApiOperation(value = "删除一个任务")
    @OptLog(optType = REMOVE)
    public Result deleteQuartz(@RequestParam Integer id) {
        quartzService.deleteJob(id);
        return Result.success();
    }

    /**
     * @return com.zzh.common.base.Result
     * @description 恢复所有暂停的任务
     * @date 2022/4/8
     */
    @PutMapping("/resumeAll")
    @ApiOperation(value = "恢复所有暂停的任务")
    @OptLog(optType = UPDATE)
    public Result resumeAll() {
        quartzService.resumeAllJob();
        return Result.success();
    }

    /**
     * @return com.zzh.common.base.Result
     * @description 暂停所有任务
     * @date 2022/4/8
     */
    @PutMapping("/pauseAll")
    @ApiOperation(value = "暂停所有任务")
    @OptLog(optType = UPDATE)
    public Result pauseAll() {
        quartzService.pauseAllJob();
        return Result.success();
    }


    /**
     * @param conditionVO 分页信息
     * @return com.zzh.common.base.Result 定时任务以及任务类名，固定组名
     * @description 查询所有定时任务以及相关信息
     * @date 2022/4/9
     */
    @PostMapping("/admin/getAll")
    @ApiOperation(value = "查询所有定时任务以及相关信息")
    public Result getAllQuartzInfo(ConditionVO conditionVO) {
        return Result.success(sysQuartzService.getAllQuartzInfo(conditionVO));
    }

}
