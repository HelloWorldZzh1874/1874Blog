package com.zzh.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.aysnc.AsyncManager;
import com.zzh.common.OnlineUserMap;
import com.zzh.common.constant.RoleEnum;
import com.zzh.common.constant.CommonConst;
import com.zzh.common.exception.EmailUncorrectedException;
import com.zzh.common.exception.SaveOrUpdateException;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.common.utils.MyPasswordEncoder;
import com.zzh.common.utils.RedisUtils;
import com.zzh.dto.EmailDTO;
import com.zzh.dto.UserBackDTO;
import com.zzh.dto.UserOnlineDTO;
import com.zzh.entity.*;
import com.zzh.mapper.RoleMapper;
import com.zzh.mapper.UserMapper;
import com.zzh.service.ConRoleUserService;
import com.zzh.service.UserInfoService;
import com.zzh.service.UserService;
import com.zzh.utils.MailUtil;
import com.zzh.utils.SecurityUtils;
import com.zzh.vo.AddAdminVO;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.PasswordVO;
import com.zzh.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.zzh.common.constant.CommonConst.FORGET_PWD_CODE_EMAIL;
import static com.zzh.common.constant.RedisConstant.CODE_EXPIRE_TIME;
import static com.zzh.common.constant.RedisConstant.CODE_KEY;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    OnlineUserMap onlineUserMap;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    TokenService tokenService;

    @Autowired
    MyPasswordEncoder myPasswordEncoder;

    @Autowired
    ConRoleUserService conRoleUserService;

    @Autowired
    AsyncManager asyncManager;

    @Autowired
    MyPasswordEncoder passwordEncoder;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void updateLoginInfo(User user) {
        userMapper.updateById(user);
    }

    @Override
    public Boolean selectAccountNotLock(String username) {
        return userMapper.selectAccountNotLock(username);
    }

    @Override
    public PageInfo<UserBackDTO> listBackUsers(ConditionVO conditionVO) {
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        // 获取用户列表
        List<UserBackDTO> userBackDTOList = userMapper.listBackUsers(conditionVO);
        return new PageInfo<>(userBackDTOList);
    }

    @Override
    public boolean forceLogout(Long userId,boolean self) {
        if(SecurityUtils.getCurUser().getUser().getId().equals(userId) && !self){
            return false;
        }
        // 查找在线用户
        User user = this.getOne(new LambdaQueryWrapper<User>()
                .select(User::getUsername)
                .eq(User::getId, userId)
        );
        if (Objects.isNull(user)) {
            return false;
        }
        // 获取在线用户的token
        OnlineUser onlineUser = onlineUserMap.get(user.getUsername());
        // 用户用户不在线则甚么也不做
        if (Objects.isNull(onlineUser)) {
            return false;
        }
        // 清除在线用户列表和在线用户redis
        if(Objects.nonNull(tokenService.parseLoginUser(onlineUser.getToken()))){
            tokenService.removeToken(onlineUser.getToken());
        }
        onlineUserMap.remove(user.getUsername());
        return true;
    }

    @Override
    public PageInfo<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO) {
        // 遍历得到要查找的在线用户数据
        List<UserOnlineDTO> collect = onlineUserMap.values().stream().map(item -> {
            // 如果有搜索关键字并且当前遍历item不是搜索用户则不存入list
            if (Objects.nonNull(conditionVO.getKeywords()) && !item.getUsername().contains(conditionVO.getKeywords()) && DateUtil.compare(DateUtil.date(),item.getLogoutDate())>=0) {
                return null;
            }
            return UserOnlineDTO.builder().nickname(item.getNikeName())
                    .id(item.getId())
                    .avatar(item.getAvatar())
                    .ipAddr(item.getIpAddr())
                    .ipSource(item.getIpSource())
                    .browser(item.getBrowser())
                    .os(item.getOs())
                    .lastLoginTime(item.getLastLogin())
                    .build();
        }).collect(Collectors.toList());
        if (collect.isEmpty() || Objects.isNull(collect.get(0))) {
            return new PageInfo<>();
        }
        // 获取分页参数
        int current = (conditionVO.getCurrent() - 1) * conditionVO.getSize();
        int size = collect.size() > conditionVO.getSize() ? current + conditionVO.getSize() : collect.size();

        // 根据参数获取分页数据
        List<UserOnlineDTO> userOnlineList = collect.subList((conditionVO.getCurrent() - 1) * conditionVO.getSize(), size);
        // 新建分页对象并返回
        PageInfo<UserOnlineDTO> pageInfo = new PageInfo<>(userOnlineList);
        pageInfo.setTotal(collect.size());
        return pageInfo;

    }

    @Override
    public void updateAdminPassword(PasswordVO passwordVO) {
        // 获得当前登录用户
        LoginUser loginUser = SecurityUtils.getCurUser();
        Long id = loginUser.getUser().getId();
        // 查询用户密码是否匹配
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, id));
        if (Objects.isNull(user)) {
            throw new CommonWriteException("当前用户不存在!");
        }
        // 正确则修改密码，错误则提示不正确
        if (myPasswordEncoder.matches(passwordVO.getOldPassword(), user.getPassword())) {
            User userAuth = User.builder()
                    .id(id)
                    .password(myPasswordEncoder.encode(passwordVO.getNewPassword()))
                    .build();
            userMapper.updateById(userAuth);
            // 强制退出重新登录
            this.forceLogout(id,true);
        } else {
            throw new SaveOrUpdateException("旧密码不正确");
        }
    }

    @Override
    public void deleteAccount(Long id) {
        // 首先强制下线该用户
        this.forceLogout(id,false);
        // 删除用户角色信息
        conRoleUserService.remove(new LambdaQueryWrapper<ConRoleUser>().eq(ConRoleUser::getUserId, id));
        // 删除用户账户信息
        User one = this.getOne(new LambdaQueryWrapper<User>().select(User::getInfoId).eq(User::getId, id));
        userInfoService.remove(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, one.getInfoId()));
        // 删除账号
        this.removeById(id);
    }

    @Override
    public void sendEmail(String email, Integer type) {
        // 如果是忘记密码，则如果邮箱正确但数据库存在此邮箱返回false(取反则为true)的情况，可保证后一个条件为false以发送验证码
        if(!checkEmail(email) && !FORGET_PWD_CODE_EMAIL.equals(type)){
            throw new CommonWriteException("该邮箱已被注册!");
        }
        // 生成六位随机验证码发送
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        EmailDTO emailDTO = EmailDTO.builder()
                .email(email)
                .subject("1874BLOG验证码")
                .code(code.toString())
                .build();
        asyncManager.sendMail(emailDTO, type);
        // 将验证码存入redis，设置过期时间为15分钟
        redisUtils.set(CODE_KEY + email, code.toString(), CODE_EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    @Override
    public void registerUser(UserVO userVO) {
        // 校验账号是否合法
        checkUser(userVO);
        if(!checkEmail(userVO.getEmail())){
            throw new CommonWriteException("该邮箱已被注册!");
        }
        // 新增用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(userVO.getEmail())
                .nickname("用户" + UUID.randomUUID())
                .avatar(CommonConst.DEFAULT_AVATAR)
                .createTime(new Date())
                .build();
        // 数据插入数据库
        userInfoService.save(userInfo);
        // 新增用户账号
        User userAuth = User.builder()
                .infoId(userInfo.getId())
                .username(userVO.getEmail())
                .password(passwordEncoder.encode(userVO.getPassword()))
                .build();
        userMapper.insert(userAuth);
        // 绑定角色信息
        saveUserRole(userAuth);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(UserVO userVO) {
        // 校验账号是否合法
        if (checkEmail(userVO.getEmail())) {
            throw new CommonWriteException("邮箱尚未注册!");
        }
        // 根据用户名修改密码(邮箱==用户名)
        userMapper.update(new User(), new LambdaUpdateWrapper<User>()
                .set(User::getPassword, passwordEncoder.encode(userVO.getPassword()))
                .eq(User::getUsername, userVO.getEmail()));
    }

    @Override
    public void addAdmin(AddAdminVO addAdminVO) {
        // 是否有相同注册邮箱
        if (!checkEmail(addAdminVO.getEmail())) {
            throw new CommonWriteException("该邮箱已经注册！");
        }
        // 查询角色对应id
        Role roleId = roleMapper.selectOne(
                new LambdaQueryWrapper<Role>().
                        select(Role::getId).
                        eq(Role::getRoleLabel, addAdminVO.getRole())
        );
        if(Objects.isNull(roleId) || Objects.isNull(roleId.getId())){
            throw new CommonWriteException("服务器异常！请稍再试!");
        }
        // 保存用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(addAdminVO.getEmail())
                .nickname(addAdminVO.getNickName())
                .avatar(CommonConst.DEFAULT_AVATAR)
                .createTime(new Date())
                .build();
        // 数据插入数据库
        userInfoService.save(userInfo);
        // 新增用户账号
        User userAuth = User.builder()
                .infoId(userInfo.getId())
                .username(addAdminVO.getEmail())
                .password(passwordEncoder.encode(addAdminVO.getPassword()))
                .build();
        userMapper.insert(userAuth);
        // 保存角色
        ConRoleUser conRoleUser = ConRoleUser.builder()
                .userId(userAuth.getId())
                .roleId(roleId.getId())
                .build();
        conRoleUserService.save(conRoleUser);
    }

    private Boolean checkEmail(String email) {
        // 校验账号是否合法
        if (!MailUtil.checkEmail(email)) {
            throw new EmailUncorrectedException("请输入正确邮箱");
        }
        UserInfo one = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getEmail, email));
        // 如果为null则返回true，表示可以注册
        return Objects.isNull(one);
    }

    /**
     * 前端注册校验用户数据是否合法
     *
     * @param user 用户数据
     * @return 合法状态
     */
    private void checkUser(UserVO user) {
        if (!user.getCode().equals(redisUtils.get(CODE_KEY + user.getEmail()))) {
            throw new CommonWriteException("验证码错误！");
        }
    }

    /**
     * 绑定用户角色
     *
     * @param user 用户信息
     */
    private void saveUserRole(User user) {
        ConRoleUser conRoleUser = ConRoleUser.builder()
                .userId(user.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        conRoleUserService.save(conRoleUser);
    }
}
