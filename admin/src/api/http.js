import axios from "axios";
import router, { resetRouter } from "../router";
import Message from "@/views/message/Message";
import store from "../store";
import Vue from "vue";

axios.defaults.timeout = 30000; // 超时时间30秒
// Content-type 响应头
axios.defaults.headers.post["Content-Type"] =
  "application/x-www-form-urlencoded;charset=UTF-8";
// 基础后台url
axios.defaults.baseURL = "http://127.0.0.1:8088";
axios.interceptors.response.use(
  response => {
    // 响应包含403访问未授权资源，如果登录则退出登录
    switch (response.data.code) {
      // 强制退出
      case "403":
        // 清空用户信息
        store.commit("logout");
        localStorage.removeItem("token");
        store.commit("resetTab");
        // 清空用户菜单
        resetRouter();
        router.push({ path: "/login" });
        Vue.prototype.$notify.error({
          title: "错误",
          message: response.data.msg
        });
        break;
      case "401":
        Vue.prototype.$notify.error({
          title: "错误",
          message: response.data.msg + "请联系管理员!"
        });
        break;
      case "500":
        Vue.prototype.$notify.error({
          title: "错误",
          message: response.data.msg
        });
        break;
      default:
        return Promise.resolve(response);
    }
  },
  error => {
    if (error.response.status) {
      switch (error.response.status) {
        case 401: // 未登录
          router.replace({
            path: "/",
            query: {
              redirect: router.currentRoute.fullPath
            }
          });
          break;
        case 404: // 未找到路径
          break;
      }
    }
    return Promise.reject(error.response);
  }
);

axios.interceptors.request.use(
  function(config) {
    let token = localStorage.getItem("token");
    // 判断是否存在token，如果存在的话，则每个http header都加上token
    if (token) {
      config.headers.token = token;
    }
    return config;
  },
  function(error) {
    return Promise.reject(error);
  }
);

/**
 *
 * 封装Get方法
 *
 */
export function get(url, params = {}) {
  return new Promise((resolve, reject) => {
    axios
      .get(url, { params: params })
      .then(response => {
        resolve(response.data);
      })
      .catch(err => {
        reject(err);
      });
  });
}

/**
 *
 * 封装post方法
 *
 */

export function post(url, data = {}) {
  return new Promise((resolve, reject) => {
    axios
      .post(url, data)
      .then(response => {
        resolve(response.data);
      })
      .catch(err => {
        reject(err);
      });
  });
}

/**
 * put方法，对应put请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function put(url, params) {
  return new Promise((resolve, reject) => {
    axios
      .put(url, params)
      .then(res => {
        resolve(res.data);
        // Loading.service(true).close();
        //  Message({message: '请求成功', type: 'success'});
      })
      .catch(err => {
        reject(err.data);
        // Loading.service(true).close();
        Message({ message: "加载失败", type: "error" });
      });
  });
}

/**
 * delete
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
export function deletefn(url, params) {
  return new Promise((resolve, reject) => {
    axios
      .delete(url, params)
      .then(res => {
        resolve(res.data);
        // Loading.service(true).close();
        //  Message({message: '请求成功', type: 'success'});
      })
      .catch(err => {
        reject(err.data);
        // Loading.service(true).close();
        Message({ message: "加载失败", type: "error" });
      });
  });
}
