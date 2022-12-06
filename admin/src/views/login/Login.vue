<template>
  <div class="login-container">
    <div
      class="login-card"
      v-loading="loading"
      element-loading-text="正在登录..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
    >
      <div class="login-title">管理员登录</div>
      <!-- 登录表单 -->
      <el-form
        status-icon
        :model="loginForm"
        :rules="rules"
        ref="ruleForm"
        class="login-form"
      >
        <!-- 用户名输入框 -->
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            prefix-icon="el-icon-user-solid"
            placeholder="用户名"
            @keyup.enter.native="login"
          />
        </el-form-item>
        <!-- 密码输入框 -->
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            prefix-icon="iconfont el-icon-mymima"
            show-password
            placeholder="密码"
            @keyup.enter.native="login"
          />
        </el-form-item>
        <el-switch
          v-model="loginForm.rememberMe"
          active-text="七天免登录"
          inactive-text="仅本次登录"
        >
        </el-switch>
      </el-form>
      <!-- 登录按钮 -->
      <el-button type="primary" @click="login">登录</el-button>
    </div>
  </div>
</template>

<script>
import { generaMenu } from "@/assets/js/menu";
import { adminLogin, verifyToken } from "@/api";

export default {
  data: function() {
    return {
      loading: false,
      loginForm: {
        username: "",
        password: "",
        rememberMe: false
      },
      rules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        password: [{ required: true, message: "密码不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    let token = localStorage.getItem("token");
    let that = this;
    if (token !== "null" && token !== "") {
      verifyToken().then(res => {
        if (res.code === "200") {
          that.$store.commit("login", res.data);
          generaMenu();
          setTimeout(function() {
            that.$router.push({ path: "/" });
          }, 100);
        }
        that.loading = false;
      });
    }
  },
  methods: {
    login() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          const that = this;
          that.loading = true;
          // eslint-disable-next-line no-undef
          let captcha = new TencentCaptcha(
            this.config.TENCENT_CAPTCHA,
            function(res) {
              if (res.ret === 0) {
                //发送登录请求
                let urlParam = new URLSearchParams();
                urlParam.append("username", that.loginForm.username);
                urlParam.append("password", that.loginForm.password);
                urlParam.append("rememberMe", that.loginForm.rememberMe);
                adminLogin(urlParam).then(res => {
                  if (res.code === "200") {
                    that.$store.commit("login", res.data);
                    localStorage.setItem("token", res.data.token);
                    generaMenu();
                    setTimeout(function() {
                      that.$message.success("登录成功!");
                      that.$router.push({ path: "/" });
                    }, 100);
                  } else {
                    that.$message.error(res.msg);
                  }
                  that.loading = false;
                });
              }
              that.loading = false;
            }
          );
          // 显示验证码
          captcha.show();
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
.login-container {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  background: url(https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/PageImg/background2.jpg)
    center center / cover no-repeat;
}
.login-card {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  background: #15191a;
  padding: 170px 60px 180px;
  width: 350px;
}
.login-title {
  color: #467be4;
  font-weight: bold;
  font-size: 1rem;
}
.login-form {
  margin-top: 1.2rem;
}
.login-card button {
  margin-top: 1rem;
  width: 100%;
}
</style>
