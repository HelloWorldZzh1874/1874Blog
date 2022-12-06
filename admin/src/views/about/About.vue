<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <mavon-editor
      v-model="aboutContent"
      style="height: calc(100vh - 250px); margin-top: 2.25rem"
    />
    <el-button
      type="danger"
      size="medium"
      class="edit-btn"
      @click="updateAbout"
    >
      修改
    </el-button>
  </el-card>
</template>

<script>
import { updateAbout, getAbout } from "../../api/index";
export default {
  created() {
    this.getAbout();
  },
  data: function() {
    return {
      aboutContent: ""
    };
  },
  methods: {
    // 查询关于我信息
    getAbout() {
      getAbout().then(res => {
        this.aboutContent = res.data;
      });
    },
    updateAbout() {
      let param = new URLSearchParams();
      param.append("aboutContent", this.aboutContent);
      updateAbout(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.message
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: res.message
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.edit-btn {
  float: right;
  margin: 1rem 0;
}
</style>
