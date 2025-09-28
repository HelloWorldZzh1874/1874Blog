<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        type="danger"
        size="small"
        icon="el-icon-deleteItem"
        :disabled="messageIdList.length === 0"
        @click="deleteFlag = true"
      >
        批量删除
      </el-button>
      <!-- 数据筛选 -->
      <div style="margin-left: auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入用户昵称"
          style="width: 200px"
          @keyup.enter.native="listMessages"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listMessages"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      v-loading="loading"
      :data="messageList"
      @selection-change="selectionChange"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <el-table-column prop="avatar" label="头像" align="center" width="150">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar" width="40" height="40"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="留言人"
        align="center"
        width="150"
      />
      <el-table-column prop="messageContent" label="留言内容" align="center" />
      <el-table-column
        prop="ipAddress"
        label="ip地址"
        align="center"
        width="150"
      />
      <el-table-column
        prop="ipSource"
        label="ip来源"
        align="center"
        width="170"
      />
      <el-table-column
        prop="createTime"
        label="留言时间"
        width="140"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-popconfirm
            title="确定删除吗？"
            @onConfirm="deleteMessage(scope.row.id)"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
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
    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="deleteFlag" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color: #ff9900" />提示
      </div>
      <div style="font-size: 1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="deleteFlag = false">取 消</el-button>
        <el-button type="primary" @click="deleteMessage(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { listBackMessages, deleteMessage } from "../../api/index";

export default {
  created() {
    this.listMessages();
  },
  data: function() {
    return {
      loading: true,
      deleteFlag: false,
      messageIdList: [],
      messageList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    // 多选项
    selectionChange(messageList) {
      this.messageIdList = [];
      messageList.forEach(item => {
        this.messageIdList.push(item.id);
      });
    },
    // 页面展示数据项改变
    sizeChange(size) {
      this.size = size;
      this.listMessages();
    },
    // 改变页数
    currentChange(current) {
      this.current = current;
      this.listMessages();
    },
    // 删除留言
    deleteMessage(id) {
      let param = {};
      if (id != null) {
        param = { data: [id] };
      } else {
        param = { data: this.messageIdList };
      }
      deleteMessage(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
          /* 如果当前页面只有一个数据并且不是第一页，则使页数返回上一页 */
          if (this.messageList.length <= 1 && this.current > 1) {
            this.current--;
          }
          this.listMessages();
        } else {
          this.$notify.error({
            title: "失败",
            message: "服务器错误，请稍后再试"
          });
        }
        this.deleteFlag = false;
      });
    },
    // 查找后台留言数据
    listMessages() {
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      listBackMessages(param).then(res => {
        this.messageList = res.data.list;
        this.count = res.data.total;
        this.loading = false;
      });
    }
  }
};
</script>
