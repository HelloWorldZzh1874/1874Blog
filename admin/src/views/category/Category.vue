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
        :disabled="this.categoryIdList.length === 0"
        @click="isDelete = true"
      >
        批量删除
      </el-button>
      <div style="margin-left: auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入分类名"
          style="width: 200px"
          @keyup.enter.native="listCategories"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listCategories"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="categoryList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <!-- 分类名 -->
      <el-table-column prop="categoryName" label="分类名" align="center" />
      <!-- 分类创建时间 -->
      <el-table-column prop="createTime" label="创建时间" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" width="160" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="openModel(scope.row)">
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left: 1rem"
            @onConfirm="deleteCategory(scope.row.id)"
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
    <el-dialog :visible.sync="isDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color: #ff9900" />提示
      </div>
      <div style="font-size: 1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteCategory(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 添加编辑对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="30%">
      <div class="dialog-title-container" slot="title" ref="categoryTitle" />
      <el-form label-width="80px" size="medium" :model="categoryForm">
        <el-form-item label="分类名">
          <el-input
            @keyup.enter.native="listCategories"
            v-model="categoryForm.categoryName"
            style="width: 220px"
          />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditCategory"> 确 定 </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import {
  listCategories,
  saveOrUpdateCategory,
  deleteCategory
} from "../../api/index";
export default {
  created() {
    this.listCategories();
  },
  data: function() {
    return {
      isDelete: false,
      loading: true,
      addOrEdit: false,
      keywords: null,
      categoryIdList: [],
      categoryList: [],
      categoryForm: {
        id: null,
        categoryName: ""
      },
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    // 多选项改变触发
    selectionChange(categoryList) {
      this.categoryIdList = [];
      categoryList.forEach(item => {
        this.categoryIdList.push(item.id);
      });
    },
    // 每页展示数量改变时触发
    sizeChange(size) {
      this.size = size;
      this.listCategories();
    },
    // 改变当前页触发
    currentChange(current) {
      this.current = current;
      this.listCategories();
    },
    // 删除分类
    deleteCategory(id) {
      let param = {};
      if (id == null) {
        param = { data: this.categoryIdList };
      } else {
        param = { data: [id] };
      }
      deleteCategory(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
          /* 如果当前页面只有一个数据并且不是第一页，则使页数返回上一页 */
          if (this.categoryList.length <= 1 && this.current > 1) {
            this.current--;
          }
          this.listCategories();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
        this.isDelete = false;
      });
    },
    // 查找所有分类数据
    listCategories() {
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      listCategories(param).then(res => {
        if (res.code === "200") {
          this.categoryList = res.data.list;
          this.count = res.data.total;
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
        this.loading = false;
      });
    },
    // 打开修改或添加对话框，首先要进行相关操作
    openModel(category) {
      if (category != null) {
        this.categoryForm = JSON.parse(JSON.stringify(category));
        // 对修改或添加操作展示不同的标题
        this.$refs.categoryTitle.innerHTML = "修改分类";
      } else {
        this.categoryForm.id = null;
        this.categoryForm.categoryName = "";
        // 对修改或添加操作展示不同的标题
        this.$refs.categoryTitle.innerHTML = "添加分类";
      }
      this.addOrEdit = true;
    },
    // 添加或修改分类信息
    addOrEditCategory() {
      if (this.categoryForm.categoryName.trim() === "") {
        this.$message.error("分类名不能为空");
        return false;
      }
      saveOrUpdateCategory(this.categoryForm).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.message
          });
          this.listCategories();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.message
          });
        }
        this.addOrEdit = false;
      });
    }
  }
};
</script>
