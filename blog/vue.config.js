module.exports = {
  lintOnSave: false,
  publicPath: "/",
  // 静态文件目录
  assetsDir: "assets",
  //去除生产环境的productionSourceMap
  productionSourceMap: false,
  transpileDependencies: ["vuetify"],
  devServer: {
    port: 80,
    proxy: {
      "/websocket": {
        target: "http:/127.0.0.1/",
        changeOrigin: true,
        pathRewrite: {
          "^/": ""
        }
      }
    },
    disableHostCheck: true,
    overlay: {
      warning: false,
      errors: false
    }
  }
};
