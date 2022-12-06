package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zzh
 * @Date 2022/4/6 15:06
 * @Version 0.1
 * @Description 每日访问量
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniqueViewDTO {
    /**
     * 日期
     */
    private String day;

    /**
     * 访问量
     */
    private Integer viewsCount;
}
