package com.zzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.dto.UniqueViewDTO;
import com.zzh.entity.UniqueView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-04-06
 */
@Mapper
public interface UniqueViewMapper extends BaseMapper<UniqueView> {
    /**
     * 获取7天用户量
     * @param startTime 开始时间
     * @param endTime 结束时间，也就是今天
     * @return 用户量
     */
    List<UniqueViewDTO> listUniqueViews(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
