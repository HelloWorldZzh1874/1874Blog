package com.zzh.service;

import com.zzh.dto.UniqueViewDTO;
import com.zzh.entity.UniqueView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-04-06
 */
public interface UniqueViewService extends IService<UniqueView> {
    /**
     * 获取7天用户量统计
     * @return 用户量
     */
    List<UniqueViewDTO> listUniqueViews();
}
