package com.zzh.mapper;

import com.zzh.dto.MusicListDTO;
import com.zzh.entity.Music;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 歌曲信息表 Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-04-03
 */
@Mapper
public interface MusicMapper extends BaseMapper<Music> {
    /**
     * 根据歌手id查询所有歌曲id
     * @param id
     * @return
     */
    List<Integer> listIdsBySinger(@Param("id") Integer id);

    /**
     * 查找分页数据
     * @param conditionVO
     * @return
     */
    List<MusicListDTO> listBackMusics(@Param("conditionVO")ConditionVO conditionVO);

    /**
     * 查找分页数据
     * @param conditionVO
     * @return
     */
    List<MusicListDTO> listMusicBySinger(@Param("conditionVO") ConditionVO conditionVO);

    /**
     * 获得歌曲歌词
     * @param id 歌曲id
     * @return 返回歌词
     */
    String getLyric(@Param("id") Integer id);

    /**
     * 根据歌曲id获取歌曲url
     * @param id 歌曲id
     * @return 返回url
     */
    String getMusicUrl(@Param("id") Integer id);

    /**
     * 通过歌曲名称模糊查询歌曲
     * @param name 歌曲名称关键字
     * @return 返回歌曲列表
     */
    List<MusicListDTO> getMusicLike(@Param("name") String name);
}
