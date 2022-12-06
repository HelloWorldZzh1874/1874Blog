package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzh
 * @description 未读消息
 * @date 2022/4/29 18:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentUserDTO {
    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 评论所属文章id
     */
    private Long articleId;

    /**
     * 评论所属文章标题
     */
    private String articleTitle;

    /**
     * 评论用户昵称
     */
    private String nickName;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 是否已读
     */
    private Boolean isRead;
}
