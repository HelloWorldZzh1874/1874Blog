package com.zzh.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zzh
 * @description websocket枚举类型
 * @date 2022/4/25 11:37
 */

@Getter
@AllArgsConstructor
public enum ChatEnum {
    /**
     * 在线人数
     */
    ONLINE_COUNT(1, "在线人数"),
    /**
     * 历史记录
     */
    HISTORY_RECORD(2, "历史记录"),
    /**
     * 发送消息
     */
    SEND_MESSAGE(3, "发送消息"),
    /**
     * 撤回消息
     */
    RECALL_MESSAGE(4, "撤回消息"),
    /**
     * 语音消息
     */
    VOICE_MESSAGE(5, "语音消息"),

    /**
     * 连接回调
     */
    HEART_BEAT(6,"连接回调");

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据类型获取枚举
     *
     * @param type 类型
     * @return 枚举
     */
    public static ChatEnum getChatType(Integer type) {
        for (ChatEnum chatType : ChatEnum.values()) {
            if (chatType.getType().equals(type)) {
                return chatType;
            }
        }
        return null;
    }
}
