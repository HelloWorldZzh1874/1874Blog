package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/2511:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketMsgDTO {
    /**
     * 类型
     */
    private Integer type;

    /**
     * 数据
     */
    private Object data;
}
