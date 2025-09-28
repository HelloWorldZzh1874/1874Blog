<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <div class="operation-container">
      <!-- 数据筛选 -->
      <div style="margin-left: auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入用户昵称"
          style="width: 200px"
          @keyup.enter.native="listOnlineUsers"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listOnlineUsers"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 用户列表 -->
    <el-table v-loading="loading" :data="userList">
      <el-table-column prop="avatar" label="头像" align="center" width="100">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar" width="40" height="40"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" align="center" />
      <el-table-column prop="ipAddr" label="ip地址" align="center" />
      <el-table-column prop="ipSource" label="登录地址" align="center" />
      <el-table-column
        prop="browser"
        label="浏览器"
        align="center"
        width="200"
      />
      <el-table-column prop="os" label="操作系统" align="center" />
      <el-table-column
        prop="lastLoginTime"
        label="登录时间"
        align="center"
        width="200"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ cutTimeDate(scope.row.lastLoginTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150">
        <template slot-scope="scope">
          <el-popconfirm
            title="确定下线吗？"
            style="margin-left: 10px"
            @onConfirm="removeOnlineUser(scope.row.id)"
          >
            <el-button size="mini" type="text" slot="reference">
              <i class="el-icon-delete" /> 强制下线
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
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
import { getOnlineUsers, forceOffline } from "../../api/index";
import { mixin } from "@/utils";
export default {
  mixins: [mixin],
  created() {
    this.listOnlineUsers();
  },
  data() {
    return {
      loading: true,
      userList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      isCheck: false,
      optLog: {}
    };
  },
  methods: {
    listOnlineUsers() {
      this.loading = true;
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      getOnlineUsers(param).then(res => {
      this.userList = res.data.list;
        this.count = res.data.total;
        this.loading = false;
      });
    },
    // 每页数据改变
    sizeChange(size) {
      this.size = size;
      this.listOnlineUsers();
    },
    // 改变页码
    currentChange(current) {
      this.current = current;
      this.listOnlineUsers();
    },
    removeOnlineUser(userId) {
      let param = new URLSearchParams();
      param.append("userId", userId);
      forceOffline(param).then(res => {
        if (res.code === "200") {
          this.listOnlineUsers();
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
        } else {
          this.$notify.error({
            title: "错误",
            message: res.msg
          });
        }
      });
    }
  }
};
</script>

<style scoped>
label {
  font-weight: bold !important;
}
</style>
