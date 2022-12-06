package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 *  歌手
 * </p>
 *
 * @author zzh
 * @since 2022-04-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("tb_singer")
@ApiModel(value="Singer对象", description=" 歌手")
public class Singer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "歌手名字")
    private String singerName;

    @ApiModelProperty(value = "性别 0女1男")
    private Boolean singerSex;

    @ApiModelProperty(value = "歌手图片")
    private String singerPic;

    @ApiModelProperty(value = "生日")
    private Date singerBirth;

    @ApiModelProperty(value = "地区")
    private String singerLocation;

    @ApiModelProperty(value = "简介")
    private String singerIntroduction;


}
