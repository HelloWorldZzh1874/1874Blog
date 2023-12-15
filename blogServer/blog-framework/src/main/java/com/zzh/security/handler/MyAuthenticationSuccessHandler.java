package com.zzh.security.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.common.OnlineUserMap;
import com.zzh.common.base.Result;
import com.zzh.common.utils.IpUtils;
import com.zzh.common.utils.JsonUtil;
import com.zzh.common.utils.RedisUtils;
import com.zzh.dto.LoginUserDTO;
import com.zzh.entity.*;
import com.zzh.service.UserInfoService;
import com.zzh.service.UserService;
import com.zzh.service.impl.TokenService;
import com.zzh.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.zzh.common.constant.CommonConst.REMEMBER_ME;
import static com.zzh.common.constant.RedisConstant.ARTICLE_USER_LIKE;
import static com.zzh.common.constant.RedisConstant.COMMENT_USER_LIKE;

/**
 * @author zzh
 * @description 登录成功处理器
 * @date 2022/2/12 0:38
 */

@Component
public class MyAuthenticationSuccessHandler extends JsonUtil implements AuthenticationSuccessHandler {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OnlineUserMap onlineUserMap;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取rememberMe参数
        Boolean rememberMe = Boolean.valueOf(request.getParameter(REMEMBER_ME));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        loginUser.setRememberMe(rememberMe);
        // 得到登录用户数据
        User user = loginUser.getUser();
        // 更新用户登录信息
        String ipAddr = IpUtils.getIpAddr(request);
        String ipSource = IpUtils.getIpSource(ipAddr);
        user.setIpAddr(ipAddr);
        user.setIpSource(ipSource);
        user.setLastLogin(DateUtil.date());
        // 解析请求头得到浏览器和系统信息
        CommonUtils.parseAgent(request, user);

        // 跟新用户相关信息
        userService.updateLoginInfo(user);

        // 创建token
        String token = tokenService.createToken(loginUser);

        UserInfo userInfo = userInfoService.getOne(
                new LambdaQueryWrapper<UserInfo>()
                        .eq(UserInfo::getId, user.getInfoId()
                        )
        );

        // 封装在线用户对象
        OnlineUser onlineUser = OnlineUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nikeName(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .lastLogin(user.getLastLogin())
                .logoutDate(rememberMe?DateUtil.offsetDay(user.getLastLogin(),7):DateUtil.offsetDay(user.getLastLogin(),1))
                .ipAddr(user.getIpAddr())
                .ipSource(user.getIpSource())
                .browser(user.getBrowser())
                .os(user.getOs())
                .email(userInfo.getEmail())
                .token(token)
                .build();

        // 放入在线用户组
        onlineUserMap.put(user.getUsername(), onlineUser);
        //查询当前用户点赞过的文章id集合
        Set<Integer> articleLikeSet = (Set<Integer>) redisTemplate.boundHashOps(ARTICLE_USER_LIKE).get(user.getId().toString());
        Set<Integer> commentLikeSet = (Set<Integer>) redisTemplate.boundHashOps(COMMENT_USER_LIKE).get(user.getId().toString());
        // 封装返回对象
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .website(userInfo.getWebSite())
                .intro(userInfo.getIntro())
                .token(token)
                .articleLikeSet(articleLikeSet)
                .commentLikeSet(commentLikeSet)
                .build();
        Result result = Result.success().put("data", loginUserDTO);
        // 返回
        this.writeJson(response, result);
    }
}
