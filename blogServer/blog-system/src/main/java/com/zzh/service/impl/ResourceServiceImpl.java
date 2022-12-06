package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.common.exception.SaveOrUpdateException;
import com.zzh.common.utils.BeanCopyUtil;
import com.zzh.dto.ResourceDTO;
import com.zzh.dto.labelOptionDTO;
import com.zzh.entity.ConRoleResource;
import com.zzh.entity.Resource;
import com.zzh.mapper.ConRoleResourceMapper;
import com.zzh.mapper.ResourceMapper;
import com.zzh.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.zzh.common.constant.CommonConst.FALSE;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    ConRoleResourceMapper conRoleResourceMapper;

    @Override
    public List<labelOptionDTO> listResourceOption() {
        // 查询资源列表
        List<Resource> resourceList = this.list(new LambdaQueryWrapper<Resource>()
                .select(Resource::getId, Resource::getResourceName, Resource::getParentId)
                .eq(Resource::getIsAnonymous, FALSE)
                .eq(Resource::getIsDisable, FALSE));
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 组装父子数据
        return parentList.stream().map(item -> {
            List<labelOptionDTO> list = new ArrayList<>();
            List<Resource> children = childrenMap.get(item.getId());
            if (Objects.nonNull(children)) {
                list = children.stream()
                        .map(resource -> labelOptionDTO.builder()
                                .id(resource.getId())
                                .label(resource.getResourceName())
                                .build())
                        .collect(Collectors.toList());
            }
            // 返回封装好的列表
            return labelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getResourceName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<ResourceDTO> listResources() {
        // 查询资源列表
        List<Resource> resourceList = resourceMapper.selectList(null);
        // 获取所有模块
        List<Resource> parentList = listResourceModule(resourceList);
        // 根据父id分组获取模块下的资源
        Map<Integer, List<Resource>> childrenMap = listResourceChildren(resourceList);
        // 绑定模块下的所有接口
        return parentList.stream().map(item -> {
            ResourceDTO resourceDTO = BeanCopyUtil.copyObject(item, ResourceDTO.class);
            List<ResourceDTO> childrenList = BeanCopyUtil.copyList(childrenMap.get(item.getId()), ResourceDTO.class);
            resourceDTO.setChildren(childrenList);
            return resourceDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void addOrUpdate(ResourceVO resourceVO) {
        // 更新资源信息
        Resource resource = BeanCopyUtil.copyObject(resourceVO, Resource.class);
        resource.setCreateTime(Objects.isNull(resource.getId()) ? new Date() : null);
        resource.setUpdateTime(Objects.nonNull(resource.getId()) ? new Date() : null);
        this.saveOrUpdate(resource);
    }

    @Override
    public void deleteResource(Integer id) {
        // 查询是否有角色关联
        Integer count = conRoleResourceMapper.selectCount(new LambdaQueryWrapper<ConRoleResource>()
                .in(ConRoleResource::getResourceId, id));
        if (count > 0) {
            throw new SaveOrUpdateException("该资源下存在角色!");
        }
        // 当前菜单是否存在孩子
        Resource resource = this.getById(id);
        // 如果没有父节点，那么他就是一级模块，需要删除他的所有子节点
        if (Objects.isNull(resource.getParentId())) {
            // 查询所有孩子id
            List<Integer> childrenId = resourceMapper.getChildrenId(id);
            // 判断是否有孩子节点 若为0则不进行删除子节点相关操作
            if (childrenId.size()!=0) {
                Integer countC = conRoleResourceMapper.selectCount(new LambdaQueryWrapper<ConRoleResource>()
                        .in(ConRoleResource::getResourceId, childrenId));
                if (countC > 0) {
                    throw new SaveOrUpdateException("子资源下存在角色!");
                }
                resourceMapper.deleteBatchIds(childrenId);
                // 删除关系表数据
                for (Integer cId : childrenId) {
                    conRoleResourceMapper.delete(new LambdaQueryWrapper<ConRoleResource>().eq(ConRoleResource::getResourceId, cId));
                }
            }
            conRoleResourceMapper.delete(new LambdaQueryWrapper<ConRoleResource>().eq(ConRoleResource::getResourceId, id));
            resourceMapper.deleteById(id);
        } else {
            // 如过不是父节点,则直接删除这个子节点
            resourceMapper.deleteById(id);
            conRoleResourceMapper.delete(new LambdaQueryWrapper<ConRoleResource>().eq(ConRoleResource::getResourceId, id));
        }
    }

    /**
     * 获取所有资源模块
     *
     * @param resourceList 资源列表
     * @return 资源模块列表
     */
    private List<Resource> listResourceModule(List<Resource> resourceList) {
        return resourceList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .collect(Collectors.toList());
    }

    /**
     * 获取模块下的所有资源
     *
     * @param resourceList 资源列表
     * @return 模块资源
     */
    private Map<Integer, List<Resource>> listResourceChildren(List<Resource> resourceList) {
        return resourceList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Resource::getParentId));
    }
}
