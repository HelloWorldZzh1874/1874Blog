package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.common.utils.BeanCopyUtil;
import com.zzh.dto.OperationLogDTO;
import com.zzh.entity.OperationLog;
import com.zzh.mapper.OperationLogMapper;
import com.zzh.service.OperationLogService;
import com.zzh.vo.ConditionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/111:55
 */
@Service
public class OperationLogServiceImpl  extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Autowired
    OperationLogMapper operationLogMapper;

    @Override
    public PageInfo<OperationLog> listLogs(ConditionVO conditionVO) {
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        // 查询日志列表
        List<OperationLog> operationLogs = operationLogMapper.selectList(new LambdaQueryWrapper<OperationLog>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLog::getOptModule, conditionVO.getKeywords())
                .or()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLog::getOptDesc, conditionVO.getKeywords())
                .gt(Objects.nonNull(conditionVO.getStartTime()), OperationLog::getCreateTime, conditionVO.getStartTime())
                .lt(Objects.nonNull(conditionVO.getEndTime()), OperationLog::getCreateTime, conditionVO.getEndTime())
                .orderByDesc(OperationLog::getId));
        return new PageInfo<>(operationLogs);
    }
}
