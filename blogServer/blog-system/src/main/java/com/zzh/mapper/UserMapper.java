package com.zzh.mapper;

import com.zzh.dto.UserBackDTO;
import com.zzh.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 返回账户锁定请求
     * @param username 账户
     * @return 返回是否锁定 true 未锁定 false锁定
     */
    Boolean selectAccountNotLock(@Param("username") String username);

    /**
     * 根据账号查找用户邮箱
     * @param username 用户名
     * @return 返回邮箱
     */
    String selectUserEmailByUsername(@Param("username") String username);


    /**
     * 查询后台用户数据
     * @param conditionVO 查询条件
     * @return 返回用户列表信息
     */
    List<UserBackDTO> listBackUsers(@Param("conditionVO") ConditionVO conditionVO);
}
