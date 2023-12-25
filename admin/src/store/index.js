import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    collapse: false,
    HOST: "http://127.0.0.1:8088",
    tabList: [{ name: "首页", path: "/" }],
    username: null,
    avatar: null,
    nickname: null,
    token: null,
    intro: null,
    webSite: null,
    userMenuList: []
  },
  mutations: {
    saveTab(state, tab) {
      if (state.tabList.findIndex(item => item.path === tab.path) === -1) {
        state.tabList.push({ name: tab.name, path: tab.path });
      }
    },
    removeTab(state, tab) {
      const index = state.tabList.findIndex(item => item.name === tab.name);
      state.tabList.splice(index, 1);
    },
    resetTab(state) {
      state.tabList = [{ name: "首页", path: "/" }];
    },
    trigger(state) {
      state.collapse = !state.collapse;
    },
    login(state, user) {
      state.avatar = user.avatar;
      state.nickname = user.nickname;
      state.token = user.token;
      state.username = user.username;
      state.intro = user.intro;
      state.webSite = user.website;
    },
    saveUserMenuList(state, userMenuList) {
      state.userMenuList = userMenuList;
    },
    logout(state) {
      state.avatar = null;
      state.nickname = null;
      state.token = null;
      state.username = null;
      state.intro = null;
      state.webSite = null;
      state.userMenuList = [];
    },
    updateAvatar(state, avatar) {
      state.avatar = avatar;
    },
    updateUserInfo(state, user) {
      state.nickname = user.nickname;
      state.intro = user.intro;
      state.webSite = user.webSite;
    }
  },
  actions: {},
  modules: {},
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ]
});
