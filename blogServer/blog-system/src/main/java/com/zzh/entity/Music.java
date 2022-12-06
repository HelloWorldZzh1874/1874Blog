package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 歌曲信息表
 * </p>
 *
 * @author zzh
 * @since 2022-04-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("tb_music")
@ApiModel(value="Music对象", description="歌曲信息表")
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "歌手id")
    private Integer singerId;

    @ApiModelProperty(value = "歌曲名字")
    private String musicName;

    @ApiModelProperty(value = "歌曲创建时间")
    @TableField("music_createTime")
    private Date musicCreatetime;

    @ApiModelProperty(value = "歌曲跟新时间")
    @TableField("music_updateTime")
    private Date musicUpdatetime;

    @ApiModelProperty(value = "歌曲图片")
    @TableField("music_Img")
    private String musicImg;

    @ApiModelProperty(value = "歌词")
    private String musicLyric;

    @ApiModelProperty(value = "歌曲地址")
    private String musicUrl;

    @ApiModelProperty(value = "歌曲专辑")
    private String musicAl;


}
