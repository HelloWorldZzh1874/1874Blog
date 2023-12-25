import { get, post, put, deletefn } from "./http";

// 判断是否登录成功
export const adminLogin = params => post(`/login`, params);
// 退出登录，清token
export const logout = params => post(`/user/logout`, params);
// token登录
export const verifyToken = () => get(`/verifyToken`);
// 获取系统信息
export const getSysInfo = () => get(`/SystemInfo/getSystemInfo`);
// 删除账号
export const deleteAccount = param => deletefn(`/user/deleteAccount`, param);

/* ===============================> 菜单api <====================================== */
// 登录时根据角色返回菜单数据
export const getMenuByRole = () => get(`/menu/admin/getMenus`);
// 后台获取菜单列表
export const getBackMenu = () => get(`/menu/admin/menuList`);
// 获取角色对应的菜单列表
export const getRoleMenuOption = () => get(`/menu/admin/role/menus`);
// 保存或更新菜单
export const saveOrUpdateMenu = param =>
  post(`/menu/admin/updateOrSaveMenu`, param);
// 删除菜单
export const deleteMenu = param => deletefn(`/menu/admin/deleteMenu`, param);

/* ===============================> 文章api <====================================== */
// 获取文章分类和标签选项
export const getArticleOptions = () => get(`/article/admin/options`);
//保存草稿或修改文章
export const saveAndUpdateArticle = params =>
  post(`/article/saveOrUpdateArticle`, params);
// 保存图片
export const saveArticleImg = file => post(`/article/uploadImg`, file);
// 删除图片
export const deleteArticleImg = params => post(`/article/deleteImg`, params);
// 查询分页文章数据
export const getArticlesByPage = param =>
  post(`/article/admin/getArticles`, param);
// 修改文章顶置
export const changeTop = (articleId, param) =>
  put(`/article/admin/top/${articleId}`, param);
// 逻辑删除或恢复文章
export const deleteOrRecArticle = param =>
  put(`/article/admin/deleteOrRecArticle`, param);
// 删除文章或多个文章
export const deleteArticles = param =>
  deletefn(`/article/admin/deleteArticles`, param);
// 根据id查询相关文章
export const getArticleById = articleId =>
  get(`/article/admin/getArticlesById/${articleId}`);

/* ===============================> 分类api <====================================== */
// 查询所有分类
export const listCategories = param =>
  post(`/category/admin/categories`, param);
// 添加或修改分类信息
export const saveOrUpdateCategory = catalogVo =>
  post(`/category/admin/saveOrUpdateCategory`, catalogVo);
// 删除分类
export const deleteCategory = categoryIdList =>
  deletefn(`/category/admin/deleteCategory`, categoryIdList);

/* ===============================> 标签api <====================================== */

// 查询标签列表
export const findTags = param => post(`/tag/admin/selectTags`, param);
// 修改或新增标签
export const saveOrUpdateTag = tagVO =>
  post(`/tag/admin/saveOrUpdateTag`, tagVO);
// 根据id删除标签
export const deleteTag = tagIdList =>
  deletefn(`/tag/admin/deleteTag`, tagIdList);

/* ===============================> 评论api <====================================== */

// 查询后台评论
export const listBackComments = param =>
  post(`/comment/admin/getBackComments`, param);
// 逻辑删除或恢复评论
export const deleteOrRecComment = param =>
  put(`/comment/admin/updateCommentsStatus`, param);
// 物理删除评论
export const deleteComments = param =>
  deletefn(`/comment/admin/deleteComments`, param);

/* ===============================> 留言api <====================================== */
// 查询后台留言列表
export const listBackMessages = param =>
  post(`/message/admin/getBackMessages`, param);
// 删除留言
export const deleteMessage = param =>
  deletefn(`/message/admin/deleteMessages`, param);

/* ===============================> 用户api <====================================== */
// 后台查找用户列表
export const listBackUsers = param => post(`/user/admin/users`, param);
// 查找在线用户
export const getOnlineUsers = param => post(`/user/admin/online`, param);
// 添加管理员
export const addAdmin = param => post(`/user/addAdmin`, param);
/* ===============================> 角色api <====================================== */
// 查询角色选项
export const getRoleLabelList = () => get(`/role/admin/roleList`);
// 改变用户账户锁定状态
export const changeEnable = (id, param) =>
  put(`/user/admin/enabledState/${id}`, param);
// 查询角色列表
export const getRoleList = param => post(`/role/admin/roles`, param);
// 保存或更新修改角色菜单资源
export const saveOrUpdateRole = param =>
  post(`/role/admin/saveOrUpdateRole`, param);
// 删除角色
export const deleteRole = param => deletefn(`/role/admin/delete`, param);

/* ===============================> 用户信息api <====================================== */
// 修改用户信息(和权限)
export const updateRole = param => put(`/userInfo/admin/updateUserInfo`, param);
// 强制下线用户
export const forceOffline = param => put(`/user/admin/forceOffline`, param);
// 更改用户信息
export const updateUserInfo = param => put(`/userInfo/setInfo`, param);
// 修改密码
export const updatePwd = param => put(`/user/admin/setPassword`, param);

/* ===============================> 资源信息api <====================================== */
// 获取资源列表
export const listResourceOption = () => get(`/resource/admin/resources`);
// 查看后台资源列表
export const listResources = () => get(`/resource/admin/resourceList`);
// 添加或更新资源数据
export const addOrUpdateResource = param =>
  post(`/resource/admin/addOrUpdate`, param);
// 删除模块或资源
export const deleteResource = param => deletefn(`resource/admin/delete`, param);

/* ===============================> 友链api <====================================== */
// 查看后台友链
export const getBackLinkList = param =>
  post(`/friend-link/admin/getLinks`, param);
// 保存或更新友链
export const addOrUpdateLink = param => post(`/friend-link/admin/save`, param);
// 删除友链
export const deleteLinks = param =>
  deletefn(`/friend-link/admin/deleteLinks`, param);
``
/* ===============================> 日志api <====================================== */
// 日志查询
export const listBackLogs = param => post(`/OperationLog/admin/logs`, param);
// 日志删除
export const deleteLogs = param => deletefn(`/OperationLog/admin/logs`, param);

/* ===============================> 关于api <====================================== */
// 查询redis中关于我的信息
export const getAbout = () => get(`/blogInfo/about`);
// 修改关于我的消息
export const updateAbout = param => put(`/blogInfo/admin/about`, param);

/* ===============================> 公告api <====================================== */
// 获取公告
export const getNotice = () => get(`/blogInfo/admin/notice`);
// 修改公告
export const updateNotice = param => put(`/blogInfo/admin/notice`, param);
// 获取首页博客数据
export const getBackBlogInfo = () => get(`/blogInfo/admin/index`);

/* ===============================> 歌曲api <====================================== */
// 查询所有音乐数据
export const getBackMusics = param => post(`/music/admin/getMusicList`, param);
// 删除歌曲
export const deleteMusic = param => deletefn(`/music/admin/delete`, param);
// 修改歌曲信息
export const updateInfo = param => post(`/music/admin/updateMusicInfo`, param);
// 查询相关歌手id的歌曲
export const listMusicBySinger = param =>
  post(`/music/admin/listBySinger`, param);

/* ===============================> 歌手api <====================================== */
// 查询所有歌手(简略信息)
export const listSingers = () => get(`/singer/admin/findAll`);
// 查询歌手列表
export const getBackSingers = param => post(`/singer/admin/list`, param);
// 添加歌手
export const addOrUpdateSinger = param =>
  post(`/singer/admin/saveOrUpdateSinger`, param);
// 删除歌手
export const deleteSinger = param => deletefn(`/singer/admin/delete`, param);

/* ===============================> 定时任务api <====================================== */
// 查询所有定时任务信息
export const getQuartzInfo = param => post(`/quartz/admin/getAll`, param);
// 添加一个定时任务
export const addJob = param => post(`/quartz/add`, param);
// 暂停定时任务根据id
export const pauseJobById = id => put(`/quartz/pauseJob/${id}`, null);
// 根据id恢复运行定时任务
export const resumeJobById = id => put(`/quartz/resumeJob/${id}`, null);
// 根据id删除定时任务
export const deleteJobById = param => deletefn(`/quartz/deleteJob`, param);
