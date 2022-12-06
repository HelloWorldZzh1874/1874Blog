package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzh
 * @description 撤回消息DTO
 * @date 2022/4/25 13:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecallMsgDTO {

    /**
     * 消息id
     */
    private Integer id;

    /**
     * 是否为语音
     */
    private Boolean isVoice;
}
