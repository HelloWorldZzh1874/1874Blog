<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <!-- 条件筛选 -->
      <div style="margin-left: auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入歌曲名"
          style="width: 200px"
          @keyup.enter.native="listMusics"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listMusics"
        >
          搜索
        </el-button>
        <el-button
          type="primary"
          size="small"
          @click="centerDialogVisible = true"
          >添加歌曲</el-button
        >
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table border :data="musicList" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column
        prop="picUrl"
        label="歌曲图片"
        align="center"
        width="140"
      >
        <template slot-scope="scope">
          <div class="song-img">
            <img :src="getUrl(scope.row.picUrl)" style="width: 100%" />
          </div>
          <el-upload
            :action="updateAvatar(scope.row.id)"
            :headers="token"
            :on-success="handleSuccess"
            :before-upload="beforeAvatarUplode"
          >
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="歌曲名称"
        align="center"
        width="140"
      />
      <el-table-column prop="al" label="歌曲专辑" align="center" width="140" />
      <el-table-column
        prop="singer"
        label="歌手名称"
        align="center"
        width="140"
      >
        <template slot-scope="scope">
          <el-tag type="success">{{ scope.row.singer }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="歌词" align="center">
        <template slot-scope="scope">
          <ul style="height: 10vh; overflow-y: scroll; list-style: none">
            <li
              v-for="(item, index) in parseLyric(scope.row.lyric)"
              :key="index"
            >
              {{ item }}
            </li>
          </ul>
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleEdt(scope.row)">
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left: 10px"
            @onConfirm="deleteMusic(scope.row.id)"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 修改对话框 -->
    <el-dialog
      title="修改歌曲"
      :visible.sync="editDialogVisible"
      width="45vw"
      center
    >
      <el-form :model="editForm" ref="editForm" label-width="6vw">
        <el-form-item prop="name" label="歌名" size="mini">
          <el-input v-model="editForm.name" placeholder="歌名"></el-input>
        </el-form-item>
        <el-form-item prop="al" label="专辑" size="mini">
          <el-input v-model="editForm.al" placeholder="专辑"></el-input>
        </el-form-item>
        <el-form-item prop="lyric" label="歌词" size="mini">
          <el-input
            v-model="editForm.lyric"
            placeholder="歌词"
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="editSave">确定</el-button>
        <el-button size="mini" @click="editDialogVisible = false"
          >取消</el-button
        >
      </span>
    </el-dialog>
    <!-- 添加對話框 -->
    <el-dialog
      title="添加歌曲"
      :visible.sync="centerDialogVisible"
      width="45vw"
      center
    >
      <el-form
        :model="registerForm"
        ref="registerForm"
        label-width="6vw"
        action=""
        id="tf"
      >
        <div>
          <label>歌名</label>
          <el-input
            type="text"
            name="name"
            v-model="registerForm.name"
          ></el-input>
        </div>
        <div>
          <label>歌手</label>
          <div>
            <el-select
              v-model="registerForm.singerId"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="item in singers"
                :key="item.id"
                :label="item.singerName"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>
        </div>
        <div>
          <label>专辑</label>
          <el-input
            type="text"
            name="introduction"
            v-model="registerForm.introduction"
          ></el-input>
        </div>
        <div>
          <label>歌词</label>
          <el-input
            type="textarea"
            name="lyric"
            v-model="registerForm.lyric"
          ></el-input>
        </div>
        <div>
          <label>歌曲上传</label>
          <input type="file" name="file" />
        </div>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="addSong">确定</el-button>
        <el-button size="mini" @click="centerDialogVisible = false"
          >取消</el-button
        >
      </span>
    </el-dialog>
    <!-- 分页 -->
    <el-pagination
      class="pagination-container"
      background
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      :page-sizes="[10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
    />
  </el-card>
</template>

<script>
import Axios from "axios";
import {
  getBackMusics,
  listSingers,
  deleteMusic,
  updateInfo
} from "../../api/index";
import { mixin } from "../../utils";
export default {
  mixins: [mixin],
  created() {
    this.listMusics();
    this.listSingers();
  },
  data: function() {
    return {
      loading: true,
      singers: [],
      editDialogVisible: false,
      centerDialogVisible: false,
      editForm: {
        //编辑框
        id: "",
        al: "",
        name: "",
        lyric: ""
      },
      registerForm: {
        // 添加表单
        name: "",
        singerId: "",
        introduction: "",
        lyric: ""
      },
      musicList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      token: {
        token: localStorage.getItem("token")
      }
    };
  },
  methods: {
    // 每页数据
    sizeChange(size) {
      this.size = size;
      this.listMusics();
    },
    // 换页
    currentChange(current) {
      this.current = current;
      this.listMusics();
    },
    // 查询所有歌手
    listSingers() {
      listSingers().then(res => {
        this.singers = res.data;
      });
    },
    // 解析歌词
    parseLyric(text) {
      // 歌词行以换行分割
      let lines = (text || "").split("\n");
      // 正则表达式
      let patter = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
      let result = [];
      for (let item of lines) {
        // 将包含正则表达式对应内容的值置空
        let value = item.replace(patter, "");
        result.push(value);
      }
      return result;
    },
    // 展示音乐数据
    listMusics() {
      this.loading = true;
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      getBackMusics(param).then(res => {
        this.musicList = res.data.list;
        this.count = res.data.total;
        this.loading = false;
      });
    },
    // 编辑
    handleEdt(row) {
      this.editDialogVisible = true;
      this.editForm = {
        id: row.id,
        al: row.al,
        name: row.name,
        lyric: row.lyric
      };
    },
    // 保存修改
    editSave() {
      updateInfo(this.editForm).then(res => {
        if (res.code === "200") {
          this.notify(res.msg, "success");
          this.editDialogVisible = false;
          this.listMusics();
        } else {
          this.notify(res.msg, "error");
        }
      });
    },
    // 修改歌曲图片
    updateAvatar(id) {
      return `${this.$store.state.HOST}/music/admin/updateImg?id=${id}`;
    },
    // 修改成功钩子
    handleSuccess(res) {
      if (res.code === "200") {
        this.$message.success(res.msg);
        this.listMusics();
      } else {
        this.$message.error(res.msg);
      }
    },
    // 删除歌曲
    deleteMusic(id) {
      let param = {};
      let data = { id: id };
      param = { params: data };
      deleteMusic(param).then(res => {
        if (res.code === "200") {
          this.notify(res.msg, "success");
          this.listMusics();
        } else {
          this.notify(res.msg, "error");
        }
      });
    },
    // 添加歌曲
    addSong() {
      let _this = this;
      let form = new FormData(document.getElementById("tf"));
      if (!form.get("lyric")) {
        form.set("lyric", "[00:00:00]暂无歌词");
      }
      form.append("singerId", this.registerForm.singerId);
      Axios.post(this.$store.state.HOST + "/music/admin/add", form, {
        //设置请求头
        headers: { "Content-Type": "multipart/form-data" }
      }).then(res => {
        if (res.data.code === "200") {
          _this.notify(res.data.msg, "success");
          _this.listMusics();
        } else {
          _this.notify(res.data.msg, "error");
        }
      });
      _this.centerDialogVisible = false;
    }
  }
};
</script>

<style scoped>
.song-img {
  width: 100%;
  height: 12vh;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
</style>
