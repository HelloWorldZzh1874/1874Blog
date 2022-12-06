<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openMenuModel(null)"
      >
        新增
      </el-button>
      <el-button
        type="danger"
        size="small"
        icon="el-icon-deleteItem"
        :disabled="this.roleIdList.length == 0"
        @click="isDelete = true"
      >
        批量删除
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left: auto">
        <el-input
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入角色名"
          style="width: 200px"
          @keyup.enter.native="listRoles"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left: 1rem"
          @click="listRoles"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="roleList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <el-table-column prop="roleName" label="角色名" align="center" />
      <el-table-column prop="roleLabel" label="权限标签" align="center">
        <template slot-scope="scope">
          <el-tag>
            {{ scope.row.roleLabel }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="isDisable" label="禁用" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isDisable"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="changeDisable(scope.row)"
          />
        </template>
      </el-table-column> -->
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="200"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="openMenuModel(scope.row)">
            <i class="el-icon-edit" /> 修改
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="openResourceModel(scope.row)"
          >
            <i class="el-icon-folder-checked" /> 资源权限
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left: 10px"
            @onConfirm="deleteRoles(scope.row.id)"
          >
            <el-button size="mini" type="text" slot="reference">
              <i class="el-icon-delete" /> 删除
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
    <!-- 菜单对话框 -->
    <el-dialog :visible.sync="roleMenu" width="30%">
      <div class="dialog-title-container" slot="title" ref="roleTitle" />
      <el-form label-width="80px" size="medium" :model="roleForm">
        <el-form-item label="角色名">
          <el-input v-model="roleForm.roleName" style="width: 250px" />
        </el-form-item>
        <el-form-item label="权限标签">
          <el-input v-model="roleForm.roleLabel" style="width: 250px" />
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-tree
            :data="menuList"
            :default-checked-keys="roleForm.menuIdList"
            show-checkbox
            node-key="id"
            ref="menuTree"
          />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="roleMenu = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateRoleMenu">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 资源对话框 -->
    <el-dialog :visible.sync="roleResource" width="30%" top="9vh">
      <div class="dialog-title-container" slot="title">修改资源权限</div>
      <el-form label-width="80px" size="medium" :model="roleForm">
        <el-form-item label="角色名">
          <el-input v-model="roleForm.roleName" style="width: 250px" disabled />
        </el-form-item>
        <el-form-item label="权限标签">
          <el-input
            v-model="roleForm.roleLabel"
            style="width: 250px"
            disabled
          />
        </el-form-item>
        <el-form-item label="资源权限">
          <el-tree
            :data="resourceList"
            :default-checked-keys="roleForm.resourceIdList"
            show-checkbox
            node-key="id"
            ref="resourceTree"
          />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="roleResource = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateRoleResource">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="isDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color: #ff9900" />提示
      </div>
      <div style="font-size: 1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteRoles(null)"> 确 定 </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import {
  getRoleList,
  listResourceOption,
  getRoleMenuOption,
  deleteRole,
  saveOrUpdateRole
} from "../../api/index";
export default {
  created() {
    this.listRoles();
  },
  data: function() {
    return {
      loading: true,
      isDelete: false,
      roleList: [],
      roleIdList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      roleMenu: false,
      roleResource: false,
      resourceList: [],
      menuList: [],
      roleForm: {
        roleName: "",
        roleLabel: "",
        resourceIdList: [],
        menuIdList: []
      }
    };
  },
  methods: {
    // 每页数据量
    sizeChange(size) {
      this.size = size;
      this.listRoles();
    },
    // 当前页
    currentChange(current) {
      this.current = current;
      this.listRoles();
    },
    // 多选项该改变
    selectionChange(roleList) {
      this.roleIdList = [];
      roleList.forEach(item => {
        this.roleIdList.push(item.id);
      });
    },
    // 查询角色数据
    listRoles() {
      this.resourceList = null;
      this.menuList = null;
      this.roleList = null;
      let param = new URLSearchParams();
      param.append("current", this.current);
      param.append("size", this.size);
      param.append("keywords", this.keywords === null ? "" : this.keywords);
      getRoleList(param).then(res => {
        this.roleList = res.data.list;
        this.count = res.data.total;
        this.loading = false;
      });
      // 获取该角色资源数据
      listResourceOption().then(res => {
        this.resourceList = res.data;
      });
      // 获取该角色的菜单数据
      getRoleMenuOption().then(res => {
        this.menuList = res.data;
      });
    },
    // 删除角色
    deleteRoles(id) {
      let param = {};
      if (id == null) {
        param = { data: this.roleIdList };
      } else {
        param = { data: [id] };
      }
      deleteRole(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: "删除成功"
          });
          /* 如果当前页面只有一个数据并且不是第一页，则使页数返回上一页 */
          if (this.roleList.length <= 1 && this.current > 1) {
            this.current--;
          }
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
        this.isDelete = false;
      });
    },
    // 打开菜单资源修改对话框
    openMenuModel(role) {
      this.$nextTick(function() {
        this.$refs.menuTree.setCheckedKeys([]);
      });
      this.$refs.roleTitle.innerHTML = role ? "修改角色" : "新增角色";
      if (role != null) {
        this.roleForm = JSON.parse(JSON.stringify(role));
        this.roleForm.type = 1;
      } else {
        this.roleForm = {
          roleName: "",
          roleLabel: "",
          resourceIdList: [],
          menuIdList: []
        };
      }
      this.roleMenu = true;
    },
    // 打开资源修改对话框
    openResourceModel(role) {
      this.$nextTick(function() {
        this.$refs.resourceTree.setCheckedKeys([]);
      });
      this.roleForm = JSON.parse(JSON.stringify(role));
      this.roleForm.type = 2;
      this.roleResource = true;
    },
    // 保存或更新角色资源信息
    saveOrUpdateRoleResource() {
      this.roleForm.menuIdList = null;
      this.roleForm.resourceIdList = this.$refs.resourceTree.getCheckedKeys();
      saveOrUpdateRole(this.roleForm).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
        this.roleResource = false;
      });
    },
    // 保存或更新角色菜单
    saveOrUpdateRoleMenu() {
      if (this.roleForm.roleName.trim() === "") {
        this.$message.error("角色名不能为空");
        return false;
      }
      if (this.roleForm.roleLabel.trim() === "") {
        this.$message.error("权限标签不能为空");
        return false;
      }
      this.roleForm.resourceIdList = null;
      this.roleForm.menuIdList = this.$refs.menuTree.getCheckedKeys();
      saveOrUpdateRole(this.roleForm).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
        this.roleMenu = false;
      });
    }
  }
};
</script>
