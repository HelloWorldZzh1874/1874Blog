package com.zzh.service.impl;

import cn.hutool.core.date.DateUtil;
import com.zzh.dto.UniqueViewDTO;
import com.zzh.entity.UniqueView;
import com.zzh.mapper.UniqueViewMapper;
import com.zzh.service.UniqueViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-04-06
 */
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewMapper, UniqueView> implements UniqueViewService {

    @Autowired
    UniqueViewMapper uniqueViewMapper;

    @Override
    public List<UniqueViewDTO> listUniqueViews() {
        return uniqueViewMapper.listUniqueViews(DateUtil.lastWeek(), DateUtil.date());
    }
}
