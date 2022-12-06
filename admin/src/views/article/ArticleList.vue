<!--suppress ALL -->
<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        v-if="isDelete === 0"
        type="danger"
        size="small"
        icon="el-icon-deleteItem"
        :disabled="articleIdList.length === 0"
        @click="updateIsDelete = true"
      >
        批量删除
      </el-button>
      <el-button
        v-else
        type="danger"
        size="small"
        icon="el-icon-deleteItem"
        :disabled="articleIdList.length == 0"
        @click="remove = true"
      >
        批量删除
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left: auto">
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
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入文章名"
          style="width: 200px"
          @keyup.enter.native="listArticles"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listArticles"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="articleList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <el-table-column prop="articleTitle" label="标题" align="center" />
      <!-- 文章分类 -->
      <el-table-column
        prop="categoryName"
        label="分类"
        width="120"
        align="center"
      />
      <!-- 文章标签 -->
      <el-table-column
        prop="tagDTOList"
        label="标签"
        width="180"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag
            v-for="item of scope.row.tagDTOList"
            :key="item.tagId"
            style="margin-right: 0.2rem; margin-top: 0.2rem"
          >
            {{ item.tagName }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 文章浏览量 -->
      <el-table-column
        prop="viewsCount"
        label="浏览量"
        width="80"
        align="center"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.viewsCount">
            {{ scope.row.viewsCount }}
          </span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 文章点赞量 -->
      <el-table-column
        prop="likeCount"
        label="点赞量"
        width="80"
        align="center"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.likeCount">
            {{ scope.row.likeCount }}
          </span>
          <span v-else>0</span>
        </template>
      </el-table-column>
      <!-- 文章发表时间 -->
      <el-table-column
        prop="createTime"
        label="发表时间"
        width="140"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 文章修改时间 -->
      <el-table-column
        prop="updateTime"
        label="更新时间"
        width="140"
        align="center"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.updateTime">
            <i class="el-icon-time" style="margin-right: 5px" />
            {{ scope.row.updateTime | date }}
          </span>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <!-- 文章置顶 -->
      <el-table-column prop="isTop" label="置顶" width="100" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isTop"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :disabled="scope.row.isDelete == 1 || scope.row.isDraft == 1"
            :active-value="1"
            :inactive-value="0"
            @change="changeTop(scope.row)"
          />
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="editArticle(scope.row.id)"
            v-if="scope.row.isDelete == 0"
          >
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left: 10px"
            @onConfirm="updateArticleStatus(scope.row.id)"
            v-if="scope.row.isDelete == 0"
          >
            <el-button size="mini" type="danger" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
          <el-popconfirm
            title="确定恢复吗？"
            v-if="scope.row.isDelete == 1"
            @onConfirm="updateArticleStatus(scope.row.id)"
          >
            <el-button size="mini" type="success" slot="reference">
              恢复
            </el-button>
          </el-popconfirm>
          <el-popconfirm
            style="margin-left: 10px"
            v-if="scope.row.isDelete == 1"
            title="确定彻底删除吗？"
            @onConfirm="deleteArticles(scope.row.id)"
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
    <!-- 批量逻辑删除对话框 -->
    <el-dialog :visible.sync="updateIsDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color: #ff9900" />提示
      </div>
      <div style="font-size: 1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="updateIsDelete = false">取 消</el-button>
        <el-button type="primary" @click="updateArticleStatus(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 批量彻底删除对话框 -->
    <el-dialog :visible.sync="remove" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color: #ff9900" />提示
      </div>
      <div style="font-size: 1rem">是否彻底删除选中项？</div>
      <div slot="footer">
        <el-button @click="remove = false">取 消</el-button>
        <el-button type="primary" @click="deleteArticles(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import {
  getArticlesByPage,
  changeTop,
  deleteOrRecArticle,
  deleteArticles
} from "../../api/index";

export default {
  created() {
    this.listArticles();
  },
  data: function() {
    return {
      // 页面正在加载
      loading: true,
      // 是否逻辑删除对话框
      updateIsDelete: false,
      // 是否物理删除对话框
      remove: false,
      // 下拉列表数据，用于选择展示哪部分数据
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
      // 默认展示发布内容
      condition: '{"isDelete":0,"isDraft":0}',
      // 从后台查询的数据列表
      articleList: [],
      // 多选选择的id列表
      articleIdList: [],
      // 搜索关键字
      keywords: null,
      // 是否为逻辑删除文章
      isDelete: 0,
      // 是否为草稿
      isDraft: 0,
      // 当前页
      current: 1,
      // 每页数据量
      size: 10,
      // 数据总量
      count: 0
    };
  },
  methods: {
    // 多选改变触发
    selectionChange(articleList) {
      this.articleIdList = [];
      articleList.forEach(item => {
        this.articleIdList.push(item.id);
      });
    },
    // 编辑相关文章,携带id到编辑页面
    editArticle(id) {
      this.$router.push({ path: "/articles/" + id });
    },
    // 改变文章逻辑删除或恢复文章
    updateArticleStatus(id) {
      let param = new URLSearchParams();
      // 如果时修改单个对象则封装成list中的一个元素
      if (id != null) {
        param.append("idList", [id]);
      } else {
        param.append("idList", this.articleIdList);
      }
      // 对文章的逻辑删除值取反
      param.append("isDelete", this.isDelete === 0 ? 1 : 0);
      deleteOrRecArticle(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.message
          });
          /* 如果当前页面只有一个数据并且不是第一页，则使页数返回上一页 */
          if (this.articleList.length <= 1 && this.current > 1) {
            this.current--;
          }
          this.listArticles();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.message
          });
        }
        this.updateIsDelete = false;
      });
    },
    // 物理删除
    deleteArticles(id) {
      let param = {};
      // 对删除单个文章的处理
      if (id == null) {
        param = { data: this.articleIdList };
      } else {
        param = { data: [id] };
      }
      deleteArticles(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.message
          });
          /* 如果当前页面只有一个数据并且不是第一页，则使页数返回上一页 */
          if (this.articleList.length <= 1 && this.current > 1) {
            this.current--;
          }
          this.listArticles();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.message
          });
        }
        this.remove = false;
      });
    },
    // 每页数据改变
    sizeChange(size) {
      this.size = size;
      this.listArticles();
    },
    // 页数改变
    currentChange(current) {
      this.current = current;
      this.listArticles();
    },
    // 改变文章置顶
    changeTop(article) {
      let param = new URLSearchParams();
      param.append("isTop", article.isTop);
      changeTop(article.id, param);
    },
    // 查找文章列表
    listArticles() {
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      param.append("isDelete", this.isDelete);
      param.append("isDraft", this.isDraft === null ? "" : this.isDraft);
      getArticlesByPage(param).then(res => {
        if (res.code === "200") {
          this.articleList = res.data.list;
          this.count = res.data.total;
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
        this.loading = false;
      });
    }
  },
  watch: {
    condition() {
      const condition = JSON.parse(this.condition);
      this.isDelete = condition.isDelete;
      this.isDraft = condition.isDraft;
      this.listArticles();
    }
  }
};
</script>
