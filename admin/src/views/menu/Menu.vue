<template>
  <el-card class="main-card">
    <!-- 标题 -->
    <div class="title">{{ this.$route.name }}</div>
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openModel(null)"
      >
        新增菜单
      </el-button>
    </div>
    <!-- 权限列表 -->
    <el-table
      v-loading="loading"
      :data="menuList"
      row-key="id"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="菜单名称" width="120" />
      <el-table-column prop="icon" align="center" label="图标" width="100">
        <template slot-scope="scope">
          <i :class="'iconfont ' + scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column
        prop="orderNum"
        align="center"
        label="排序"
        width="100"
      />
      <el-table-column prop="path" label="访问路径" />
      <el-table-column prop="component" label="组件路径" />
      <el-table-column prop="isDisable" label="禁用" align="center" width="80">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isDisable"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="updateStatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="isHidden" label="隐藏" align="center" width="80">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isHidden"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="updateStatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        align="center"
        width="150"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right: 5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="mini"
            @click="addCateLog(scope.row.id)"
            v-if="scope.row.children"
          >
            <i class="el-icon-plus" /> 新增
          </el-button>
          <el-button type="text" size="mini" @click="openModel(scope.row)">
            <i class="el-icon-edit" /> 修改
          </el-button>
          <el-popconfirm
            title="确定删除吗？如果时一级目录这样会删除所有子菜单!"
            style="margin-left: 10px"
            @confirm="deleteLink(scope.row.id)"
          >
            <el-button size="mini" type="text" slot="reference">
              <i class="el-icon-delete" /> 删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增模态框 -->
    <el-dialog :visible.sync="addMenu" width="30%" top="12vh">
      <el-form label-width="80px" size="medium" :model="menuForm">
        <el-form-item label="菜单名称">
          <el-input v-model="menuForm.name" style="width: 220px" />
        </el-form-item>
        <el-form-item label="菜单图标">
          <el-select v-model="menuForm.icon" placeholder="请选择">
            <el-option
              v-for="item in iconList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
              <span style="float: left">{{ item.label }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px"
                ><i :class="'iconfont ' + item.value"
              /></span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="组件路径">
          <el-input v-model="menuForm.component" style="width: 220px" />
        </el-form-item>
        <el-form-item label="路由地址">
          <el-input v-model="menuForm.path" style="width: 220px" />
        </el-form-item>
        <el-form-item label="显示排序">
          <el-input-number
            v-model="menuForm.orderNum"
            controls-position="right"
            :min="1"
            :max="10"
          />
        </el-form-item>
        <el-form-item label="显示状态">
          <el-radio-group v-model="menuForm.isHidden">
            <el-radio :label="0">显示</el-radio>
            <el-radio :label="1">隐藏</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addMenu = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateMenu"> 确 定 </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { getBackMenu, saveOrUpdateMenu, deleteMenu } from "../../api/index";
import iconList from "../../assets/js/icon";
export default {
  created() {
    this.listMenus();
  },
  data() {
    return {
      keywords: "",
      loading: true,
      addMenu: false,
      showIcon: false,
      isCatalog: true,
      newMenu: {},
      menuList: [],
      menuForm: {},
      icon: "",
      iconList: iconList
    };
  },
  methods: {
    //查询后台菜单列表
    listMenus() {
      getBackMenu().then(res => {
        this.menuList = res.data;
        this.loading = false;
      });
    },
    // 删除菜单
    deleteLink(id) {
      let param = {};
      let data = { id: id };
      param = { params: data };
      deleteMenu(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
          this.listMenus();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
      });
    },
    // 打开菜单对话框
    openModel(menu) {
      this.menuForm = {};
      if (menu != null) {
        this.menuForm = menu;
      }
      this.addMenu = true;
    },
    // 新增菜单时表单数据
    addCateLog(id) {
      this.menuForm = {};
      this.menuForm.parentId = id;
      this.addMenu = true;
    },
    // 保存或新增菜单
    saveOrUpdateMenu() {
      saveOrUpdateMenu(this.menuForm).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
          this.listMenus();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
      });
      this.addMenu = false;
    },
    // 更改菜单隐藏或禁用状态
    updateStatus(param) {
      saveOrUpdateMenu(param).then(res => {
        if (res.code === "200") {
          this.$notify.success({
            title: "成功",
            message: res.msg
          });
          this.listMenus();
        } else {
          this.$notify.error({
            title: "失败",
            message: res.msg
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.menu-container {
  position: absolute;
  width: 90%;
  background: #fff;
  border-radius: 4px;
  border: 1px solid #e6ebf5;
  padding: 12px;
  z-index: 2000;
  color: #606266;
  text-align: justify;
  font-size: 14px;
  -webkit-box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
  word-break: break-all;
  overflow-y: auto;
  height: 200px;
}
.menu-container div {
  cursor: pointer;
}
</style>
