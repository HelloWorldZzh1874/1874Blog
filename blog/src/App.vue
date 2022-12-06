<template>
  <v-app id="app">
    <!-- 导航栏 -->
    <TopNavBar></TopNavBar>
    <!-- 侧边导航栏 -->
    <SideNavBar></SideNavBar>
    <!-- 内容 -->
    <v-content>
      <router-view :key="$route.fullPath" />
    </v-content>
    <!-- 页脚 -->
    <Footer></Footer>
    <!-- 返回顶部 -->
    <BackTop></BackTop>
    <!-- 搜索模态框 -->
    <searchModel></searchModel>
    <!-- 登录模态框 -->
    <LoginModel></LoginModel>
    <!-- 注册模态框 -->
    <RegisterModel></RegisterModel>
    <!-- 忘记密码模态框 -->
    <ForgetModel></ForgetModel>
    <!-- 绑定邮箱模态框 -->
    <EmailModel></EmailModel>
    <!-- 音乐播放器 -->
    <Player></Player>
    <!-- 聊天室 -->
    <ChatRoom></ChatRoom>
  </v-app>
</template>

<script>
import TopNavBar from "./components/layout/TopNavBar";
import SideNavBar from "./components/layout/SideNavBar";
import Footer from "./components/layout/Footer";
import BackTop from "./components/BackTop";
import searchModel from "./components/model/SearchModel";
import LoginModel from "./components/model/LoginModel";
import RegisterModel from "./components/model/RegisterModel";
import ForgetModel from "./components/model/ForgetModel";
import EmailModel from "./components/model/EmailModel";
import Player from "./components/zw-player";
import ChatRoom from "./components/ChatRoom";
import { verifyToken, getNotReadCount } from "./api/index";
export default {
  name: "app",
  components: {
    TopNavBar,
    Player,
    SideNavBar,
    Footer,
    BackTop,
    searchModel,
    LoginModel,
    RegisterModel,
    ForgetModel,
    EmailModel,
    ChatRoom
  },
  provide() {
    return {
      reload: this.reload
    };
  },
  watch: {
    $route: "watchComment"
  },
  created() {
    // 如果有token则进行token登录
    let token = localStorage.getItem("token");
    if (token !== null && token !== "" && this.$store.state.avatar === null) {
      verifyToken().then(res => {
        if (res.code === "200") {
          this.$store.commit("login", res.data);
          this.watchComment();
        }
      });
    }
  },
  data() {
    return {
      isRouterAlive: true
    };
  },
  methods: {
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function() {
        this.isRouterAlive = true;
      });
    },
    // 监控路由变化实现未读消息处理
    watchComment() {
      if (this.$store.state.id) {
        getNotReadCount().then(res => {
          this.$store.state.commentCount = res.data;
        });
      }
    }
  }
};
</script>
