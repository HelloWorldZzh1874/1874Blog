package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zzh
 * @Date 2021/8/9 10:37
 * @Version 0.1
 * @Description 回复数量
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCountDTO {
    /**
     * 评论id
     */
    private Integer commentId;

    /**
     * 回复数量
     */
    private Integer replyCount;
}
