package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.common.base.BaseErrorInfo;
import com.zzh.common.exception.SaveOrUpdateException;
import com.zzh.common.exception.baseException.MyException;
import com.zzh.common.utils.BeanCopyUtil;
import com.zzh.dto.BackMenuDTO;
import com.zzh.dto.MenuDTO;
import com.zzh.dto.labelOptionDTO;
import com.zzh.entity.ConRoleMenu;
import com.zzh.entity.Menu;
import com.zzh.mapper.ConRoleMenuMapper;
import com.zzh.mapper.MenuMapper;
import com.zzh.service.MenuService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.MenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-06
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Autowired
    ConRoleMenuMapper conRoleMenuMapper;

    @Override
    public List<MenuDTO> getMenuListByRole(String role) {
        // 获得当前角色的菜单列表
        List<Menu> menus = menuMapper.listMenuByRoleId(role);
        // 获得一级菜单
        List<Menu> catalog = getCatalog(menus);
        // 获得子菜单
        Map<Integer, List<Menu>> childrenMenu = getChildrenMenu(menus);
        List<MenuDTO> menuDTOS = convertUserMenuList(catalog, childrenMenu);
        if(Objects.isNull(menuDTOS)){
            throw new MyException(BaseErrorInfo.SERVER_BUSY);
        }
        return menuDTOS;
    }

    @Override
    public List<labelOptionDTO> getRoleMenuOption() {
        // 查询菜单数据
        List<Menu> menuList = this.list(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId, Menu::getName, Menu::getParentId, Menu::getOrderNum));
        // 获取目录列表
        List<Menu> catalogList = getCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getChildrenMenu(menuList);
        // 组装目录菜单数据
        return catalogList.stream().map(item -> {
            // 获取目录下的菜单排序
            List<labelOptionDTO> list = new ArrayList<>();
            List<Menu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> labelOptionDTO.builder()
                                .id(menu.getId())
                                .label(menu.getName())
                                .build())
                        .collect(Collectors.toList());
            }
            return labelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<BackMenuDTO> getBackMenuList(ConditionVO conditionVO) {
        // 查询菜单数据
        List<Menu> menuList = this.list(new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Menu::getName, conditionVO.getKeywords()));
        // 获取目录列表
        List<Menu> catalogList = getCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getChildrenMenu(menuList);
        // 组装目录菜单数据
        return catalogList.stream().map(item -> {
            BackMenuDTO menuDTO = BeanCopyUtil.copyObject(item, BackMenuDTO.class);
            // 获取目录下的菜单排序
            List<BackMenuDTO> list = BeanCopyUtil.copyList(childrenMap.get(item.getId()), BackMenuDTO.class).stream()
                    .sorted(Comparator.comparing(BackMenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTO.setChildren(list);
            return menuDTO;
        }).sorted(Comparator.comparing(BackMenuDTO::getOrderNum)).collect(Collectors.toList());
    }

    @Override
    public void updateOrSaveMenu(MenuVO menuVo) {
        // 判断重复项
        Integer count = menuMapper.selectCount(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getName, menuVo.getName())
                .ne(Objects.nonNull(menuVo.getId()), Menu::getId, menuVo.getId())
        );
        if (count > 0) {
            throw new SaveOrUpdateException("菜单名重复!");
        }
        count = menuMapper.selectCount(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getComponent, menuVo.getComponent())
                .ne(Objects.nonNull(menuVo.getId()), Menu::getId, menuVo.getId())
        );
        if (count > 0 && !"Layout".equals(menuVo.getComponent())) {
            throw new SaveOrUpdateException("有相同组件已注册!!");
        }
        count = menuMapper.selectCount(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getPath, menuVo.getPath())
                .ne(Objects.nonNull(menuVo.getId()), Menu::getId, menuVo.getId())
        );
        if (count > 0) {
            throw new SaveOrUpdateException("有相同路径的组件!");
        }
        // 若无重复
        Menu menu = Menu.builder()
                .id(menuVo.getId())
                .name(menuVo.getName())
                .path(menuVo.getPath())
                .component(menuVo.getComponent())
                .icon(menuVo.getIcon())
                .createTime(Objects.isNull(menuVo.getId()) ? new Date() : null)
                .updateTime(new Date())
                .parentId(menuVo.getParentId())
                .orderNum(menuVo.getOrderNum())
                .isHidden(menuVo.getIsHidden())
                .isDisable(menuVo.getIsDisable())
                .build();
        this.saveOrUpdate(menu);
    }

    @Override
    public void deleteMenu(Integer id) {
        // 当前菜单是否存在孩子
        Menu menu = menuMapper.selectById(id);
        // 如果没有父节点，那么他就是目录，需要删除他的所有子节点
        if (Objects.isNull(menu.getParentId())) {
            // 查询所有孩子id
            List<Integer> childrenId = menuMapper.getChildrenId(id);
            // 如果不为空删除所有子节点
            if (Objects.nonNull(childrenId) && !childrenId.isEmpty()) {
                menuMapper.deleteBatchIds(childrenId);
                // 删除关系表数据
                for (Integer cId : childrenId) {
                    conRoleMenuMapper.delete(new LambdaQueryWrapper<ConRoleMenu>().eq(ConRoleMenu::getMenuId, cId));
                }
            }
            conRoleMenuMapper.delete(new LambdaQueryWrapper<ConRoleMenu>().eq(ConRoleMenu::getMenuId, id));
            menuMapper.deleteById(id);
        } else {
            // 如过不是父节点，则直接删除这个子节点
            menuMapper.deleteById(id);
            conRoleMenuMapper.delete(new LambdaQueryWrapper<ConRoleMenu>().eq(ConRoleMenu::getMenuId, id));
        }
    }

    /**
     * 获取一级菜单
     *
     * @param menus 所有的菜单
     * @return 返回一级菜单列表
     */
    private List<Menu> getCatalog(List<Menu> menus) {
        return menus.stream()
                // 返回与条件匹配的流---parentId为null则是一级菜单
                .filter(item -> Objects.isNull(item.getParentId()))
                // 通过Comparator比较器排序
                .sorted(Comparator.comparing(Menu::getOrderNum))
                // 收集流---封装成list
                .collect(Collectors.toList());
    }

    /**
     * 获取子菜单
     *
     * @param menus 所有菜单列表
     * @return 返回子菜单列表
     */
    private Map<Integer, List<Menu>> getChildrenMenu(List<Menu> menus) {
        return menus.stream()
                // 获取parentId不为null的菜单
                .filter(item -> Objects.nonNull(item.getParentId()))
                // 根据parentId分组收集
                .collect(Collectors.groupingBy(Menu::getParentId));
    }

    /**
     * 将一级菜单和子菜单合并成前端菜单项目
     *
     * @param catalogs      一级菜单
     * @param childrenMenus 子菜单
     */
    private List<MenuDTO> convertUserMenuList(List<Menu> catalogs, Map<Integer, List<Menu>> childrenMenus) {
        // 将一级目录转换成流,处理后返回
        return catalogs.stream()
                // map流对每个元素应用函数，而不改变源数据
                .map(item -> {
                    // 每个将子菜单封装好的menu,也就是前端每个一级菜单---返回他的list
                    MenuDTO menuDTO = new MenuDTO();
                    // 处理好的子菜单列表
                    List<MenuDTO> listMenuDTO = new ArrayList<>();
                    // 通过每个一级目录元素的id取出相应的子菜单
                    List<Menu> childrenMenu = childrenMenus.get(item.getId());
                    // 如果当前一级菜单有子菜单的处理
                    if (CollectionUtils.isNotEmpty(childrenMenu)) {
                        // 如果有子菜单，则此一级菜单需要展开，不用展示页面----多级菜单
                        // 复制属性到dto,用户封装成前端需要的对象内容
                        menuDTO = BeanCopyUtil.copyObject(item, MenuDTO.class);
                        // 将子菜转换成流并处理，返回处理好的子菜单list
                        listMenuDTO = childrenMenu.stream()
                                // 对子菜单排序(前端展示顺序)
                                .sorted(Comparator.comparing(Menu::getOrderNum))
                                .map(children -> {
                                    // 将menu转换成menuDTO类型
                                    MenuDTO dto = BeanCopyUtil.copyObject(children, MenuDTO.class);
                                    dto.setIsHidden(children.getIsHidden());
                                    return dto;
                                }).collect(Collectors.toList());
                    } else {
                        // 如果没有子菜单则展示相应页面
                        menuDTO.setPath(item.getPath());
                        // Layout表示一级菜单不展开
                        menuDTO.setComponent("Layout");
                        // 将展示组件封装成子菜单
                        listMenuDTO.add(MenuDTO.builder().path("")
                                .name(item.getName())
                                .component(item.getComponent())
                                .icon(item.getIcon()).build());
                    }
                    // 该菜单是否隐藏
                    menuDTO.setIsHidden(item.getIsHidden());
                    // 封装子菜单
                    menuDTO.setChildren(listMenuDTO);
                    return menuDTO;
                }).collect(Collectors.toList());

    }
}
