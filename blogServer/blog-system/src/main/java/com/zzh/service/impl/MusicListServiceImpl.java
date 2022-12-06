package com.zzh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.dto.MusicListDTO;
import com.zzh.entity.MusicList;
import com.zzh.mapper.MusicListMapper;
import com.zzh.service.MusicListService;
import com.zzh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-04-26
 */
@Service
public class MusicListServiceImpl extends ServiceImpl<MusicListMapper, MusicList> implements MusicListService {
    @Autowired
    MusicListMapper musicListMapper;

    @Override
    public List<MusicListDTO> getHostListMusic() {
        return musicListMapper.getHostListMusic();
    }

    @Override
    public List<MusicListDTO> getCollectMusic() {
        return musicListMapper.getCollectMusic(SecurityUtils.getCurUser().getUser().getId());
    }
}
