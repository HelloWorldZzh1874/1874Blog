package com.zzh.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Author zzh
 * @Date 2021/8/19 10:37
 * @Version 0.1
 * @Description 歌手数据
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SingerVO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "名字不能为空")
    @ApiModelProperty(value = "歌手名字")
    private String singerName;

    @ApiModelProperty(value = "性别 0女1男")
    private Boolean singerSex;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    @ApiModelProperty(value = "生日")
    private Date singerBirth;

    @ApiModelProperty(value = "地区")
    private String singerLocation;

    @ApiModelProperty(value = "简介")
    private String singerIntroduction;
}
