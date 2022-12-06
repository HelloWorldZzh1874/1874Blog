let path = require("path");
module.exports = {
  publicPath: "/",
  // 静态文件目录
  outputDir: "dist",
  assetsDir: "assets",
  //去除生产环境的productionSourceMap
  productionSourceMap: false,
  devServer: {
    port: 8081,
    host: "0.0.0.0",
    proxy: {
      "/api": {
        target: "http://127.0.0.1:8088",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        }
      }
    },
    overlay: {
      warning: false,
      errors: false
    },
    disableHostCheck: true
  },
  chainWebpack: config => {
    config.resolve.alias.set("@", resolve("src"));
  },
  lintOnSave: false
};
function resolve(dir) {
  return path.join(__dirname, dir);
}
