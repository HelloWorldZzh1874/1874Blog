package com.zzh.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zzh
 * @Date 2021/8/18 21:10
 * @Version 0.1
 * @Description 歌曲信息Vo
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MusicInfoVO {

    @ApiModelProperty(name = "id", value = "歌曲id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "al", value = "歌曲专辑", dataType = "String")
    private String al;

    @ApiModelProperty(name = "name", value = "歌曲专辑", dataType = "String")
    private String name;

    @ApiModelProperty(name = "lyric", value = "歌词", dataType = "String")
    private String lyric;
}
