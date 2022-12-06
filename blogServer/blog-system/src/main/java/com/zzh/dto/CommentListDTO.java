package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zzh
 * @description 返回的回复列表
 * @date 2022/4/29 19:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentListDTO {
    /**
     * 封装的回复
     */
    private List<CommentUserDTO> commentUserDTOS;

    /**
     * 未读回复数
     */
    private Long notReadCount;
}
