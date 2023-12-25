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
          placeholder="请输入歌手名"
          style="width: 200px"
          @keyup.enter.native="listSingers"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listSingers"
        >
          搜索
        </el-button>
        <el-button type="primary" size="small" @click="openAdd"
          >添加歌手</el-button
        >
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table border :data="singerList" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column
        prop="singerPic"
        label="歌手图片"
        align="center"
        width="140"
      >
        <template slot-scope="scope">
          <div class="singer-img">
            <img
              :src="getUrl(scope.row.singerPic)"
              style="width: 100%"
              alt="歌手图片"
            />
          </div>
          <el-upload
            :action="updateAvatar(scope.row.id)"
            :on-success="handleSuccess"
            :headers="token"
            :before-upload="beforeAvatarUplode"
            accept=".jpg,.jpeg,.png,.gif,.bmp,.pdf,.JPG,.JPEG"
          >
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column
        prop="singerName"
        label="歌手名称"
        align="center"
        width="140"
      />
      <el-table-column prop="singerSex" label="性别" align="center" width="100">
        <template slot-scope="scope">
          <el-tag type="success">{{ changeSex(scope.row.singerSex) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="singerBirth"
        label="生日"
        align="center"
        width="200"
      >
        <template slot-scope="scope">
          {{ scope.row.singerBirth.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="singerLocation"
        label="地区"
        align="center"
        width="200"
      />
      <el-table-column prop="singerIntroduction" label="简介" align="center" />
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="SingerMusic(scope.row.id)"
          >
            歌曲管理
          </el-button>
          <el-button type="primary" size="mini" @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left: 10px"
            @confirm="deleteMusic(scope.row.id)"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 對話框 -->
    <el-dialog
      :title="this.title"
      :visible.sync="centerDialogVisible"
      width="45vw"
      center
    >
      <el-form
        ref="form"
        addOrUpdateaddOrUpdate
        :model="registerForm"
        label-width="80px"
      >
        <el-form-item label="歌手名称">
          <el-input v-model="registerForm.singerName"></el-input>
        </el-form-item>
        <el-form-item label="歌手性别">
          <el-select v-model="registerForm.singerSex" placeholder="请选择">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            v-model="registerForm.singerBirth"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="歌手地区">
          <el-input v-model="registerForm.singerLocation"></el-input>
        </el-form-item>

        <el-form-item label="歌手简介">
          <el-input
            type="textarea"
            autosize
            v-model="registerForm.singerIntroduction"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="addOrUpdate">确定</el-button>
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
import {
  getBackSingers,
  addOrUpdateSinger,
  deleteSinger
} from "../../api/index";
import { mixin } from "@/utils";
export default {
  mixins: [mixin],
  created() {
    this.listSingers();
  },
  data: function() {
    return {
      loading: true,
      centerDialogVisible: false,
      title: "",
      registerForm: {
        // 添加表单
        id: "",
        singerName: "",
        singerSex: "",
        singerBirth: "",
        singerLocation: "",
        singerIntroduction: ""
      },
      singerList: [],
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
      this.listSingers();
    },
    // 分页
    currentChange(current) {
      this.current = current;
      this.listSingers();
    },
    // 列出所有歌手列表
    listSingers() {
      this.loading = true;
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      getBackSingers(param).then(res => {
        this.singerList = res.data.list;
        this.count = res.data.total;
        this.loading = false;
      });
    },
    // 打开添加对话框
    openAdd() {
      this.centerDialogVisible = true;
      this.title = "添加歌手";
    },
    // 编辑
    handleEdit(row) {
      this.title = "修改歌手";
      this.centerDialogVisible = true;
      this.registerForm.id = row.id;
      this.registerForm.singerName = row.singerName;
      this.registerForm.singerSex = row.singerSex === true ? "1" : "0";
      this.registerForm.singerBirth = row.singerBirth;
      this.registerForm.singerLocation = row.singerLocation;
      this.registerForm.singerIntroduction = row.singerIntroduction;
    },
    // 上传歌手图片
    updateAvatar(id) {
      return `${this.$store.state.HOST}/singer/admin/updateImg?id=${id}`;
    },
    // 上传后钩子
    handleSuccess(res) {
      if (res.code === "200") {
        this.$message.success(res.msg);
        this.listSingers();
      } else {
        this.$message.error(res.msg);
      }
    },
    // 根据id删除歌手
    deleteMusic(id) {
      let param = {};
      let data = { id: id };
      param = { params: data };
      deleteSinger(param).then(res => {
        if (res.code === "200") {
          this.notify(res.msg, "success");
          this.listSingers();
        } else {
          this.notify(res.msg, "error");
        }
      });
    },
    // 添加
    addOrUpdate() {
      this.registerForm.singerSex = this.registerForm.singerSex === "1";
      addOrUpdateSinger(this.registerForm).then(res => {
        if (res.code === "200") {
          this.notify(res.msg, "success");
          this.listSingers();
        } else {
          this.notify(res.msg, "error");
        }
      });
      this.registerForm = {
        // 重置表单
        id: "",
        singerName: "",
        singerSex: "",
        singerBirth: "",
        singerLocation: "",
        singerIntroduction: ""
      };
      this.centerDialogVisible = false;
    },
    // 跳转至歌手歌曲页面
    SingerMusic(id) {
      this.$router.push({ path: "/singer/musics", query: { id } });
    }
  }
};
</script>

<style scoped>
.singer-img {
  width: 100%;
  height: 12vh;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
</style>
