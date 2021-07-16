<template>
  <div class="container">
    <el-button
      type="primary"
      icon="el-icon-plus"
      size="small"
      style="marginBottom:10px;"
      @click="()=>{addModalVisible=true}"
    >新增</el-button>

    <el-card>
      <el-table :data="roleList" border style="width: 100%;">
        <el-table-column prop="name" label="角色名" />
        <el-table-column prop="rights" label="权限">
          <template slot-scope="scope">
            <tooltip :rights="scope.row.rights" />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" :formatter="timestampToDateStr" label="创建时间" />
        <el-table-column prop="message" label="角色信息" />

        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="editUser(scope.row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="delUser(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div v-show="total>10" class="pagination-wrapper">
        <el-pagination
          background
          :page-size="10"
          layout="total,prev, pager, next"
          :total="total"
          @current-change="changePage"
        />
      </div>
    </el-card>
    <add-user :add-modal-visible="addModalVisible" @cancel="cancel" @refresh="init(1)" />
    <edit-user
      :add-modal-visible="editModalVisible"
      :form="form"
      @cancel="cancel"
      @refresh="init(1)"
    />
  </div>
</template>

<script>
import { getRoleList, reqDelRole } from '@/api/role'
import { Message } from 'element-ui'
import { RIGHTS } from '@/utils/role'
import Tooltip from './components/tooltip'
import AddUser from './components/addUser'
import EditUser from './components/editUser'
import moment from "moment"

export default {
  name: '',
  components: {
    AddUser,
    EditUser,
    Tooltip
  },
  data() {
    return {
      addModalVisible: false,
      editModalVisible: false,
      roleList: [],
      form: {},
      total: 0
    }
  },
  mounted() {
    this.init(1)
  },
  methods: {
    timestampToDateStr(rowdata) {
      if (rowdata === undefined || rowdata.createTime === undefined || rowdata.createTime === null) {
        return "";
      }
      return moment(rowdata.createTime).format("yyyy/MM/DD hh:mm:ss");
    },
    init(page) {
      getRoleList({ page, rows: 1000 }).then(res => {
        const data = res.data.data
        this.total = res.data.count
        data.map(item => {
          item.rights = this.filterRights(item.rights)
        })

        this.roleList = data
      })
    },
    filterRights(data) {
      const ret = []
      data.split('|').map(item => {
        ret.push(RIGHTS[item])
      })
      return ret
    },
    cancel() {
      this.addModalVisible = false
      this.editModalVisible = false
    },
    editUser(row) {
      this.form = {
        id: row.id,
        name: row.name,
        rights: row.rights,
        message: row.message
      }
      this.editModalVisible = true
    },
    delUser(info) {
      reqDelRole({ id: info.id }).then(res => {
        this.init(1)
        Message.success('删除成功！')
      })
    },
    changePage(e) {
      this.init(e)
    }
  }
}
</script>
<style scoped>
.container {
  padding: 16px;
}
.pagination-wrapper {
  width: 100%;
  text-align: right;
  margin-top: 20px;
}
</style>
