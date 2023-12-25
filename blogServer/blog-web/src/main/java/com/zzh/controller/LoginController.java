package com.zzh.controller;

import com.zzh.common.base.BaseController;
import com.zzh.common.base.Result;
import com.zzh.common.constant.HttpStatus;
import com.zzh.common.utils.RedisUtils;
import com.zzh.dto.LoginUserDTO;
import com.zzh.service.LoginService;
import com.zzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zzh
 * @description 登录控制器
 * @date 2022/1/2523:32
 */

@RestController
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @description 退出登录
     * @date 2022/1/27
     * @return com.zzh.common.base.Result
     */
    @PostMapping("/user/logout")
    public Result logout() {
        loginService.logout();
        return Result.success("注销成功!");
    }

    /**
     * @description 记住密码的用户取出用户信息
     * @date 2022/2/19
     * @return 返回用户信息
     */
    @GetMapping("/verifyToken")
    public Result getLoginInfo(HttpServletRequest request){
        LoginUserDTO loginUserDTO = loginService.tokenLogin(request);
        if(Objects.isNull(loginUserDTO)){
            return Result.success(HttpStatus.NO_CONTENT,null);
        }
        return Result.success(loginUserDTO);
    }
}
