<template>
  <div>
    <!-- 标签或分类名 -->
    <div class="MyArticle-banner banner">
      <h1 class="banner-title animated fadeInDown">我的文章</h1>
    </div>
    <div class="article-list-wrapper">
      <div class="select-wrapper">
        <el-select
          v-model="condition"
          placeholder="请选择"
          size="small"
          style="margin-right: 1rem"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
      <v-row>
        <v-col md="4" cols="12" v-for="item of articleList" :key="item.id">
          <!-- 文章 -->
          <v-card class="animated zoomIn article-item-card">
            <div class="article-item-cover">
              <router-link :to="'/articles/' + item.id">
                <!-- 缩略图 -->
                <v-img
                  class="on-hover"
                  width="100%"
                  height="100%"
                  :src="item.articleCover"
                />
              </router-link>
            </div>
            <div class="article-item-info">
              <!-- 文章标题 -->
              <div>
                <router-link :to="'/articles/' + item.id">
                  {{ item.articleTitle }}
                </router-link>
              </div>
              <div style="margin-top: 0.375rem">
                <!-- 发表时间 -->
                <v-icon size="20">mdi-clock-outline</v-icon>
                {{ item.createTime | date }}
                <!-- 文章分类 -->
                <router-link
                  :to="'/categories/' + item.categoryId"
                  class="float-right"
                >
                  <v-icon>mdi-bookmark</v-icon>{{ item.categoryName }}
                </router-link>
              </div>
            </div>
            <!-- 分割线 -->
            <v-divider></v-divider>
            <!-- 文章标签 -->
            <div class="tag-wrapper">
              <router-link
                :to="'/tags/' + tag.id"
                class="tag-btn"
                v-for="tag of item.tagDTOList"
                :key="tag.id"
              >
                {{ tag.tagName }}
              </router-link>
              <span class="btn-class">
                <el-button
                  type="primary"
                  round
                  size="mini"
                  v-if="isEditBtn"
                  @click="editArticle(item.id)"
                  >编辑</el-button
                >
                <el-button
                  type="warning"
                  round
                  size="mini"
                  v-if="isEditBtn"
                  @click="chengeDelete(item.id)"
                  >删除</el-button
                >
                <el-button
                  type="warning"
                  round
                  size="mini"
                  v-if="isDeleteBtn"
                  @click="deleteArticle(item.id)"
                  >彻底删除</el-button
                >
                <el-button
                  type="success"
                  round
                  size="mini"
                  v-if="isDeleteBtn"
                  @click="chengeDelete(item.id)"
                  >恢复</el-button
                >
              </span>
            </div>
          </v-card>
        </v-col>
      </v-row>
      <!-- 逻辑更改对话框 -->
      <el-dialog :visible.sync="updateIsDelete" width="30%">
        <div class="dialog-title-container" slot="title">
          <i class="el-icon-warning" style="color: #ff9900" />提示
        </div>
        <div style="font-size: 1rem">是否{{ stateName }}选中项？</div>
        <div slot="footer">
          <el-button @click="updateIsDelete = false">取 消</el-button>
          <el-button type="primary" @click="updateArticleStatus()">
            确 定
          </el-button>
        </div>
      </el-dialog>
      <!-- 彻底删除对话框 -->
      <el-dialog :visible.sync="remove" width="30%">
        <div class="dialog-title-container" slot="title">
          <i class="el-icon-warning" style="color: #ff9900" />提示
        </div>
        <div style="font-size: 1rem">是否彻底删除选中项？</div>
        <div slot="footer">
          <el-button @click="remove = false">取 消</el-button>
          <el-button type="primary" @click="deleteArticles()">
            确 定
          </el-button>
        </div>
      </el-dialog>
      <!-- 无限加载 -->
      <infinite-loading @infinite="infiniteHandler">
        <div slot="no-more" />
      </infinite-loading>
    </div>
  </div>
</template>

<script>
import { getUserArticle, deleteOrRecArticle, deleteArticles } from '../../api/index'

export default {
  created () {
  },
  data: function () {
    return {
      current: 1,
      deleteId: '',
      remove: false,
      isEditBtn: true,
      isDeleteBtn: false,
      isDraftBtn: false,
      stateName: null,
      updateIsDelete: false,
      articleList: [],
      categoryOrTag: "",
      isDelete: 0,
      isDraft: 0,
      options: [
        {
          value: '{"isDelete":0,"isDraft":0}',
          label: "已发布"
        },
        {
          value: '{"isDelete":1,"isDraft":null}',
          label: "回收站"
        },
        {
          value: '{"isDelete":0,"isDraft":1}',
          label: "草稿箱"
        }
      ],
      condition: '{"isDelete":0,"isDraft":0}',
    }
  },
  methods: {
    chengeDelete (id) {
      this.deleteId = id;
      this.updateIsDelete = true;
      this.isDelete == 0 ? this.stateName = "删除" : this.stateName = "恢复"
    },
    deleteArticle (id) {
      this.deleteId = id;
      this, this.remove = true;
    },
    editArticle (id) {
      this.$router.push({ path: "/articles/edit/" + id });
    },
    infiniteHandler ($state) {
      const userId = this.$store.state.id
      getUserArticle(
        userId,
        this.current,
        this.isDelete === null ? '' : this.isDelete,
        this.isDraft === null ? '' : this.isDraft).then(res => {
          if (res.data.articlePreviewDTOList.length) {
            this.current++;
            this.articleList.push(...res.data.articlePreviewDTOList);
            $state.loaded();
          } else {
            $state.complete();
          }
        });
    },
    updateArticleStatus () {
      let param = new URLSearchParams();
      param.append("idList", this.deleteId);
      param.append("isDelete", this.isDelete == 0 ? 1 : 0);
      deleteOrRecArticle(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.message
          });
          if (this.articleList.length <= 1) {
            this.articleList = null
          } else {
            this.current = 1;
            this.listArticles();
          }
        } else {
          this.$notify.error({
            title: "失败",
            message: res.message
          });
        }
        this.updateIsDelete = false;
      })
    },
    deleteArticles () {
      var param = {};
      param = { data: [this.deleteId] };
      deleteArticles(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.message
          });
          if (this.articleList.length <= 1) {
            this.articleList = null
          } else {
            this.current = 1;
            this.listArticles();
          }
        } else {
          this.$notify.error({
            title: "失败",
            message: res.message
          });
        }
        this.remove = false;
      });
    },
    listArticles () {
      getUserArticle(
        this.$store.state.id,
        this.current,
        this.isDelete === null ? '' : this.isDelete,
        this.isDraft === null ? '' : this.isDraft).then(res => {
          if (res.data.articlePreviewDTOList.length) {
            this.current++;
            this.articleList = res.data.articlePreviewDTOList;
          }
        });
      this.loading = false;
    }
  },
  watch: {
    condition () {
      const condition = JSON.parse(this.condition);
      this.isDelete = condition.isDelete;
      this.isDraft = condition.isDraft;
      if (this.isDraft === 1) {
        this.isEditBtn = true
        this.isDraftBtn = true
        this.isDeleteBtn = false
      } else if (this.isDelete === 1) {
        this.isEditBtn = false
        this.isDraftBtn = false
        this.isDeleteBtn = true
      } else {
        this.isEditBtn = true,
          this.isDeleteBtn = false,
          this.isDraftBtn = false
      }
      this.current = 1;
      this.articleList = null;
      this.listArticles();
    }
  }
}
</script>

<style scoped>
.MyArticle-banner {
  background: url(https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/PageImg/art.jpg)
    center center / cover no-repeat;
  background-color: #49b1f5;
}
@media (min-width: 760px) {
  .article-list-wrapper {
    max-width: 1106px;
    margin: 370px auto 1rem auto;
  }
  .article-item-card:hover {
    transition: all 0.3s;
    box-shadow: 0 4px 12px 12px rgba(7, 17, 27, 0.15);
  }
  .article-item-card:not(:hover) {
    transition: all 0.3s;
  }
  .article-item-card:hover .on-hover {
    transition: all 0.6s;
    transform: scale(1.1);
  }
  .article-item-card:not(:hover) .on-hover {
    transition: all 0.6s;
  }
  .article-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
    font-size: 15px;
  }
  .select-wrapper {
    margin: 170px auto 1rem auto;
  }
  .btn-class {
    float: right;
  }
}
@media (max-width: 759px) {
  .article-list-wrapper {
    margin-top: 230px;
    padding: 0 12px;
  }
  .article-item-info {
    line-height: 1.7;
    padding: 15px 15px 12px 18px;
  }

  .btn-class {
    float: right;
  }
}
.article-item-card {
  border-radius: 8px !important;
  box-shadow: 0 4px 8px 6px rgba(7, 17, 27, 0.06);
}
.article-item-card a {
  transition: all 0.3s;
}
.article-item-cover {
  height: 220px;
  overflow: hidden;
}
.article-item-card a:hover {
  color: #8e8cd8;
}
.tag-wrapper {
  padding: 10px 15px 10px 18px;
}
.tag-wrapper a {
  color: #fff !important;
}
.tag-btn {
  display: inline-block;
  font-size: 0.725rem;
  line-height: 22px;
  height: 22px;
  border-radius: 10px;
  padding: 0 12px !important;
  background: linear-gradient(to right, #bf4643 0%, #6c9d8f 100%);
  opacity: 0.6;
  margin-right: 0.5rem;
}
</style>