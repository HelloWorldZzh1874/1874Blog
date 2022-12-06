<template>
  <div>
    <!-- banner -->
    <div class="about-banner banner">
      <h1 class="banner-title">关于我</h1>
    </div>
    <!-- 关于我内容 -->
    <v-card class="blog-container">
      <div class="my-wrapper">
        <v-avatar size="110">
          <img
            class="author-avatar"
            src="https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/AvatarImg/Defalt_avatar2.jpg"
            alt=""
          />
        </v-avatar>
      </div>
      <div class="about-content markdown-body" v-html="aboutContent" />
    </v-card>
  </div>
</template>

<script>
import { getAbout } from "../../api/index";
export default {
  created() {
    this.getAboutContent();
  },
  data: function() {
    return {
      aboutContent: ""
    };
  },
  methods: {
    getAboutContent() {
      getAbout().then(res => {
        const MarkdownIt = require("markdown-it");
        const md = new MarkdownIt();
        this.aboutContent = md.render(res.data);
      });
    }
  }
};
</script>

<style scoped>
.about-banner {
  background: url(https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/PageImg/car.webp)
    center center / cover no-repeat;
  background-color: #49b1f5;
}
.about-content {
  word-break: break-word;
  line-height: 1.8;
  font-size: 14px;
}
.my-wrapper {
  text-align: center;
}
.author-avatar {
  transition: all 0.5s;
}
.author-avatar:hover {
  transform: rotate(360deg);
}
</style>
