package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzh
 * @description 邮箱DTO
 * @date 2022/2/7 16:03
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    /**
     * 邮箱号
     */
    private String email;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 内嵌超链接
     */
    private String webUrl;

    /**
     * 如果是验证码邮件
     */
    private String code;
}
