package com.zzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzh.dto.OperationLogDTO;
import com.zzh.entity.OperationLog;
import com.zzh.vo.ConditionVO;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/111:54
 */
public interface OperationLogService extends IService<OperationLog> {
    /**
     * 查看操作日志
     * @param conditionVO 查找条件
     * @return
     */
    PageInfo<OperationLog> listLogs(ConditionVO conditionVO);
}
