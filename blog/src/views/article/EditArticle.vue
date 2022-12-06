<template>
  <div>
    <el-card class="main-card">
      <router-link to="/myArticle">
        <el-button>返回</el-button>
      </router-link>
      <div class="title">{{ this.$route.name }}</div>
      <!-- 文章标题 -->
      <div class="article-title-container">
        <el-input
            v-model="article.articleTitle"
            size="medium"
            placeholder="输入文章标题"
        />
        <el-button
            type="danger"
            size="medium"
            class="save-btn"
            @click="saveArticleDraft"
            v-if="article.isDraft != 0"
        >
          保存草稿
        </el-button>
        <el-button
            type="danger"
            size="medium"
            @click="addOrEdit = true"
            style="margin-left: 10px"
        >
          发布文章
        </el-button>
      </div>
      <!-- 文章内容 -->
      <mavon-editor
          ref="md"
          v-model="article.articleContent"
          @imgAdd="uploadImg"
          style="height: calc(100vh - 260px)"
      />
      <!-- 添加文章对话框 -->
      <el-dialog :visible.sync="addOrEdit" width="40%" top="10vh">
        <div class="dialog-title-container" slot="title">上传文章</div>
        <!-- 文章数据 -->
        <el-form label-width="80px" size="medium" :model="article">
          <el-form-item label="文章分类">
            <el-select v-model="article.categoryId" placeholder="请选择分类">
              <el-option
                  v-for="item in categoryList"
                  :key="item.id"
                  :label="item.categoryName"
                  :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="文章标签">
            <el-select
                v-model="article.tagIdList"
                multiple
                placeholder="请选择标签"
            >
              <el-option
                  v-for="item in tagList"
                  :key="item.id"
                  :label="item.tagName"
                  :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="上传封面">
            <el-upload
                class="upload-cover"
                drag
                :before-upload="beforeAvatorUplode"
                action="http://81.68.122.101/article/uploadCover"
                multiple
                :on-remove="deleteCover"
                :on-success="uploadCover"
            >
              <i class="el-icon-upload" v-if="article.articleCover == ''"/>
              <div class="el-upload__text" v-if="article.articleCover == ''">
                将文件拖到此处，或<em>点击上传</em>
              </div>
              <img
                  v-else
                  :src="article.articleCover"
                  width="360px"
                  height="180px"
              />
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button @click="addOrEdit = false">取 消</el-button>
          <el-button type="danger" @click="saveOrUpdateArticle">
            发 表
          </el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import {
  getArticleOptions,
  saveAndUpdateArticle,
  saveArticleImg,
  deleteArticleImg,
  getArticleById
} from '../../api/index'
import {mixin} from "../../utils/index"

export default {
  mixins: [mixin],
  created() {
    const path = this.$route.path;
    const arr = path.split("/");
    const articleId = arr[3];
    // 如果是add则是添加功能
    if (articleId != 'add') {
      getArticleById(articleId).then(res => {
        if (res.code === "200") {
          this.article = res.data;
        } else {
          this.$message.error(res.message)
        }
      });
    }
    this.listArticleOptions();
  },
  destroyed() {
    //文章自动保存功能
    this.autoSaveArticle();
  },
  data: function () {
    return {
      addOrEdit: false,
      autoSave: true,
      categoryList: [],
      tagList: [],
      article: {
        id: null,
        articleTitle: "",
        articleContent: "",
        articleCover: "",
        categoryId: null,
        tagIdList: [],
        isTop: 0,
        isDraft: null
      }
    };
  },
  methods: {
    listArticleOptions() {
      getArticleOptions().then(res => {
        this.categoryList = res.data.categoryList;
        this.tagList = res.data.tagList;
      })
    },

    uploadCover(response) {
      this.article.articleCover = response.data;
    },

    uploadImg(pos, file) {
      var formdata = new FormData();
      formdata.append("file", file);
      saveArticleImg(formdata).then(res => {
        if (res.code === "200") {
          this.$refs.md.$img2Url(pos, res.data);
        } else {
          this.$message.error(res.message)
        }
      });
    },

    saveArticleDraft() {
      if (this.article.articleTitle.trim() == "") {
        this.$message.error("文章标题不能为空");
        return false;
      }
      if (this.article.articleContent.trim() == "") {
        this.$message.error("文章内容不能为空");
        return false;
      }
      this.article.isDraft = 1;
      this.article.id = this.$store.state.id;
      saveAndUpdateArticle(this.article).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: "保存草稿成功"
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: "保存草稿失败"
          });
        }
      })
      //关闭自动保存功能
      this.autoSave = false;
    },

    deleteCover() {
      let param = new URLSearchParams;
      param.append("key", this.article.articleCover)
      deleteArticleImg(param).then(res => {
        if (res.code === "200") {
          this.article.articleCover = ""
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      })
    },

    saveOrUpdateArticle() {
      if (this.article.articleTitle.trim() == "") {
        this.$message.error("文章标题不能为空");
        return false;
      }
      if (this.article.articleContent.trim() == "") {
        this.$message.error("文章内容不能为空");
        return false;
      }
      if (!this.article.categoryId) {
        this.$message.error("文章分类不能为空");
        return false;
      }
      if (this.article.tagIdList.length == 0) {
        this.$message.error("文章标签不能为空");
        return false;
      }
      if (this.article.articleCover.trim() == "") {
        this.article.articleCover = "https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/Articles/DefaultCover.webp"
      }
      this.article.isDraft = 0;
      this.article.id = this.$store.state.id;
      saveAndUpdateArticle(this.article).then(res => {
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
        this.addOrEdit = false;
      });
      //关闭自动保存功能
      this.autoSave = false;
    },

    autoSaveArticle() {
      if (
          this.autoSave &&
          this.article.articleTitle.trim() != "" &&
          this.article.articleContent.trim() != ""
      ) {
        this.article.isDraft = this.article.isDraft == 0 ? this.article.isDraft : 1;
        this.article.id = this.$store.state.id;
        saveAndUpdateArticle(this.article).then(res => {
          if (res.code === "200") {
            this.$notify.success({
              title: "成功",
              message: "保存成功"
            });
          } else {
            this.$notify.error({
              title: "失败",
              message: "保存失败"
            });
          }
        })
      }
    }
  }
};
</script>

<style scoped>
.MyArticle-banner {
  background: url(https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/PageImg/art.jpg) center center / cover no-repeat;
  background-color: #49b1f5;
}

.article-title-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
  margin-top: 2.25rem;
}

.save-btn {
  margin-left: 0.75rem;
  background: #fff;
  color: #f56c6c;
}

.main-card {
  min-height: calc(100vh - 126px);
}

.title {
  position: absolute;
  left: 0;
  font-size: 16px;
  font-weight: bold;
  color: #202a34;
}

.title::before {
  content: "";
  width: 24px;
  height: 16px;
  border-left: 3px solid #0081ff;
  margin-right: 20px;
}

.dialog-title-container {
  display: flex;
  align-items: center;
  font-weight: bold;
}

.dialog-title-container i {
  font-size: 1.5rem;
  margin-right: 0.5rem;
}
</style>
