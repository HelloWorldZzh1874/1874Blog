package com.zzh.mapper;

import com.zzh.dto.MusicListDTO;
import com.zzh.entity.MusicList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-04-26
 */
@Mapper
public interface MusicListMapper extends BaseMapper<MusicList> {
    /**
     * 查询歌单信息
     * @return
     */
    List<MusicListDTO> getHostListMusic();

    /**
     * 根据用户id查询收藏信息
     * @param userId 用户id
     * @return
     */
    List<MusicListDTO> getCollectMusic(@Param("userId") Long userId);
}
