package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zzh
 * @Date 2021/8/5 21:15
 * @Version 0.1
 * @Description 歌曲列表信息
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MusicListDTO {
    /**
     * 歌曲id
     */
    private Integer id;
    /**
     * 歌曲名称
     */
    private String name;
    /**
     * 歌手
     */
    private String singer;
    /**
     * 歌曲图片
     */
    private String picUrl;
    /**
     * 歌曲专辑
     */
    private String al;
    /**
     * 歌曲歌词
     */
    private String lyric;
}
