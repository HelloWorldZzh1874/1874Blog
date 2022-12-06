package com.zzh.service;

import com.github.pagehelper.PageInfo;
import com.zzh.dto.MessageBackDTO;
import com.zzh.dto.MessageDTO;
import com.zzh.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.MessageVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-15
 */
public interface MessageService extends IService<Message> {
    /**
     * 获取后台留言列表
     * @param conditionVO
     * @return
     */
    PageInfo<MessageBackDTO> getBackMessages(ConditionVO conditionVO);

    /**
     * 获取留言列表
     * @return
     */
    List<MessageDTO> getMessageList();

    /**
     * 添加留言
     * @param messageVO 添加对象
     */
    void addMessage(MessageVO messageVO, HttpServletRequest request);
}
