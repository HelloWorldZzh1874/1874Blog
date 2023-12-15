package com.zzh.common.exception.baseException;

import com.zzh.common.base.Result;
import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.EmailUncorrectedException;
import com.zzh.common.exception.RefuseAnonymousUserException;
import com.zzh.common.exception.WebSocketException;
import com.zzh.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zzh
 * @description 全局异常处理
 * @date 2022/1/2719:27
 */

@ControllerAdvice
public class GlobalExceptionHandler extends JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*============== 处理需要直接相应页面的异常 =================*/
    /**
     * 匿名用户相关异常
     * @param req 请求
     * @param resp 相应
     * @param e 异常信息
     * @throws IOException 写入页面的IO异常
     */
    @ExceptionHandler(value = RefuseAnonymousUserException.class)
    @ResponseBody
    public void refuseExceptionHandler(HttpServletRequest req, HttpServletResponse resp, MyException e) throws IOException {
        logger.error(e.getMessage());
        this.writeJson(resp,Result.error(e.getErrorCode(),e.getErrorMsg()));
    }


    /**
     * 需要相应异常信息的异常
     * @param req 请求
     * @param resp 相应
     * @param e 异常信息
     * @throws IOException 写入页面的IO异常
     */
    @ExceptionHandler(value = CommonWriteException.class)
    @ResponseBody
    public void commonWriteJsonException(HttpServletRequest req, HttpServletResponse resp, MyException e) throws IOException {
        logger.error(e.getMessage());
        this.writeJson(resp,Result.error(e.getErrorCode(),e.getErrorMsg()));
    }


    /**
     * @description 邮箱相关异常错误
     * @date 2022/2/22
     * @param req
     * @param resp
     * @param e
     * @return void
     */
    @ExceptionHandler(value = EmailUncorrectedException.class)
    @ResponseBody
    public void emailUncorrectedExceptionHandler(HttpServletRequest req, HttpServletResponse resp, MyException e) throws IOException {
        logger.error(e.getMessage());
        this.writeJson(resp,Result.error(HttpStatus.UNCORRECTED,e.getErrorMsg()));
    }

    /**
     * @description WebSocket异常
     * @date 2022/4/25
     * @param req
     * @param resp
     * @param e
     * @return void
     */
    @ExceptionHandler(value = WebSocketException.class)
    @ResponseBody
    public void webSocketExceptionHandler(HttpServletRequest req, HttpServletResponse resp, MyException e) throws IOException {
        logger.error(e.getMessage());
        this.writeJson(resp,Result.error(HttpStatus.UNCORRECTED,e.getErrorMsg()));
    }

}
