<template>
  <v-dialog v-model="forgetFlag" :fullscreen="isMobile" max-width="460">
    <v-card class="login-container" style="border-radius: 4px">
      <v-icon class="float-right" @click="forgetFlag = false">
        mdi-close
      </v-icon>
      <div class="login-wrapper">
        <!-- 用户名 -->
        <v-text-field
          v-model="email"
          label="邮箱号"
          placeholder="请输入您的邮箱号"
          clearable
          @keyup.enter="forget"
        />
        <!-- 验证码 -->
        <div class="mt-7 send-wrapper">
          <v-text-field
            maxlength="6"
            v-model="code"
            label="验证码"
            placeholder="请输入6位验证码"
            @keyup.enter="forget"
          />
          <v-btn :disabled="flag" text small @click="sendCode">
            {{ codeMsg }}
          </v-btn>
        </div>
        <!-- 密码 -->
        <v-text-field
          v-model="password"
          class="mt-7"
          label="密码"
          placeholder="请输入您的密码"
          @keyup.enter="forget"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          @click:append="show = !show"
        />
        <!-- 按钮 -->
        <v-btn
          class="mt-7"
          block
          color="green"
          style="color: #fff"
          @click="forget"
        >
          确定
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
import { sendEmail, forGetPassWord } from "../../api/index";
export default {
  data: function() {
    return {
      email: "",
      code: "",
      password: "",
      flag: true,
      codeMsg: "发送",
      time: 60,
      show: false
    };
  },
  methods: {
    openLogin() {
      this.$store.state.forgetFlag = false;
      this.$store.state.loginFlag = true;
    },
    sendCode() {
      const that = this;
      // eslint-disable-next-line no-undef
      let captcha = new TencentCaptcha(this.config.TENCENT_CAPTCHA, function(
        res
      ) {
        if (res.ret === 0) {
          //发送邮件
          let param = new URLSearchParams();
          param.append("email", that.email);
          param.append("type", "2");
          sendEmail(param).then(res => {
            if (res.code === "200") {
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
    forget() {
      let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (!reg.test(this.email)) {
        this.$toast({ type: "error", message: "邮箱格式不正确" });
        return false;
      }
      if (this.code.trim().length !== 6) {
        this.$toast({ type: "error", message: "请输入6位验证码" });
        return false;
      }
      if (this.password.trim().length < 6) {
        this.$toast({ type: "error", message: "密码不能少于6位" });
        return false;
      }
      const user = {
        email: this.email,
        password: this.password,
        code: this.code
      };
      forGetPassWord(user).then(res => {
        if (res.code) {
          this.$toast({ type: "success", message: res.msg });
          this.openLogin();
        } else {
          this.$toast({ type: "error", message: res.msg });
        }
      });
    }
  },
  computed: {
    forgetFlag: {
      set(value) {
        this.$store.state.forgetFlag = value;
      },
      get() {
        return this.$store.state.forgetFlag;
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
