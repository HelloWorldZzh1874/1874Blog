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
    beforeAvatorUplode(file) {
      const isImg = file.type === "image/jpeg" || file.type === "image/png";
      if (!isImg) {
        this.$message.error("图片格式只能为JPG或PNG");
        return false;
      }
      const Islg2 = file.size / 1024 / 1024 < 5;
      if (!Islg2) {
        this.$message.error("上传图片大小必须小于5MB");
        return false;
      }
      return true;
    }
  }
};
