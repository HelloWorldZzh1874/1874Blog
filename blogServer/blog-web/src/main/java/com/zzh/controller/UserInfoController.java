package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.Result;
import com.zzh.service.UserInfoService;
import com.zzh.vo.UserInfoVO;
import com.zzh.vo.UserRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.zzh.common.base.BaseController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.zzh.aop.OptTypeConst.SAVE;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
@RestController
@Api(tags = "用户信息")
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 修改用户角色
     *
     * @param userRoleVO 要修改的数据
     */
    @ApiOperation(value = "修改用户信息和角色信息")
    @OptLog(optType = SAVE)
    @PutMapping("/admin/updateUserInfo")
    public Result updateRole(@Validated @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return Result.success();
    }

    /**
     * @description 修改用户头像
     * @date 2022/4/5
     * @param file 头像文件
     * @return 返回文件存储路径
     */
    @ApiOperation("修改用户头像")
    @PostMapping("/avatar")
    public Result updateUserAvatar(MultipartFile file) {
        String avatar = userInfoService.updateAvatar(file);
        return Result.success().setData(avatar);
    }

    /**
     * @description 修改用户资料
     * @date 2022/4/5
     * @param userInfoVO 用户信息数据
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "修改用户资料")
    @PutMapping("/setInfo")
    public Result updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.success("修改成功!");
    }
}

