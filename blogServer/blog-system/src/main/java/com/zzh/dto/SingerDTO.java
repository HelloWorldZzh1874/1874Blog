package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zzh
 * @Date 2021/8/18 22:11
 * @Version 0.1
 * @Description 歌手搜索VO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingerDTO {
    /**
     * 歌手id
     */
    Integer id;
    /**
     * 歌手名
     */
    String singerName;
}
