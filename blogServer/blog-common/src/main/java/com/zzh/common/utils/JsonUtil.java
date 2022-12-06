package com.zzh.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zzh
 * @description json工具
 * @date 2022/2/112:32
 */
public class JsonUtil {

    /**
     * @description 返回JSON
     * @date 2022/2/1
     * @param data 输出内容
     */
    protected void writeJson(HttpServletResponse response,
                             Object data) throws IOException {
        //这里很重要，否则页面获取不到正常的JSON数据集
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        //输出JSON
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(data));
        out.flush();
        out.close();
    }
}
