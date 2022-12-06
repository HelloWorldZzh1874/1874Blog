# 0.请求拦截器

1. 每个请求都会被 TokenAuthenticationFilter 拦截并判断是否携带token,并对token进行刷新操作。
2. 如果携带token，会解析token中的用户数据存入security context中供以后的filter使用。
3. 放行请求，如果没有将用户信息存入security context 则会被某些filter拦截。

# 0.1 资源鉴权

1. 类 MyFilterInvocationSecurityMetadataSource 收集角色和权限的集合
2. 每个请求都会找到请求路径和请求方法对于的资源，并将该资源需要的权限放入set集合交给之后进行决策
3. 类 MyAccessDecisionManager 用于对需要鉴权的资源进行鉴权，如果上面处理的set集合中没有需要的权限，则认为是匿名资源，可以访问
4. 如果访问资源属于需要权限的资源，并且当前用户属于匿名用户，则抛出异常
5. 如果访问资源属于需要权限的资源，用户属于登录用户，那么遍历用户拥有的权限列表并于资源所需权限匹配，如果用户拥有访问该资源的权限则通过，未拥有相关权限则抛出异常

# 1.登录流程

1. MyUsernamePasswordAuthenticationFilter 重写 security 的 UsernamePasswordAuthenticationFilter,拦截登录请求并获得账号密码参数(只接受POST请求)
2. 从 OnlineUser(HashMap)中检查当前账户是否存在，如果存在，抛出账号已登录异常并进行相关操作
3. 通过 loginService 判断账号密码是否匹配
4. 如果账号密码匹配，判断redis中该账户是可以解锁并进行解锁操作，再将账号、密码装入UsernamePasswordAuthenticationToken中(通过UserDetailsServiceImpl封装账户信息和角色等)
5. 如果账号密码不匹配，则根据 loginService 抛出的异常进行不同的操作
6. 如果属于密码错误，则判断是否为第一次输入错误，如果是则存入redis该账户的登陆错误信息次数(1)
7. 密码错误并且redis中有该账户登录错误信息，则增加登录错误次数
8. 密码错误并且redis中该账户登陆错误次数大于阈值，则锁定该账户，不允许再登录
9. 如果登录成功,Security最终会进入 MyAuthenticationSuccessHandler(重写AuthenticationSuccessHandler)
   ,并生成token，更具请求中的remember设置redis中的有效时间;封装在线用户数据放入在线用户组(onlineUserMap),封装返回对象相应前端。

# 2.Token的生成于存储

1. TokenService 包含了token的生成于解析相关数据的方法
2. 生成token需要用户登录数据(LoginUser)
    1. 首先生成唯一的UUID(通过工具类)
    2. JWT工具中的JwtUtil.createJwt(String jwtSec, String uuid)方法来生成指定密匙的token，并在该token的声明claims中存入value为uuid的键值对
    3. 根据是否要remember设置token存入redis的过期时间，以uuid为基础封装后为key，用户数据为value存入redis，返回该token
3. 解析token
    1. 根据得到的token和密匙解析出该token的claims(JwtUtils.parseJwt())
    2. 从声明claims中取得uuid
    3. 封装uuid作为key，取出redis中的相关数据并返回

# 3.角色与菜单

1. 用户登录成功后，会从后端获取相关的菜单列表与组件信息
2. 首先从Security上下文中取得用户登录信息
3. 通过用户信息中的角色去菜单业务中调用getMenuListByRole(String role)方法
4. 在 getMenuListByRole(String role) 方法中通过用户角色role从数据库获得此角色的所有菜单信息，并封装成menus(List)
5. 再将menus进行处理，得到一级菜单，具体做法是:把menus中parentId属性为null的对象收集成list排序后返回。(lambda)
6. 同样处理menus,得到<父级菜单id,子菜单对象>这样一个map，具体做法是:将parentId属性不为null的对象通过getParentId分组并返回list(lambda)
7. 最后将一级菜单和子菜单合并(通过子菜单map中的父级菜单id和一级菜单id匹配)，返回封装数据

```java
/**
 * 将一级菜单和子菜单合并成前端菜单项目
 *
 * @param catalogs      一级菜单
 * @param childrenMenus 子菜单
 */
private List<MenuDTO> convertUserMenuList(List<Menu> catalogs,Map<Integer, List<Menu>>childrenMenus){
        // 将一级目录转换成流,处理后返回
        return catalogs.stream()
        // map流对每个元素应用函数，而不改变源数据
        .map(item->{
        // 每个将子菜单封装好的menu,也就是前端每个一级菜单---返回他的list
        MenuDTO menuDTO=new MenuDTO();
        // 处理好的子菜单列表
        List<MenuDTO> listMenuDTO=new ArrayList<>();
        // 通过每个一级目录元素的id取出相应的子菜单
        List<Menu> childrenMenu=childrenMenus.get(item.getId());
        // 如果当前一级菜单有子菜单的处理
        if(CollectionUtils.isNotEmpty(childrenMenu)){
        // 如果有子菜单，则此一级菜单需要展开，不用展示页面----多级菜单
        // 复制属性到dto,用户封装成前端需要的对象内容
        menuDTO=BeanCopyUtil.copyObject(item,MenuDTO.class);
        // 将子菜转换成流并处理，返回处理好的子菜单list
        listMenuDTO=childrenMenu.stream()
        // 对子菜单排序(前端展示顺序)
        .sorted(Comparator.comparing(Menu::getOrderNum))
        .map(children->{
        // 将menu转换成menuDTO类型
        MenuDTO dto=BeanCopyUtil.copyObject(children,MenuDTO.class);
        dto.setIsHidden(children.getIsHidden());
        return dto;
        }).collect(Collectors.toList());
        }else{
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
```
