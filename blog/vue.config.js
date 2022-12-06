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
        target: "http:/81.68.122.101/",
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
