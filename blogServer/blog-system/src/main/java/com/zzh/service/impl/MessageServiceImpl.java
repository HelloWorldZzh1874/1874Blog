package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.common.utils.BeanCopyUtil;
import com.zzh.common.utils.IpUtils;
import com.zzh.dto.MessageBackDTO;
import com.zzh.dto.MessageDTO;
import com.zzh.entity.Message;
import com.zzh.mapper.MessageMapper;
import com.zzh.service.MessageService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.MessageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-15
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public PageInfo<MessageBackDTO> getBackMessages(ConditionVO conditionVO) {
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        // 构建查询语句
        LambdaQueryWrapper<Message> messageLambdaQueryWrapper = new LambdaQueryWrapper<Message>()
                .select(Message::getId, Message::getNickname, Message::getAvatar, Message::getIpAddress, Message::getIpSource, Message::getMessageContent, Message::getCreateTime)
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Message::getNickname, conditionVO.getKeywords())
                .orderByDesc(Message::getCreateTime);
        List<Message> messagePage = messageMapper.selectList(messageLambdaQueryWrapper);
        // 转换DTO
        List<MessageBackDTO> messageBackDTOList = BeanCopyUtil.copyList(messagePage, MessageBackDTO.class);
        return new PageInfo<>(messageBackDTOList);
    }

    @Override
    public List<MessageDTO> getMessageList() {
        // 查询留言列表
        List<Message> messageList = messageMapper.selectList(new LambdaQueryWrapper<Message>()
                .select(Message::getId, Message::getNickname, Message::getAvatar, Message::getMessageContent, Message::getTime));
        return BeanCopyUtil.copyList(messageList, MessageDTO.class);
    }

    @Override
    public void addMessage(MessageVO messageVO, HttpServletRequest request) {
        // 获取用户ip
        String ipAddr = IpUtils.getIpAddr(request);
        String ipSource = IpUtils.getIpSource(ipAddr);
        // 封装成数据库对
        Message message = Message.builder()
                .nickname(messageVO.getNickname())
                .avatar(messageVO.getAvatar())
                .messageContent(messageVO.getMessageContent())
                .time(messageVO.getTime())
                .createTime(new Date())
                .ipAddress(ipAddr)
                .ipSource(ipSource).build();
        messageMapper.insert(message);
    }
}
