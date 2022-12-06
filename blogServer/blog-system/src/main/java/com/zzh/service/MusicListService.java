package com.zzh.service;

import com.zzh.dto.MusicListDTO;
import com.zzh.entity.MusicList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-04-26
 */
public interface MusicListService extends IService<MusicList> {

    /**
     * 查询指定播放列表歌曲
     * @return 返回歌曲id列表
     */
    List<MusicListDTO> getHostListMusic();

    /**
     * 查询当前登录用户的收藏列表
     * @return 返回歌曲id列表
     */
    List<MusicListDTO> getCollectMusic();
}
