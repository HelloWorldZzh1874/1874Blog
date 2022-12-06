package com.zzh.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.common.exception.SaveOrUpdateException;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.dto.MusicListDTO;
import com.zzh.entity.Music;
import com.zzh.entity.MusicList;
import com.zzh.entity.Singer;
import com.zzh.mapper.MusicListMapper;
import com.zzh.mapper.MusicMapper;
import com.zzh.mapper.SingerMapper;
import com.zzh.service.MusicListService;
import com.zzh.service.MusicService;
import com.zzh.utils.SecurityUtils;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.MusicInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.zzh.common.constant.CommonConst.*;

/**
 * <p>
 * 歌曲信息表 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-04-03
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Autowired
    MusicMapper musicMapper;

    @Autowired
    SingerMapper singerMapper;

    @Autowired
    MusicListMapper musicListMapper;

    @Override
    public void deleteMusic(Integer id) {
        Music music = musicMapper.selectById(id);
        /*
        删除歌曲图片
         */
        // 获得图片实际地址
        String path = music.getMusicImg();
        String realPath = System.getProperty("user.dir") + System.getProperty("file.separator") + path;
        File imgFile = new File(realPath);
        if (imgFile.exists() && !MUSIC_COVER.equals(path)) {
            imgFile.delete();
        }
        /*
        删除歌曲文件
         */
        path = music.getMusicUrl();
        realPath = System.getProperty("user.dir") + System.getProperty("file.separator") + path;
        File file = new File(realPath);
        if (file.exists()) {
            file.delete();
        }
        // 删除数据库数据
        musicMapper.deleteById(id);
        musicListMapper.delete(new LambdaQueryWrapper<MusicList>().eq(MusicList::getMusicId, id));
    }

    @Override
    public PageInfo<MusicListDTO> getMusicLists(ConditionVO conditionVO) {
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        // 获取列表
        List<MusicListDTO> musicListDTO = musicMapper.listBackMusics(conditionVO);
        return new PageInfo<>(musicListDTO);
    }

    @Override
    public PageInfo<MusicListDTO> listMusicBySingerId(ConditionVO conditionVO) {
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        // 获取列表
        List<MusicListDTO> musicListDTO = musicMapper.listMusicBySinger(conditionVO);
        return new PageInfo<>(musicListDTO);
    }

    @Override
    public void updateMusicImg(MultipartFile file, Integer id) {
        // 如果为空则返回
        if (file.isEmpty()) {
            throw new SaveOrUpdateException("服务器异常！请稍后再试！");
        }
        Music music = musicMapper.selectById(id);
        if (Objects.isNull(music)) {
            throw new SaveOrUpdateException("服务器异常！请稍后再试！");
        }
         /*
        删除原歌曲图片
         */
        // 获得图片实际地址
        String path = music.getMusicImg();
        String realPath = System.getProperty("user.dir") + System.getProperty("file.separator") + path;
        File imgFile = new File(realPath);
        if (imgFile.exists() && !MUSIC_COVER.equals(path)) {
            imgFile.delete();
        }

        // 文件名等于毫秒加文件名 避免重复
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        // 创建文件
        File file1 = new File(MUSIC_IMG_PATH);
        // 如果文件路径不存在则新增
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(MUSIC_IMG_PATH + System.getProperty("file.separator") + fileName);
        try {
            // 保存文件
            file.transferTo(dest);
            musicMapper.update(new Music(), new LambdaUpdateWrapper<Music>()
                    .set(Music::getMusicImg, "/img/musicImg/" + fileName)
                    .set(Music::getMusicCreatetime, new Date())
                    .eq(Music::getId, id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMusicInfo(MusicInfoVO musicInfoVO) {
        Music music = Music.builder()
                .id(musicInfoVO.getId())
                .musicAl(musicInfoVO.getAl())
                .musicName(musicInfoVO.getName())
                .musicLyric(musicInfoVO.getLyric())
                .build();
        musicMapper.updateById(music);
    }

    @Override
    public void addMusic(Music music, MultipartFile file) {
        Singer singer = singerMapper.selectById(music.getSingerId());
        if (Objects.isNull(singer)) {
            throw new SaveOrUpdateException("没有此歌手!请先添加!");
        }
        // 上传文件
        // 如果为空则返回
        if (file.isEmpty()) {
            throw new SaveOrUpdateException("服务器异常！请稍后再试！");
        }
        // 文件名等于毫秒加文件名 避免重复
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        // 创建文件
        File file1 = new File(MUSIC_FILE_PATH);
        // 如果文件路径不存在则新增
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(MUSIC_FILE_PATH + System.getProperty("file.separator") + fileName);
        try {
            // 保存文件
            file.transferTo(dest);
            // 判断文件类型
            String type = FileTypeUtil.getType(dest);
            // 如果不是允许的类型则抛出异常删除,该文件
            if (!FILE_TYPE.get(MUSIC_FILE_TYPE).contains(type)) {
                dest.delete();
                throw new SaveOrUpdateException("请上传音频文件mp3,flac");
            }
            // 保存数据库
            music.setMusicUrl("/musics/" + fileName);
            music.setMusicCreatetime(new Date());
            musicMapper.insert(music);
            MusicList listMusic = MusicList.builder().listId(HOT_MUSIC_TYPE).musicId(music.getId()).build();
            musicListMapper.insert(listMusic);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getMusicLyric(Integer id) {
        return musicMapper.getLyric(id);
    }

    @Override
    public String getMusicUrl(Integer id) {
        return musicMapper.getMusicUrl(id);
    }

    @Override
    public void addCollect(Integer id) {
        if (Objects.isNull(this.getById(id))) {
            throw new CommonWriteException("暂无此歌曲!");
        }
        MusicList musicList = MusicList.builder().listId(COLLECT_MUSIC).musicId(id).userId(SecurityUtils.getCurUser().getUser().getId()).build();
        musicListMapper.insert(musicList);
    }

    @Override
    public void removeCollect(Integer id) {
        if (Objects.isNull(this.getById(id))) {
            throw new CommonWriteException("暂无此歌曲!");
        }
        musicListMapper.delete(new LambdaQueryWrapper<MusicList>().eq(MusicList::getMusicId, id).eq(MusicList::getUserId, SecurityUtils.getCurUser().getUser().getId()));
    }

    @Override
    public List<MusicListDTO> getMusicLike(String name) {
        return musicMapper.getMusicLike(name);
    }
}
