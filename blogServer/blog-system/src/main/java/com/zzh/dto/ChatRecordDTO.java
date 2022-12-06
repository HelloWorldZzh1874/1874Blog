package com.zzh.dto;

import com.zzh.entity.ChatRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/25 11:46
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatRecordDTO {
    /**
     * 聊天记录
     */
    private List<ChatRecord> chatRecordList;

    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * ip来源
     */
    private String ipSource;
}
