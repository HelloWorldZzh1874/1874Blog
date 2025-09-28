<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openModel(null)"
      >
        新增
      </el-button>
      <el-button
        type="danger"
        size="small"
        icon="el-icon-deleteItem"
        :disabled="linkIdList.length == 0"
        @click="deleteFlag = true"
      >
        批量删除
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left: auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入友链名"
          style="width: 200px"
          @keyup.enter.native="listLinks"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listLinks"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="linkList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <el-table-column
        prop="linkAvatar"
        label="链接头像"
        align="center"
        width="180"
      >
        <template slot-scope="scope">
          <el-avatar
            :src="scope.row.linkAvatar"
            width="40"
            height="40"
          ></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="linkName" label="链接名" align="center" />
      <el-table-column prop="linkAddress" label="链接地址" align="center" />
      <el-table-column prop="linkIntro" label="链接介绍" align="center" />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="140"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="openModel(scope.row)">
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left: 1rem"
            @onConfirm="deleteLink(scope.row.id)"
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
        <el-button type="primary" @click="deleteLink(null)"> 确 定 </el-button>
      </div>
    </el-dialog>
    <!-- 添加对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="30%">
      <div class="dialog-title-container" slot="title" ref="linkTitle" />
      <el-form label-width="80px" size="medium" :model="linkForm">
        <el-form-item label="链接名">
          <el-input style="width: 250px" v-model="linkForm.linkName" />
        </el-form-item>
        <el-form-item label="链接头像">
          <el-input style="width: 250px" v-model="linkForm.linkAvatar" />
        </el-form-item>
        <el-form-item label="链接介绍">
          <el-input style="width: 250px" v-model="linkForm.linkIntro" />
        </el-form-item>
        <el-form-item label="链接地址">
          <el-input style="width: 250px" v-model="linkForm.linkAddress" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditLink"> 确 定 </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { getBackLinkList, addOrUpdateLink, deleteLinks } from "../../api/index";
export default {
  created() {
    this.listLinks();
  },
  data: function() {
    return {
      loading: true,
      deleteFlag: false,
      addOrEdit: false,
      linkIdList: [],
      linkList: [],
      linkForm: {
        id: null,
        linkName: "",
        linkAvatar: "",
        linkIntro: "",
        linkAddress: ""
      },
      keywords: null,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    // 多选选择
    selectionChange(linkList) {
      this.linkIdList = [];
      linkList.forEach(item => {
        this.linkIdList.push(item.id);
      });
    },
    // 每页显示
    sizeChange(size) {
      this.size = size;
      this.listLinks();
    },
    // 翻页
    currentChange(current) {
      this.current = current;
      this.listLinks();
    },
    // 删除友链
    deleteLink(id) {
      let param = {};
      if (id == null) {
        param = { data: this.linkIdList };
      } else {
        param = { data: [id] };
      }
      deleteLinks(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.message
          });
          /* 如果当前页面只有一个数据并且不是第一页，则使页数返回上一页 */
          if (this.linkList.length <= 1 && this.current > 1) {
            this.current--;
          }
          this.listLinks();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.message
          });
        }
        this.deleteFlag = false;
      });
    },
    // 打开友链修改或添加对话框
    openModel(link) {
      if (link != null) {
        this.linkForm = JSON.parse(JSON.stringify(link));
        this.$refs.linkTitle.innerHTML = "修改友链";
      } else {
        this.linkForm.id = null;
        this.linkForm.linkName = "";
        this.linkForm.linkAvatar = "";
        this.linkForm.linkIntro = "";
        this.linkForm.linkAddress = "";
        this.$refs.linkTitle.innerHTML = "添加友链";
      }
      this.addOrEdit = true;
    },
    // 添加或更新友链请求
    addOrEditLink() {
      if (this.linkForm.linkName.trim() === "") {
        this.$message.error("友链名不能为空");
        return false;
      }
      if (this.linkForm.linkAvatar.trim() === "") {
        this.$message.error("友链头像不能为空");
        return false;
      }
      if (this.linkForm.linkIntro.trim() === "") {
        this.$message.error("友链介绍不能为空");
        return false;
      }
      if (this.linkForm.linkAddress.trim() === "") {
        this.$message.error("友链地址不能为空");
        return false;
      }
      addOrUpdateLink(this.linkForm).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
          this.listLinks();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
        this.addOrEdit = false;
      });
    },
    // 查询友链列表
    listLinks() {
      this.loading = true;
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      getBackLinkList(param).then(res => {
        this.linkList = res.data.list;
        this.count = res.data.total;
        this.loading = false;
      });
    }
  }
};
</script>
