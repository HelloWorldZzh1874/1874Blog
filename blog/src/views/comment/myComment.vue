<template>
  <div>
    <!-- banner -->
    <div class="archive-banner banner">
      <h1 class="banner-title" style="color: gray">我的回复</h1>
    </div>
    <!-- 回复列表 -->
    <v-card class="blog-container">
      <timeline>
        <timeline-title> 您共有 {{ this.total }} 条回复 </timeline-title>
        <timeline-item v-for="item of commentList" :key="item.id">
          <!-- 日期 -->
          <span class="time">{{ item.createTime | date }}</span>
          <!-- 回复内容 -->
          @<router-link
            @click.native="read(item)"
            :to="'/articles/' + item.articleId"
            style="color: #666; text-decoration: none"
          >
            <span style="font-weight: bold">{{ item.nickName }}</span> :
            {{
              item.content.length > 50
                ? item.content.substr(0, 50) + "..."
                : item.content
            }}
          </router-link>
          <el-tag style="margin-left: 0.75rem" v-if="!item.isRead" type="info"
            >未读</el-tag
          >
          <v-btn
            color="primary"
            style="float: right;"
            @click="deleteUserComment(item.commentId)"
          >
            删除
          </v-btn>
        </timeline-item>
      </timeline>
      <!-- 分页按钮 -->
      <v-pagination
        color="#00C4B6"
        v-model="current"
        :length="Math.ceil(total / this.size)"
        total-visible="7"
      />
      <v-snackbar v-model="snackbar" color="primary" outlined>
        {{ text }}
      </v-snackbar>
    </v-card>
  </div>
</template>

<script>
import { Timeline, TimelineItem, TimelineTitle } from "vue-cute-timeline";
import {
  deleteUserComment,
  getUserComment,
  changeReadStatus
} from "../../api/index";
export default {
  created() {
    this.listComments();
  },
  components: {
    Timeline,
    TimelineItem,
    TimelineTitle
  },
  data: function() {
    return {
      current: 1,
      size: 10,
      total: 0,
      snackbar: false,
      text: "",
      // 所有回复
      comments: null,
      // 分页回复展示
      commentList: []
    };
  },
  methods: {
    listComments() {
      getUserComment().then(res => {
        this.comments = res.data;
        this.commentList = this.comments.slice(
          this.current - 1,
          this.size * this.current
        );
        this.total = this.comments.length;
      });
    },
    deleteUserComment(id) {
      let param = {};
      let data = { id: id };
      param = { params: data };
      deleteUserComment(param).then(res => {
        this.snackbar = true;
        if (res.code === 200) {
          this.text = "删除成功!";
        } else {
          this.text = res.msg;
        }
        this.listComments();
      });
    },
    read(item) {
      if (!item.isRead) {
        changeReadStatus(item.commentId);
      }
    }
  },
  watch: {
    current() {
      this.commentList = this.comments.slice(
        this.current - 1,
        this.size * this.current
      );
    }
  }
};
</script>

<style scoped>
.archive-banner {
  background: url(https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/PageImg/comment.jpg)
    center center / cover no-repeat;
  background-color: #49b1f5;
}
.time {
  font-size: 0.75rem;
  color: #555;
  margin-right: 1rem;
}
</style>
