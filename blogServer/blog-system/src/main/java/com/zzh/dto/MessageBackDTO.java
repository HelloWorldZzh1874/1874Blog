package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author zzh
 * @Date 2021/7/29 21:25
 * @Version 0.1
 * @Description 后台返回留言数据
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageBackDTO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户ip
     */
    private String ipAddress;

    /**
     * 用户ip地址
     */
    private String ipSource;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 留言时间
     */
    private Date createTime;

}
