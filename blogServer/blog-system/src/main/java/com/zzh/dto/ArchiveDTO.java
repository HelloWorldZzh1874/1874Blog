package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author zzh
 * @Date 2021/8/7 21:54
 * @Version 0.1
 * @Description 归档文章
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    private Date createTime;

}
