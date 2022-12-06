package com.zzh.service;

import com.zzh.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.UserInfoVO;
import com.zzh.vo.UserRoleVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 修改用户角色
     * @param userRoleVO 要修改的数据
     */
    void updateUserRole(UserRoleVO userRoleVO);

    /**
     * 更改用户头像
     * @param file 文件
     * @return 返回保存路径
     *
     */
    String updateAvatar(MultipartFile file);

    /**
     * 修改用户资料
     * @param infoVO
     */
    void updateUserInfo(UserInfoVO infoVO);
}
