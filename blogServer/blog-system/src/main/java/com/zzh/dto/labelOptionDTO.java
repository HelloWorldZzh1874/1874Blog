package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author zzh
 * @Date 2021/7/30 23:11
 * @Version 0.1
 * @Description 角色选项DTO
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class labelOptionDTO {
    /**
     * 选项id
     */
    private Integer id;

    /**
     * 选项名
     */
    private String label;

    /**
     * 子选项
     */
    private List<labelOptionDTO> children;
}
