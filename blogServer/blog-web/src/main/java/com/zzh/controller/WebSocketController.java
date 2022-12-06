package com.zzh.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.common.base.FilePathEnum;
import com.zzh.common.exception.WebSocketException;
import com.zzh.common.utils.BeanCopyUtil;
import com.zzh.common.utils.IpUtils;
import com.zzh.common.utils.OssUtil;
import com.zzh.dto.ChatRecordDTO;
import com.zzh.dto.RecallMsgDTO;
import com.zzh.dto.WebSocketMsgDTO;
import com.zzh.entity.ChatRecord;
import com.zzh.mapper.ChatRecordMapper;
import com.zzh.vo.VoiceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.zzh.common.constant.ChatEnum.*;

/**
 * @author zzh
 * @description WebSocket通信
 * @date 2022/4/25 11:23
 */
@Controller
@ServerEndpoint(value = "/websocket", configurator = WebSocketController.ChatConfigurator.class)
public class WebSocketController {
    /**
     * 用户session
     */
    private Session session;

    /**
     * 用户session集合--使用第三代集合线程安全
     */
    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<>();

    private static ChatRecordMapper chatRecordMapper;

    @Autowired
    public void setChatRecordDao(ChatRecordMapper chatRecordMapper) {
        WebSocketController.chatRecordMapper = chatRecordMapper;
    }

    /**
     * 重写equals和hashcode用于比较当前session是否是同一个
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSocketController that = (WebSocketController) o;
        return Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session);
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws IOException {
        // 加入连接
        this.session = session;
        webSocketSet.add(this);
        // 更新在线人数
        updateOnlineCount();
        // 加载历史聊天记录
        ChatRecordDTO chatRecordDTO = listChartRecords(endpointConfig);
        // 发送消息
        WebSocketMsgDTO messageDTO = WebSocketMsgDTO.builder()
                .type(HISTORY_RECORD.getType())
                .data(chatRecordDTO)
                .build();
        // 防止多个线程调用导致前台收不到消息
        synchronized (session) {
            session.getBasicRemote().sendText(JSON.toJSONString(messageDTO));
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        // 更新在线人数
        webSocketSet.remove(this);
        updateOnlineCount();
    }

    /**
     * 更新在线人数
     *
     * @throws IOException io异常
     */
    private void updateOnlineCount() throws IOException {
        WebSocketMsgDTO messageDTO = WebSocketMsgDTO.builder()
                .type(ONLINE_COUNT.getType())
                .data(webSocketSet.size())
                .build();
        for (WebSocketController webSocketController : webSocketSet) {
            synchronized (webSocketController.session) {
                webSocketController.session.getBasicRemote().sendText(JSON.toJSONString(messageDTO));
            }
        }
    }


    /**
     * 加载历史聊天记录
     *
     * @param endpointConfig 配置
     * @return 加载历史聊天记录
     */
    private ChatRecordDTO listChartRecords(EndpointConfig endpointConfig) {
        // 加载历史消息(昨天的)
        List<ChatRecord> chatRecordList = chatRecordMapper.selectList(new LambdaQueryWrapper<ChatRecord>()
                .ge(ChatRecord::getCreateTime, DateUtil.yesterday()));
        String ipAddr = endpointConfig.getUserProperties().get(ChatConfigurator.HEADER_NAME).toString();
        return ChatRecordDTO.builder()
                .chatRecordList(chatRecordList)
                .ipAddr(ipAddr)
                .ipSource(IpUtils.getIpSource(ipAddr))
                .build();
    }

    /**
     * 获取客户端真实ip
     */
    public static class ChatConfigurator extends ServerEndpointConfig.Configurator {

        public static String HEADER_NAME = "ip";

        @Override
        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
            try {
                HttpSession session = (HttpSession) request.getHttpSession();
                String ipAddress = (String) session.getAttribute("ip");
                // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
                // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
                if (ipAddress != null && ipAddress.length() > 15) {
                    // = 15
                    if (ipAddress.indexOf(",") > 0) {
                        ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                    }
                }
                sec.getUserProperties().put(HEADER_NAME, ipAddress);
            } catch (Exception e) {
                sec.getUserProperties().put(HEADER_NAME, "未知ip");
            }
        }
    }

    /**
     * 收到客户端通知后调用的方法
     *
     * @param message 客户端的通知
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        WebSocketMsgDTO messageDTO = JSON.parseObject(message, WebSocketMsgDTO.class);
        // 判断通知类型
        switch (Objects.requireNonNull(getChatType(messageDTO.getType()))) {
            case SEND_MESSAGE:
                // 发送消息
                ChatRecord chatRecord = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), ChatRecord.class);
                chatRecordMapper.insert(chatRecord);
                messageDTO.setData(chatRecord);
                for (WebSocketController webSocketController : webSocketSet) {
                    synchronized (webSocketController.session) {
                        webSocketController.session.getBasicRemote().sendText(JSON.toJSONString(messageDTO));
                    }
                }
                break;
            case RECALL_MESSAGE:
                // 撤回消息
                RecallMsgDTO recallMessage = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), RecallMsgDTO.class);
                // 删除记录
                deleteRecord(recallMessage.getId());
                for (WebSocketController webSocketController : webSocketSet) {
                    synchronized (webSocketController.session) {
                        webSocketController.session.getBasicRemote().sendText(JSON.toJSONString(messageDTO));
                    }
                }
                break;
            case HEART_BEAT:
                // 心跳消息
                messageDTO.setData("pong");
                session.getBasicRemote().sendText(JSON.toJSONString(JSON.toJSONString(messageDTO)));
            default:
                break;
        }
    }

    /**
     * 发送语音
     * 此业务由其他controller执行
     *
     * @param voiceVo 语音路径
     */
    public void sendVoice(VoiceVo voiceVo) throws IOException {
        // 上传语音文件
        String content = OssUtil.upload(voiceVo.getFile(), FilePathEnum.VOICE.getPath());
        voiceVo.setContent(content);
        // 保存记录
        ChatRecord chatRecord = BeanCopyUtil.copyObject(voiceVo, ChatRecord.class);
        chatRecordMapper.insert(chatRecord);
        // 发送消息
        WebSocketMsgDTO messageDTO = WebSocketMsgDTO.builder()
                .type(VOICE_MESSAGE.getType())
                .data(chatRecord)
                .build();
        for (WebSocketController webSocketController : webSocketSet) {
            synchronized (webSocketController.session) {
                webSocketController.session.getBasicRemote().sendText(JSON.toJSONString(messageDTO));
            }
        }
    }

    /**
     * 删除记录
     *
     * @param id ID
     */
    private void deleteRecord(Integer id) {
        chatRecordMapper.deleteById(id);
    }


    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Throwable error) {
        throw new WebSocketException(error.getMessage());
    }
}
