import { get, post,deletefn } from "../../../api/http";

// 获取歌单歌曲id
export const getHotMusic = () => get(`/musicList/getList`);
// 获取用户收藏歌曲
export const getCollect = () => get(`/musicList/getCollect`);
// 获取歌曲url
export const getMusicUrl = id => get(`/music/getUrl?id=${id}`);
// 添加到用户收藏
export const addCollect = id => post(`/music/addUserCollect`, id);
// 移除用户收藏歌曲
export const removeCollect = id => deletefn(`/music/removeCollect`, id);
// 获取歌曲歌词
export const getWords = id => get(`/music/lyric?id=${id}`);
//获取搜索建议
export const getSearchSuggest = key => get(`/music/getMusicLike?name=${key}`);
