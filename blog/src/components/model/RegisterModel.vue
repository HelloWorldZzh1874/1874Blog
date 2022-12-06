<template>
  <v-dialog v-model="registerFlag" :fullscreen="isMobile" max-width="460">
    <v-card class="login-container" style="border-radius: 4px">
      <v-icon class="float-right" @click="registerFlag = false">
        mdi-close
      </v-icon>
      <div class="login-wrapper">
        <!-- 邮箱号 -->
        <v-text-field
          v-model="email"
          label="邮箱号"
          placeholder="请输入您的邮箱号"
          clearable
          @keyup.enter="register"
        />
        <!-- 验证码 -->
        <div class="mt-7 send-wrapper">
          <v-text-field
            maxlength="6"
            v-model="code"
            label="验证码"
            placeholder="请输入6位验证码"
            @keyup.enter="register"
          />
          <v-btn text small :disabled="flag" @click="sendCode">
            {{ codeMsg }}
          </v-btn>
        </div>
        <!-- 密码 -->
        <v-text-field
          v-model="password"
          class="mt-7"
          label="密码"
          placeholder="请输入您的密码"
          @keyup.enter="register"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          @click:append="show = !show"
        />
        <!-- 注册按钮 -->
        <v-btn
          class="mt-7"
          block
          color="red"
          style="color: #fff"
          @click="register"
          :loading="loading"
          :disabled="loading"
        >
          注册
        </v-btn>
        <!-- 登录 -->
        <div class="mt-10 login-tip">
          已有账号？<span @click="openLogin">登录</span>
        </div>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
import { sendEmail, register, login } from "../../api/index";
export default {
  data: function() {
    return {
      email: "",
      code: "",
      password: "",
      flag: true,
      loading: false,
      codeMsg: "发送",
      time: 60,
      show: false
    };
  },
  methods: {
    openLogin() {
      this.$store.state.registerFlag = false;
      this.$store.state.loginFlag = true;
    },
    sendCode() {
      const that = this;
      // eslint-disable-next-line no-undef
      let captcha = new TencentCaptcha(this.config.TENCENT_CAPTCHA, function(
        res
      ) {
        if (res.ret === 0) {
          let param = new URLSearchParams();
          param.append("email", that.email);
          param.append("type", "1");
          sendEmail(param).then(res => {
            if (res.code === "200") {
              //发送邮件成功倒计时
              that.countDown();
              that.$toast({ type: "success", message: res.msg });
            } else {
              that.$toast({ type: "error", message: res.msg });
            }
          });
        }
      });
      // 显示验证码
      captcha.show();
    },
    countDown() {
      this.flag = true;
      this.timer = setInterval(() => {
        this.time--;
        this.codeMsg = this.time + "s";
        if (this.time <= 0) {
          clearInterval(this.timer);
          this.codeMsg = "发送";
          this.time = 60;
          this.flag = false;
        }
      }, 1000);
    },
    register() {
      this.loading = true;
      if (this.code.trim().length !== 6) {
        this.$toast({ type: "error", message: "请输入6位验证码" });
        return false;
      }
      if (this.password.trim().length < 6) {
        this.$toast({ type: "error", message: "密码不能少于6位" });
        return false;
      }
      let param = new URLSearchParams();
      param.append("email", this.email);
      param.append("password", this.password);
      param.append("code", this.code);
      register(param).then(res => {
        if (res.code === "200") {
          this.loading = false;
          // 注册成功发送登录请求
          let urlParam = new URLSearchParams();
          urlParam.append("username", this.email);
          urlParam.append("password", this.password);
          login(urlParam).then(res => {
            this.email = "";
            this.password = "";
            if (res.code === "200") {
              this.$toast({ type: "success", message: "登陆成功!" });
              this.$store.commit("login", res.data);
              localStorage.setItem("token", res.data.token);
              this.$store.commit("closeModel");
            } else {
              this.$toast({ type: "error", message: res.msg });
            }
          });
        } else {
          this.$toast({ type: "error", message: res.msg });
        }
      });
    }
  },
  computed: {
    registerFlag: {
      set(value) {
        this.$store.state.registerFlag = value;
      },
      get() {
        return this.$store.state.registerFlag;
      }
    },
    isMobile() {
      const clientWidth = document.documentElement.clientWidth;
      return clientWidth <= 960;
    }
  },
  watch: {
    email(value) {
      let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      this.flag = !reg.test(value);
    }
  }
};
</script>
