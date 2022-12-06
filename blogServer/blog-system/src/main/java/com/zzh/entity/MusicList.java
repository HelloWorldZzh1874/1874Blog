package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author zzh
 * @since 2022-04-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("tb_music_list")
@ApiModel(value="MusicList对象", description="")
public class MusicList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = " 主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "列表id:1普通歌单,-1用户收藏歌单")
    private Integer listId;

    @ApiModelProperty(value = " 歌曲id")
    private Integer musicId;

    @ApiModelProperty(value = " 收藏歌单用户id")
    private Long userId;
}
