package com.zzh.service;

import com.zzh.dto.ResourceDTO;
import com.zzh.dto.labelOptionDTO;
import com.zzh.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.ResourceVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
public interface ResourceService extends IService<Resource> {
    /**
     * 查看资源选项
     * @return 资源选项
     */
    List<labelOptionDTO> listResourceOption();

    /**
     * 查看资源数据
     * @return ResourceDTO
     */
    List<ResourceDTO> listResources();

    /**
     * 添加或更新资源数据
     * @param resourceVO 资源数据
     */
    void addOrUpdate(ResourceVO resourceVO);

    /**
     * 删除资源
     * @param id 资源id
     */
    void deleteResource(Integer id);
}
