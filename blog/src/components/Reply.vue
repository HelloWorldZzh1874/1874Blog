<template>
  <div class="reply-input-wrapper" style="display: none" ref="reply">
    <textarea
      class="comment-textarea"
      :placeholder="'回复 @' + nickname + '：'"
      auto-grow
      dense
      v-model="commentContent"
    />
    <div class="emoji-container">
      <span
        :class="chooseEmoji ? 'emoji-btn-active' : 'emoji-btn'"
        @click="chooseEmoji = !chooseEmoji"
      >
        <i class="iconfont iconbiaoqing" />
      </span>
      <div style="margin-left: auto">
        <button @click="cancleReply" class="cancle-btn v-comment-btn">
          取消
        </button>
        <button @click="insertReply" class="upload-btn v-comment-btn">
          提交
        </button>
      </div>
    </div>
    <!-- 表情框 -->
    <emoji @addEmoji="addEmoji" :chooseEmoji="chooseEmoji"></emoji>
  </div>
</template>

<script>
import Emoji from "./Emoji";
import EmojiList from "../assets/js/emoji";
import {addComment} from "../api";

export default {
  components: {
    Emoji
  },
  data: function() {
    return {
      index: 0,
      chooseEmoji: false,
      nickname: "",
      replyId: null,
      parentId: null,
      commentContent: ""
    };
  },
  methods: {
    cancleReply() {
      this.$refs.reply.style.display = "none";
    },
    insertReply() {
      //判断登录
      if (!this.$store.state.id) {
        this.$store.state.loginFlag = true;
        return false;
      }
      if (this.commentContent.trim() === "") {
        this.$toast({ type: "error", message: "回复不能为空" });
        return false;
      }
      //解析表情
      let reg = /\[.+?\]/g;
      this.commentContent = this.commentContent.replace(reg, function(str) {
        return (
          "<img alt='表情' src= '" +
          EmojiList[str] +
          "' width='22' height='20' style='padding: 0 1px'/>"
        );
      });
      //发送请求
      const path = this.$route.path;
      const arr = path.split("/");
      const articleId = arr[arr.length - 1];
      let comment = {
        articleId: articleId,
        replyId: this.replyId,
        parentId: this.parentId,
        commentContent: this.commentContent
      };
      this.commentContent = "";
      addComment(comment).then(res => {
        if (res.code === "200") {
          this.$emit("reloadReply", this.index);
          this.$toast({ type: "success", message: res.msg });
        } else {
          this.$toast({ type: "warnning", message: res.message });
        }
      });
    },
    addEmoji(text) {
      this.commentContent += text;
    }
  }
};
</script>

<style scoped>
.reply-input-wrapper {
  border: 1px solid #90939950;
  border-radius: 4px;
  padding: 10px;
  margin: 0 0 10px;
}
</style>
