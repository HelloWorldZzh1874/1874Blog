package com.zzh.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zzh.common.OnlineUserMap;
import com.zzh.common.exception.PasswordNotMatchException;
import com.zzh.common.exception.UserNotFoundException;
import com.zzh.common.utils.IpUtils;
import com.zzh.common.utils.MyPasswordEncoder;
import com.zzh.dto.LoginUserDTO;
import com.zzh.utils.SecurityUtils;
import com.zzh.entity.*;
import com.zzh.service.LoginService;
import com.zzh.service.UserInfoService;
import com.zzh.service.UserService;
import com.zzh.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;

import static com.zzh.common.constant.RedisConstant.ARTICLE_USER_LIKE;
import static com.zzh.common.constant.RedisConstant.COMMENT_USER_LIKE;

/**
 * @author zzh
 * @description TODO
 * @date 2022/3/5 11:10
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    MyPasswordEncoder passwordEncoder;

    @Autowired
    OnlineUserMap onlineUserMap;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean checkUser(String username, String password) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }
        String encodedPwd = user.getPassword();
        // 判断密码是否与数据库匹配
        if (!passwordEncoder.matches(password, encodedPwd)) {
            throw new PasswordNotMatchException();
        }
        return true;
    }

    @Override
    public void logout() {
        // 从 SecurityContextHolder 获得当前登录对象
        LoginUser principal = SecurityUtils.getCurUser();
        // 从redis移除token相关信息
        tokenService.removeToken(principal);
        // 移除map在线用户
        onlineUserMap.remove(principal.getUsername());
    }

    @Override
    public Boolean checkAccountLock(String username) {
        return userService.selectAccountNotLock(username);
    }

    @Override
    public void changeLock(String username, Boolean isNotLock) {
        userService.update(
                new User(),
                new LambdaUpdateWrapper<User>()
                        .eq(User::getUsername, username)
                        .set(User::getNotLock, isNotLock)
        );
    }

    @Override
    public LoginUserDTO tokenLogin(HttpServletRequest request) {
        Authentication authentication = SecurityUtils.getAuthentication();
        if (Objects.isNull(authentication) || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
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
        UserInfo userInfo = userInfoService.getOne(
                new LambdaQueryWrapper<UserInfo>()
                        .eq(UserInfo::getId, user.getInfoId()
                        )
        );

        // 放入在线用户组
        OnlineUser user1 = onlineUserMap.get(user.getUsername());

        // 封装在线用户对象
        OnlineUser onlineUser = OnlineUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nikeName(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .lastLogin(user.getLastLogin())
                .ipAddr(user.getIpAddr())
                .ipSource(user.getIpSource())
                .browser(user.getBrowser())
                .token(user1.getToken())
                .logoutDate(user1.getLogoutDate())
                .os(user.getOs())
                .email(userInfo.getEmail())
                .build();

        // 放入在线用户组
        onlineUserMap.put(user.getUsername(), onlineUser);

        //查询当前用户点赞过的文章id集合
        Set<Integer> articleLikeSet = (Set<Integer>) redisTemplate.boundHashOps(ARTICLE_USER_LIKE).get(user.getId().toString());
        Set<Integer> commentLikeSet = (Set<Integer>) redisTemplate.boundHashOps(COMMENT_USER_LIKE).get(user.getId().toString());

        // 封装返回对象
        return LoginUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .website(userInfo.getWebSite())
                .intro(userInfo.getIntro())
                .articleLikeSet(articleLikeSet)
                .commentLikeSet(commentLikeSet)
                .build();
    }
}
