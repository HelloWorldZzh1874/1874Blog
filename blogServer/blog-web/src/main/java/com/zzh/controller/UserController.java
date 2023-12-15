package com.zzh.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zzh.aop.OptLog;
import com.zzh.common.base.BaseController;
import com.zzh.common.base.Result;
import com.zzh.entity.User;
import com.zzh.service.UserService;
import com.zzh.vo.AddAdminVO;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.PasswordVO;
import com.zzh.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.zzh.aop.OptTypeConst.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * @param condition 条件
     * @return 返回分页数据
     * @description 查询后台用户列表
     * @Date 2022/3/9
     */
    @ApiOperation(value = "查看后台用户列表")
    @PostMapping("/admin/users")
    public Result listBackUsers(ConditionVO condition) {
        return Result.success(userService.listBackUsers(condition));
    }

    /**
     * @param userId  用户id
     * @param enabled 用户账户禁用状态 1可用 0禁用
     * @return
     * @description 更改用户账户状态
     * @date 2022/3/23
     */
    @ApiOperation(value = "改变用户禁用状态")
    @PutMapping("/admin/enabledState/{id}")
    @OptLog(optType = UPDATE)
    public Result changeEnable(@PathVariable("id") Long userId, Boolean enabled) {
        userService.update(new User(), new LambdaUpdateWrapper<User>().set(User::getEnabled, enabled).eq(User::getId, userId));
        if (!enabled) {
            if (!userService.forceLogout(userId,false)) {
                return Result.error("下线失败，请稍后再试!");
            }
        }
        return Result.success();
    }

    /**
     * @param conditionVO 分页条件
     * @return 返回分页数据
     * @description 获取的在线用户
     * @date 2022/3/18
     */
    @ApiOperation(value = "统计在线用户")
    @PostMapping("/admin/online")
    public Result getOnlineUser(ConditionVO conditionVO) {
        return Result.success(userService.listOnlineUsers(conditionVO));
    }

    /**
     * @param userId 用户id
     * @description 强制下线用户
     * @date 2022/3/25
     */
    @ApiOperation(value = "强制下线")
    @OptLog(optType = UPDATE)
    @PutMapping("/admin/forceOffline")
    public Result forceOffline(Long userId) {
        if (!userService.forceLogout(userId,false)) {
            return Result.error("下线失败，无法下线自己!");
        }
        return Result.success();
    }

    /**
     * @param passwordVO 用户密码对象，包括旧密码和新密码
     * @return
     * @description 修改管理员密码
     * @date 2022/4/5
     */
    @ApiOperation(value = "修改管理员密码")
    @PutMapping("/admin/setPassword")
    public Result updateAdminPassword(@Valid @RequestBody PasswordVO passwordVO) {
        userService.updateAdminPassword(passwordVO);
        return Result.success("修改成功!请重新登录~");
    }

    /**
     * @param id 账户id
     * @return com.zzh.common.base.Result
     * @description 删除账号
     * @date 2022/4/18
     */
    @DeleteMapping("/deleteAccount")
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除账号")
    public Result deleteAccount(@RequestParam Long id) {
        userService.deleteAccount(id);
        return Result.success();
    }


    /**
     * 发送验证码
     * @param email 用户注册邮箱
     * @return
     */
    @ApiOperation(value = "发送邮箱验证码")
    @PostMapping("/register/code")
    public Result sendCode(String email,Integer type) {
        userService.sendEmail(email,type);
        return Result.success("发送成功!");
    }

    /**
     * 用户注册
     * @param userVO 用户注册数据
     * @return
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result registerUser(@Validated UserVO userVO){
        userService.registerUser(userVO);
        return Result.success();
    }

    /**
     * @description 用户修改密码
     * @date 2022/4/25
     * @param user 注册或修改密码Vo
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "用户修改密码")
    @PutMapping("/password")
    public Result updatePassword(@Valid @RequestBody UserVO user) {
        userService.updatePassword(user);
        return Result.success("修改成功!");
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加管理员")
    @PostMapping("/addAdmin")
    public Result addAdmin(AddAdminVO addAdmin){
        userService.addAdmin(addAdmin);
        return Result.success();
    }
}


