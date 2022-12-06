package com.zzh.service;

import com.github.pagehelper.PageInfo;
import com.zzh.dto.UserBackDTO;
import com.zzh.dto.UserOnlineDTO;
import com.zzh.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.AddAdminVO;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.PasswordVO;
import com.zzh.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
public interface UserService extends IService<User> {
    /**
     * 更新用户登录相关信息
     * @param user
     */
    void updateLoginInfo(User user);

    /**
     * 查找账户锁定状态
     * @param username 用户名
     * @return 返回是否锁定布尔值
     */
    Boolean selectAccountNotLock(String username);

    /**
     * 查询后台用户列表
     *
     * @param conditionVO 条件
     * @return 用户列表
     */
    PageInfo<UserBackDTO> listBackUsers(ConditionVO conditionVO);

    /**
     * 强制用户下线
     * @date 2022/3/23
     * @param userId 下线用户id
     * @param self 是否剔除自己
     * @return 返回成功与否
     */
    boolean forceLogout(Long userId,boolean self);

    /**
     * 查看在线用户列表
     * @param conditionVO 条件
     * @return 在线用户列表
     */
    PageInfo<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO);

    /**
     * 修改管理员密码
     * @param passwordVO 旧密码各新密码
     */
    void updateAdminPassword(PasswordVO passwordVO);

    /**
     * 删除账户
     * @param id 账户id
     */
    void deleteAccount(Long id);

    /**
     * 发送邮箱
     * @param email 用户名，也是用户邮箱
     * @param type 邮箱类型
     */
    void sendEmail(String email,Integer type);


    /**
     * 用户注册
     * @param userVO 用户注册数据
     * @return
     */
    void registerUser(UserVO userVO);

    /**
     * 用户修改密码
     * @param userVO 用户数据
     */
    void updatePassword(UserVO userVO);

    /**
     * 添加管理员账户
     * @param addAdminVO 管理员账户实体
     */
    void addAdmin(AddAdminVO addAdminVO);

}
