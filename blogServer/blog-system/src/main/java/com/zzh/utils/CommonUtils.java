package com.zzh.utils;

import com.zzh.entity.User;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zzh.common.constant.HttpCommonConstant.AGENT;

/**
 * @author zzh
 * @description TODO
 * @date 2022/3/613:44
 */
public class CommonUtils {

    /**
     * @param request 请求
     * @description 解析请求头获取设备信息
     * @date 2022/2/2
     */
    public static void parseAgent(HttpServletRequest request, User user) {
        // 得到请求头内容
        String agent = request.getHeader(AGENT);
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);

        //获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 获取浏览器对象
        Browser browser = userAgent.getBrowser();
        // 注入相关信息
        user.setBrowser(browser.getName());
        user.setOs(operatingSystem.getName());
    }
}
