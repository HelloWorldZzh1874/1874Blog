import { get, post, put, deletefn } from "./http";

// 获得首页数据
export const getHomeDate = () => get(`/`);
// 登录
export const login = param => post(`/login`, param);
// 退出登录，清token
export const logout = params => post(`/user/logout`, params);
// token登录
export const verifyToken = () => get(`/verifyToken`);

/*======================= 文章相关api ============================= */
// 查看首页文章
export const getHomeArticle = current =>
  get(`/article/homeData?current=${current}`);
// 获取归档信息
export const getArchive = current =>
  get(`/article/archives?current=${current}`);
// 获取最新文章
export const getNewArticle = () => get(`/article/newest`);
// 通过id获取文章
export const getArticle = id => get(`/article/${id}`);
// 搜索文章
export const searchArticle = param => get(`/article/search`, param);

export const getUserArticle = (id, current, isDelete, isDraft) =>
  get(
    `/articles/getUserArticle?userId=${id}&current=${current}&isDelete=${isDelete}&isDraft=${isDraft}`
  );
export const getArticleById = articleId =>
  get(`admin/article/selectArticlesById/${articleId}`);
export const saveArticleImg = file => post(`admin/article/uploadImg`, file); //保存图片
export const deleteArticleImg = params =>
  post(`admin/article/deleteImg`, params);
export const saveAndUpdateArticle = params =>
  post(`admin/article/saveAndUpdateArticle`, params); //保存草稿
export const getArticleOptions = () => get(`admin/article/options`); // 获取文章分类和标签选项
export const deleteOrRecArticle = param =>
  put(`admin/article/deleteOrRecArticle`, param);
export const deleteArticles = param =>
  deletefn(`/admin/article/deleteArticles`, param);

// 文章点赞
export const setLike = params => put(`/article/like`, params);

/*======================= 分类相关api ============================= */
// 查询所有标签分类
export const getCatalogList = () => get(`/category/categoryList`);

/*======================= 标签相关api ============================= */
// 查询所有标签列表
export const getTagList = () => get(`/tag/getList`);

/*======================= 友链相关api ============================= */
// 友链列表
export const getLinkList = () => get(`/friend-link/getList`);

/*======================= 关于我 ============================= */
// 获取关于我信息
export const getAbout = () => get(`/about`);

/*======================= 留言api ============================= */
// 获取留言弹幕
export const getMessageList = () => get(`/message/getList`);
// 添加弹幕
export const addMessage = param => post(`/message/add`, param);

/*======================= 用户api ============================= */
// 发送验证码
export const sendEmail = params => post(`/user/register/code`, params);
// 注册
export const register = param => post(`/user/register`, param);
// 用户修改信息
export const updateUserInfo = param => put(`/userInfo/setInfo`, param);
// 忘记密码
export const forGetPassWord = param => put(`/user/password`, param);

/*======================= 評論api ============================= */
// 查询文章和友链评论
export const getArticleComments = (id, current) =>
  get(`/comment/${id}/${current}`);
// 查询评论回复
export const getReplay = (commentId, current) =>
  get(`/comment/replies/${commentId}`, current);
// 评论点赞
export const saveCommentsLike = param => post(`/comment/like`, param);
// 添加评论或回复
export const addComment = param => post(`/comment/addComment`, param);
// 查询用户评论
export const getUserComment = () => get(`/comment/getUserComment`);
// 查询未读消息数量
export const getNotReadCount = () => get(`/comment/getNotReadCount`);
// 删除评论
export const deleteUserComment = param =>
  deletefn(`/comment/deleteUserComment`, param);
// 已读评论
export const changeReadStatus = id => put(`/comment/readComment/${id}`, null);
