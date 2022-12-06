package com.zzh.mapper;

import com.zzh.dto.RoleUrlDTO;
import com.zzh.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  资源Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 资源权限元数据
     * @return 返回封装数据
     */
    List<RoleUrlDTO> listRoleUrls();

    /**
     * 查询子节点id
     * @date 2022/3/29
     * @param id 模块id
     * @return 返回自己的id列表
     */
    List<Integer> getChildrenId(Integer id);
}
