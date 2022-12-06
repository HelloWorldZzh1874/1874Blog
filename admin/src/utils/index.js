import da from "element-ui/src/locale/lang/da";

export const mixin = {
  methods: {
    // 提示信息
    notify(title, type) {
      this.$notify({
        title: title,
        type: type,
        duration: 2000
      });
    },

    // 根据相对地址获取绝对地址
    getUrl(url) {
      return `${this.$store.state.HOST}${url}`;
    },

    // 上传图之前校验
    beforeAvatarUplode(file) {
      const isImg = file.type === "image/jpeg" || file.type === "image/png";
      if (!isImg) {
        this.$message.error("图片格式只能为JPG或PNG");
        return false;
      }
      const Islg2 = file.size / 1024 / 1024 < 50;
      if (!Islg2) {
        this.$message.error("上传图片大小必须小于50MB");
        return false;
      }
      return true;
    },
    // 剪切时间
    cutTimeDate(dateTime) {
      if (dateTime != null) {
        return dateTime.substr(0, 19).replace("T", " ");
      }
    },
    // 转换性别
    changeSex(sex) {
      if (sex === "0") {
        return "女";
      } else if (sex === "1") {
        return "男";
      } else {
        return "未知";
      }
    }
  }
};
