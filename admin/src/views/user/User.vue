<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <!-- 条件筛选 -->
      <el-button
        type="primary"
        size="small"
        style="margin-left: 1rem"
        @click="adminModel = true"
      >
        添加管理员
      </el-button>
      <div style="margin-left: auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入昵称"
          style="width: 200px"
          @keyup.enter.native="listUsers"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listUsers"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table border :data="userList" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column
        prop="linkAvatar"
        label="头像"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar" width="40" height="40"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="昵称"
        align="center"
        width="140"
      />
      <el-table-column prop="role" label="用户角色" align="center">
        <template slot-scope="scope">
          <el-tag style="margin-right: 4px; margin-top: 4px">
            {{ scope.row.role }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="" label="禁用" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enabled"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="0"
            :inactive-value="1"
            @change="changeDisable(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="ipAddr"
        label="登录ip"
        align="center"
        width="140"
      />
      <el-table-column
        prop="ipSource"
        label="登录地址"
        align="center"
        width="140"
      />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="130"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <el-table-column
        prop="lastLoginTime"
        label="上次登录时间"
        width="130"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ scope.row.lastLoginTime | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="openEditModel(scope.row)"
          >
            编辑
          </el-button>
          <el-button
            type="danger"
            size="mini"
            @click="openDeleteModel(scope.row)"
          >
            删除账号
          </el-button>
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
    <!-- 修改对话框 -->
    <el-dialog :visible.sync="isEdit" width="30%">
      <div class="dialog-title-container" slot="title">修改用户</div>
      <el-form label-width="60px" size="medium" :model="userForm">
        <el-form-item label="昵称">
          <el-input v-model="userForm.nickname" style="width: 220px" />
        </el-form-item>
        <el-form-item label="角色">
          <el-radio-group v-model="roleName">
            <el-radio
              v-for="item of userRoleList"
              :key="item.roleName"
              :label="item.roleName"
            >
              {{ item.roleName }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="isEdit = false">取 消</el-button>
        <el-button type="primary" @click="editUserRole"> 确 定 </el-button>
      </div>
    </el-dialog>
    <!-- 修改对话框 -->
    <el-dialog :visible.sync="isDelete" width="30%">
      <div class="dialog-title-container" slot="title">删除账号</div>
      <span>是否删除该账号?包括该账号的所有资料!</span>
      <div slot="footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteUser"> 确 定 </el-button>
      </div>
    </el-dialog>
    <!-- 添加管理员对话框 -->
    <el-dialog :visible.sync="adminModel" width="30%">
      <div class="dialog-title-container" slot="title">添加管理员</div>
      <el-form
        label-width="100px"
        size="medium"
        ref="adminData"
        :model="adminData"
        :rules="addFormRules"
      >
        <el-form-item prop="email" label="账户邮箱">
          <el-input v-model="adminData.email" style="width: 220px" />
        </el-form-item>
        <el-form-item prop="nickName" label="昵称">
          <el-input v-model="adminData.nickName" style="width: 220px" />
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input v-model="adminData.password" style="width: 220px" />
        </el-form-item>
        <el-form-item label="角色">
          <el-radio-group v-model="adminData.role">
            <el-radio
              v-for="item of userRoleList"
              :key="item.roleName"
              :label="item.roleName"
            >
              {{ item.roleName }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="adminModel = false">取 消</el-button>
        <el-button type="primary" @click="addAdmin"> 确 定 </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import {
  listBackUsers,
  getRoleLabelList,
  updateRole,
  changeEnable,
  deleteAccount,
  addAdmin
} from "../../api/index";
import { mixin } from "@/utils";
export default {
  mixins: [mixin],
  created() {
    this.listUsers();
  },
  data: function() {
    //====自定义校验规则====
    //验证邮箱的规则
    let checkEmail = (rules, value, callback) => {
      //验证邮箱的正则表达式
      const regEmail = /^([a-zA-A0-9_-])+@([a-zA-A0-9_-])+(\.[a-zA-A0-9_-])+/;
      if (regEmail.test(value) === true) {
        //合法的邮箱
        return callback();
      } else {
        callback(new Error("请输入合法的邮箱"));
      }
    };
    return {
      loading: true,
      isEdit: false,
      isDelete: false,
      deleteAccountId: null,
      userForm: {
        id: null,
        nickname: ""
      },
      adminData: {
        email: "",
        password: "",
        nickName: "",
        role: ""
      },
      userRoleList: [],
      adminModel: false,
      roleName: "",
      userList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      // 验证规则
      addFormRules: {
        nickName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 3,
            max: 10,
            message: "昵称 3 到 10 个字符之间",
            trigger: "blur"
          }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 15,
            message: "密码的长度在 6 到 15 个字符之间",
            trigger: "blur"
          }
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          //使用自定义的邮箱的规则
          { validator: checkEmail, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    // 每页数据改变
    sizeChange(size) {
      this.size = size;
      this.listUsers();
    },
    // 改变页码
    currentChange(current) {
      this.current = current;
      this.listUsers();
    },
    // 改变锁定状态
    changeDisable(user) {
      let param = new URLSearchParams();
      param.append("enabled", user.enabled);
      changeEnable(user.id, param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
      });
    },
    // 打开修改对话框
    openEditModel(user) {
      this.roleName = "";
      this.roleName = user.role;
      this.userForm = JSON.parse(JSON.stringify(user));
      this.isEdit = true;
    },
    openDeleteModel(user) {
      this.isDelete = true;
      this.deleteAccountId = user.id;
    },
    // 删除账户
    deleteUser() {
      let param = {};
      let data = { id: this.deleteAccountId };
      param = { params: data };
      deleteAccount(param).then(res => {
        if (res.code === "200") {
          this.$message.success(res.msg);
        } else {
          this.$message.error(res.msg);
        }
        this.deleteAccountId = null;
        this.isDelete = false;
        this.listUsers();
      });
    },
    // 修改用户信息
    editUserRole() {
      this.userForm.roleName = this.roleName;
      updateRole(this.userForm).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.message
          });
          /* 如果当前页面只有一个数据并且不是第一页，则使页数返回上一页 */
          if (this.userList.length <= 1 && this.current > 1) {
            this.current--;
          }
          this.listUsers();
        } else {
          this.$notify.error({
            title: "失败",
            message: "服务器异常，请稍后再试"
          });
        }
        this.isEdit = false;
      });
    },
    addAdmin() {
      if (this.adminData.role === "") {
        this.$message.warning("必须选择角色!");
        return false;
      }
      this.$refs["adminData"].validate(valid => {
        if (valid) {
          let param = new URLSearchParams();
          param.append("email", this.adminData.email);
          param.append("password", this.adminData.password);
          param.append("nickName", this.adminData.nickName);
          param.append("role", this.adminData.role);
          addAdmin(param).then(res => {
            if (res.code === "200") {
              this.$message.success("添加成功!");
              this.adminModel = false;
              this.listUsers();
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },
    // 查询用户列表和角色选项数据
    listUsers() {
      this.loading = true;
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      listBackUsers(param).then(res => {
        this.userList = res.data.list;
        this.count = res.data.total;
        this.loading = false;
      });
      getRoleLabelList().then(res => {
        this.userRoleList = res.data;
      });
    }
  }
};
</script>
