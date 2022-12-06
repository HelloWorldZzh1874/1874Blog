package com.zzh.mapper;

import com.zzh.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-03-15
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
