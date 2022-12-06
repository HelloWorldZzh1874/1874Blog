package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.Result;
import com.zzh.service.MessageService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.MessageVO;
import com.zzh.vo.VoiceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.zzh.common.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static com.zzh.aop.OptTypeConst.REMOVE;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/message")
@Api(tags = "留言模块")
public class MessageController extends BaseController {

    @Autowired
    MessageService messageService;

    @Autowired
    WebSocketController webSocketController;

    /**
     * @description 查询后台留言
     * @date 2022/3/15
     * @param conditionVO 查询条件
     * @return
     */
    @ApiOperation("查询后台留言")
    @PostMapping("/admin/getBackMessages")
    public Result getBackMessages(ConditionVO conditionVO){
        return Result.success(messageService.getBackMessages(conditionVO));
    }

    /**
     * @description 删除留言
     * @date 2022/3/15
     * @param messageIdList 删除id列表
     * @return
     */
    @ApiOperation(value = "删除留言")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/deleteMessages")
    public Result deleteMessages(@RequestBody List<Integer> messageIdList){
        messageService.removeByIds(messageIdList);
        return Result.success();
    }

    /**
     * @description 获取留言列表
     * @date 2022/4/22
     * @param
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "获取留言列表")
    @GetMapping("/getList")
    public Result getMessages(){
        return Result.success(messageService.getMessageList());
    }

    /**
     * @description 添加留言
     * @date 2022/4/22
     * @param messageVO
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "添加留言")
    @PostMapping("/add")
    public Result addMessage(@Validated @RequestBody MessageVO messageVO, HttpServletRequest request){
        messageService.addMessage(messageVO,request);
        return Result.success();
    }

    @ApiOperation(value = "上传语音")
    @ApiImplicitParam(name = "file", value = "语音文件", required = true, dataType = "MultipartFile")
    @PostMapping("/voice")
    public void saveVoice(VoiceVo voiceVo) throws IOException {
        webSocketController.sendVoice(voiceVo);
    }
}

