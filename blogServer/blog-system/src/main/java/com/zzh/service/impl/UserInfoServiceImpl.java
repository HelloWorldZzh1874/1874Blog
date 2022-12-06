package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.common.base.FilePathEnum;
import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.common.utils.OssUtil;
import com.zzh.utils.SecurityUtils;
import com.zzh.entity.ConRoleUser;
import com.zzh.entity.LoginUser;
import com.zzh.entity.Role;
import com.zzh.entity.UserInfo;
import com.zzh.mapper.ConRoleUserMapper;
import com.zzh.mapper.RoleMapper;
import com.zzh.mapper.UserInfoMapper;
import com.zzh.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.vo.UserInfoVO;
import com.zzh.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;

import static com.zzh.common.constant.CommonConst.ANONYMOUS_USER;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    ConRoleUserMapper conRoleUserMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public void updateUserRole(UserRoleVO userRoleVO) {
        // 更改用户昵称
        UserInfo userInfo = UserInfo.builder()
                .id(userRoleVO.getUserInfoId())
                .nickname(userRoleVO.getNickname())
                .updateTime(new Date())
                .build();
        userInfoMapper.updateById(userInfo);
        // 删除用户角色重新添加
        conRoleUserMapper.delete(new LambdaQueryWrapper<ConRoleUser>()
                .eq(ConRoleUser::getUserId, userRoleVO.getId()));

        Role roleId = roleMapper.selectOne(
                new LambdaQueryWrapper<Role>().
                        select(Role::getId).
                        eq(Role::getRoleLabel, userRoleVO.getRoleName())
        );
        if(Objects.isNull(roleId) || Objects.isNull(roleId.getId())){
            throw new CommonWriteException("服务器异常！请稍后再试!");
        }
        ConRoleUser conRoleUser = ConRoleUser.builder()
                .userId(userRoleVO.getId())
                .roleId(roleId.getId())
                .build();
        conRoleUserMapper.insert(conRoleUser);
    }

    @Override
    public String updateAvatar(MultipartFile file) {
        // 从 SecurityContextHolder 获得当前登录对象
        LoginUser principal = SecurityUtils.getCurUser();
        String path = OssUtil.upload(file, FilePathEnum.AVATAR.getPath());
        // 更新用户信息
        UserInfo userInfo = UserInfo.builder()
                .id(principal.getUser().getInfoId())
                .avatar(path)
                .updateTime(new Date())
                .build();
        this.updateById(userInfo);
        return path;
    }

    @Override
    public void updateUserInfo(UserInfoVO infoVO) {
        LoginUser loginUser = SecurityUtils.getCurUser();
        UserInfo userInfo = UserInfo.builder()
                .id(loginUser.getUser().getInfoId())
                .nickname(infoVO.getNickname())
                .intro(infoVO.getIntro())
                .webSite(infoVO.getWebSite())
                .updateTime(new Date())
                .build();
        userInfoMapper.updateById(userInfo);
    }
}
