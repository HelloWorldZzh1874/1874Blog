<template>
  <div>
    <div class="user-banner banner">
      <h1 class="banner-title">个人中心</h1>
    </div>
    <v-card class="blog-container">
      <div>
        <span class="info-title">基本信息</span>
      </div>
      <v-row class="info-wrapper">
        <v-col md="3" cols="12">
          <!-- <button id="pick-avatar">
            <v-avatar size="140">
              <img :src="this.$store.state.avatar" />
            </v-avatar>
          </button> -->
          <!-- <avatar-cropper
            @uploaded="uploadAvatar"
            trigger="#pick-avatar"
            upload-url="/api/users/avatar"
          /> -->
          <el-upload
            v-loading.fullscreen.lock="loading"
            element-loading-text="上传中"
            :action="updateAvatar()"
            :before-upload="beforeAvatorUplode"
            :on-progress="uploadVideoProcess"
            :show-file-list="false"
            :headers="token"
            :on-success="uploadAvatar"
          >
            <el-avatar
              :size="140"
              :src="this.$store.state.avatar"
            ></el-avatar>
          </el-upload>
        </v-col>
        <v-col md="7" cols="12">
          <el-input
            v-model="userInfo.nickname"
            label="昵称"
            placeholder="请输入您的昵称"
          />
          <el-input
            v-model="userInfo.webSite"
            class="mt-7"
            label="个人网站"
            placeholder="http://你的网址"
          />
          <el-input
            v-model="userInfo.intro"
            class="mt-7"
            label="简介"
            placeholder="介绍下自己吧"
          />
          <v-btn @click="updateUserInfo" outlined class="mt-5">修改</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import { mixin } from "../../utils/index";
import { updateUserInfo } from "../../api/index";
export default {
  mixins: [mixin],
  data: function() {
    return {
      userInfo: {
        nickname: this.$store.state.nickname,
        intro: this.$store.state.intro,
        webSite: this.$store.state.webSite
      },
      token: {
        token: localStorage.getItem("token")
      },
      loading: false
    };
  },
  methods: {
    updateUserInfo() {
      updateUserInfo(this.userInfo).then(res => {
        if (res.code === "200") {
          this.$store.commit("updateUserInfo", this.userInfo);
          this.$toast({ type: "success", message: res.msg });
        } else {
          this.$toast({ type: "error", message: res.msg });
        }
      });
    },
    updateAvatar() {
      return `${this.$store.state.HOST}/userInfo/avatar`;
    },
    // 上传成功钩子
    uploadAvatar(res) {
      this.loading = false;
      if (res.code === "200") {
        this.$toast({ type: "success", message: "更改成功" });
        this.$store.commit("updateAvatar", res.data);
      } else {
        this.$toast({ type: "error", message: res.msg });
      }
    },
    // 上传时钩子
    uploadVideoProcess() {
      this.loading = true;
    }
  }
};
</script>

<style scoped>
.user-banner {
  background: url(https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/PageImg/cartooncar.jpg)
    center center / cover no-repeat;
  background-color: #49b1f5;
}
.info-title {
  font-size: 1.25rem;
  font-weight: bold;
}
.info-wrapper {
  margin-top: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
#pick-avatar {
  outline: none;
}
.binding {
  display: flex;
  align-items: center;
}
</style>
