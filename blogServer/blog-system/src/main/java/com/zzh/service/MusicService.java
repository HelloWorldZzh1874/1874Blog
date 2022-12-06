package com.zzh.service;

import com.github.pagehelper.PageInfo;
import com.zzh.dto.MusicListDTO;
import com.zzh.entity.Music;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.MusicInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 歌曲信息表 服务类
 * </p>
 *
 * @author zzh
 * @since 2022-04-03
 */
public interface MusicService extends IService<Music> {
    /**
     * 删除歌曲
     * @param id 歌曲id
     */
    void deleteMusic(Integer id);

    /**
     * 查询信息
     * @param conditionVO 查询条件
     * @return
     */
    PageInfo<MusicListDTO> getMusicLists(ConditionVO conditionVO);

    /**
     * 根据歌手id查询
     * @param conditionVO 查询条件
     * @return
     */
    PageInfo<MusicListDTO> listMusicBySingerId(ConditionVO conditionVO);

    /**
     * 修改歌曲封面
     * @param file 图片文件
     * @param id  歌曲id
     */
    void updateMusicImg(MultipartFile file, Integer id);

    /**
     * 更新歌曲信息
     * @param musicInfoVO 歌曲数据
     */
    void updateMusicInfo(MusicInfoVO musicInfoVO);

    /**
     * 添加歌曲
     * @param music 音乐对象
     * @param file 音乐文件
     */
    void addMusic(Music music,MultipartFile file);

    /**
     * 获得歌曲歌词
     * @param id 歌曲id
     * @return 返回歌词
     */
    String getMusicLyric(Integer id);

    /**
     * 根据歌曲id获取歌曲url
     * @param id 歌曲id
     * @return 返回url
     */
    String getMusicUrl(Integer id);

    /**
     * 添加歌曲到用户收藏
     * @param id 歌曲id
     */
    void addCollect(Integer id);

    /**
     * 移除个人收藏
     * @param id 歌曲id
     * @return 返回url
     */
    void removeCollect(Integer id);

    /**
     * 通过歌曲名称模糊查询歌曲
     * @param name 歌曲名称关键字
     * @return 返回歌曲列表
     */
    List<MusicListDTO> getMusicLike(String name);

}
