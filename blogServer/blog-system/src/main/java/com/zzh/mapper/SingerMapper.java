package com.zzh.mapper;

import com.zzh.entity.Singer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  歌手 Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-04-02
 */
@Mapper
public interface SingerMapper extends BaseMapper<Singer> {

}
