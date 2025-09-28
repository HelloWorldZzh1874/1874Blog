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
        style="margin-right: 50px"
      >
        新增任务
      </el-button>
      <!-- 数据筛选 -->
      <div style="margin-left: auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入任务名称"
          style="width: 200px"
          @keyup.enter.native="listQuartz"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listQuartz"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table border v-loading="loading" :data="quartzList">
      <!-- 表格列 -->
      <el-table-column prop="id" label="id" align="center" width="50" />
      <el-table-column
        prop="jobName"
        label="任务名字"
        align="center"
        width="150"
      />
      <el-table-column
        prop="jobClassname"
        label="任务类"
        align="center"
        width="100"
      />
      <el-table-column
        prop="jobGroup"
        label="任务组"
        align="center"
        width="150"
      >
        <template slot-scope="scope">
          <el-tag>{{ scope.row.jobGroup }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="cronExpression"
        label="corn表达式"
        align="center"
      />
      <el-table-column
        prop="status"
        label="任务状态"
        align="center"
        width="300"
      >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-text="运行中"
            inactive-text="已暂停"
            :active-value="1"
            :inactive-value="0"
            @change="changeStatus(scope.row.id, scope.row.status)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        prop="createBy"
        label="创建者"
        width="100"
        align="center"
      />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="200"
        align="center"
      >
        <template slot-scope="scope">
          {{ cutTimeDate(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="updateTime"
        label="更新时间"
        width="200"
        align="center"
      >
        <template slot-scope="scope">
          {{ cutTimeDate(scope.row.updateTime) }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-popconfirm
            title="确定删除吗？"
            @onConfirm="deleteJob(scope.row.id)"
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
    <!-- 添加对话框 -->
    <el-dialog title="添加任务" :visible.sync="addForm" width="30%" center>
      <div class="dialog-title-container" slot="title">添加定时任务</div>
      <el-form label-position="top" size="small" :model="jobForm">
        <el-form-item label="任务名">
          <el-input style="width: 250px" v-model="jobForm.jobName" />
        </el-form-item>
        <el-form-item label="任务类">
          <el-select v-model="jobForm.jobClassname" placeholder="请选择">
            <el-option
              v-for="item in jobClazzNameList"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="corn表达式">
          <el-input style="width: 250px" v-model="jobForm.cronExpression" />
          <el-button
            type="success"
            @click="checkCorn = true"
            style="margin-left: 2vw"
            >生成表达式</el-button
          >
        </el-form-item>
        <el-form-item label="任务所属组">
          <el-select v-model="jobForm.jobGroup" placeholder="请选择">
            <el-option
              v-for="item in jobGroups"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addForm = false">取 消</el-button>
        <el-button type="primary" @click="addJob"> 确 定 </el-button>
      </div>
    </el-dialog>
    <el-dialog title="生成 cron" :visible.sync="checkCorn">
      <cornFrom
        @hide="checkCorn = false"
        @fill="crontabFill"
        :expression="this.jobForm.cronExpression"
      ></cornFrom>
    </el-dialog>
  </el-card>
</template>

<script>
import {
  getQuartzInfo,
  addJob,
  pauseJobById,
  resumeJobById,
  deleteJobById
} from "../../api/index";

import { mixin } from "@/utils";
import cornFrom from "../../layout/components/CronCompoent/index";
export default {
  mixins: [mixin],
  components: { cornFrom },
  created() {
    this.listQuartz();
  },
  data: function() {
    return {
      loading: true,
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      startTime: null,
      endTime: null,
      quartzList: null,
      addForm: false,
      jobClazzNameList: null,
      jobGroups: null,
      checkCorn: false,
      jobForm: {
        jobName: "",
        jobClassname: "",
        cronExpression: "",
        jobGroup: ""
      }
    };
  },
  methods: {
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
    // 初始数据
    listQuartz() {
      this.quartzList = null;
      this.loading = true;
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      if (this.startTime) {
        param.append("startTime", this.startTime);
      }
      if (this.endTime) {
        param.append("endTime", this.endTime);
      }
      getQuartzInfo(param).then(res => {
        if (res.code === "200") {
          this.jobGroups = res.data.jobGroups;
          this.jobClazzNameList = res.data.jobClazzNameList;
          this.quartzList = res.data.quartzDtoList;
          this.count = res.data.total;
        } else {
          this.$message.error(res.msg);
        }
      });
      this.loading = false;
    },
    // 添加任务
    addJob() {
      addJob(this.jobForm).then(res => {
        if (res.code === "200") {
          this.$message.success(res.msg);
          this.listQuartz();
        } else {
          this.$message.error(res.msg);
        }
        this.addForm = false;
      });
    },
    openModel() {
      this.addForm = true;
    },
    // 改变任务运行状态
    changeStatus(id, status) {
      // 如果是已经运行则暂停--status是改变后的值
      if (status === 0) {
        pauseJobById(id).then(res => {
          if (res.code === "200") {
            this.$message.success(res.msg);
          } else {
            this.$message.error(res.msg);
          }
        });
      }
      // 如果是暂停则允许
      else if (status === 1) {
        resumeJobById(id).then(res => {
          if (res.code === "200") {
            this.$message.success(res.msg);
          } else {
            this.$message.error(res.msg);
          }
        });
      }
    },
    // 删除任务
    deleteJob(id) {
      let param = {};
      let data = { id: id };
      param = { params: data };
      deleteJobById(param).then(res => {
        if (res.code === "200") {
          this.$message.success(res.msg);
        } else {
          this.$message.error(res.msg);
        }
        this.listQuartz();
      });
    },
    crontabFill(val) {
      this.jobForm.cronExpression = val;
    }
  }
};
</script>
