package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zzh
 * @Date 2021/3/9 22:03
 * @Version 0.1
 * @Description 标签返回类型
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;
}
