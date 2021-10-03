<template>
  <div class='container'>
    <el-card>
      <el-button
        type='primary'
        icon='el-icon-plus'
        size='small'
        style='marginBottom:10px;'
        @click='()=>{addModalVisible=true}'
      >新增</el-button>
      <el-table :data='userList' border style='width: 100%;'>
        <el-table-column prop='phone' label='手机号' />
        <el-table-column prop='name' label='名称' />
        <el-table-column prop='roleId' label='角色'>
          <template slot-scope='scope'>
            <p v-if='scope.row.roleId == 1'>超级管理员</p>
            <p v-if='scope.row.roleId == 2'>未认证用户</p>
            <p v-if='scope.row.roleId == 3'>已认证用户</p>
            <p v-if='scope.row.roleId == 4'>进件审核员</p>
          </template>
        </el-table-column>

        <el-table-column prop='name' label='是否启用'>
          <template slot-scope='scope'>
            <p>{{ scope.row.disabled?'禁用':'启用' }}</p>
          </template>
        </el-table-column>
        <el-table-column fixed='right' label='操作' align='center'>
          <template slot-scope='scope'>
            <div>
              <el-button type='info' size='mini' @click='viewDetail(scope.row)'>查看详情</el-button>
              <el-button type='primary' size='mini' @click='editUser(scope.row)'>编辑</el-button>
            </div>
            <div>
              <el-button type='warning' size='mini' @click='charge(scope.row)'>充值</el-button>
              <el-button type='danger' size='mini' @click='editPassword(scope.row)'>重置密码</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div v-show='userList.length>0' class='pagination-wrapper'>
        <el-pagination
          background
          :page-size='10'
          layout='total,prev, pager, next'
          :total='total'
          @current-change='changePage'
        />
      </div>
    </el-card>
    <add-user
      :add-modal-visible='addModalVisible'
      :role-list='roleList'
      @cancel='cancel'
      @refresh='init(1)'
    />
    <edit-user
      :edit-modal-visible='editModalVisible'
      :role-list='roleList'
      :form='form'
      @cancel='cancel'
      @refresh='init(1)'
    />
    <edit-password
      :edit-pw-modal-visible='editPwModalVisible'
      :role-list='roleList'
      :form='form'
      @cancel='cancel'
      @refresh='init(1)'
    />

    <detail-dialog
      :detail-dialog-visible='detailDialogVisible'
      :check-param='checkParam'
      @cancel='cancel'
      @refresh='init(1)'
    />
    <charge-dialog
      :charge-modal-visible='chargeModalVisible'
      :form='form'
      @cancel='cancel'
      @refresh='init(1)'
    />
  </div>
</template>

<script>
import { getDetail } from "@/api/certificate"
import { getUserList, reqDelUser } from '@/api/user'
import { getRoleList } from '@/api/role'
import { Message } from 'element-ui'
import AddUser from './components/addUser'
import EditUser from './components/editUser'
import EditPassword from './components/editPassword'
import ChargeDialog from './components/chargeDialog'
import DetailDialog from './components/detailDialog'
export default {
  name: '',
  components: {
    AddUser,
    EditUser,
    EditPassword,
    DetailDialog,
    ChargeDialog
  },
  data() {
    return {
      detailDialogVisible: false, //进件审核对话框
      addModalVisible: false,
      editModalVisible: false,
      editPwModalVisible: false,
      chargeModalVisible: false,
      userList: [],
      form: {},
      total: 0,
      roleList: [],
      checkParam: {
        roleId: 2,
        name: "",
        cardType: 1,
        cardNo: "",
        job: "",
        phone: "",
        organization: "",
        city: "",
        email: "",
        accountId: "",
        address: "",
        isPass: 1,
        description: "",
        updateTime: "",
        idcardFront: "",
        idcardBack: "",
        businessLicense: ""
      }
    }
  },
  mounted() {
    this.init(1);
    getRoleList({ page: 1, rows: 1000 }).then(res => {
      this.roleList = res.data.data
    });
  },
  methods: {
    init(page) {
      getUserList({ page, rows: 10 }).then(res => {
        this.userList = res.data.data
        this.total = res.data.count
      })
    },
    charge(row) {
      this.form = {
        accountId: row.id,
        orderType: 1
      }
      this.chargeModalVisible = true
    },
    cancel() {
      this.addModalVisible = false
      this.editModalVisible = false
      this.editPwModalVisible = false
      this.detailDialogVisible = false
      this.chargeModalVisible = false
    },
    editUser(row) {
      let disable = '0'
      if (row.disable) disable = '1'
      this.form = {
        id: row.id,
        accountId: row.id,
        name: row.name,
        phone: row.phone,
        disable,
        roleId: row.roleId.toString(),
        remark: row.remark
      }
      this.editModalVisible = true
    },
    editPassword(row) {
      let disabled = '0'
      if (row.disabled) disabled = '1'
      this.form = {
        id: row.id,
        name: row.name,
        phone: row.phone,
        password: '',
        disabled,
        roleId: row.roleId.toString(),
        remark: row.remark
      }
      this.editPwModalVisible = true
    },
    delUser(info) {
      reqDelUser({ id: info.id }).then(res => {
        this.init(1)
        Message.success('删除成功!')
      })
    },
    changePage(e) {
      this.init(e)
    },
    // 查看详情
    viewDetail(row) {
      // console.log(index,row)
      var that = this
      getDetail({
        accountId: row.id
      }).then(
        function(res) {
          // success
          console.log('getDetail ', res.data.data)
          var detailResp = res.data.data
          // 按文件类型获取图片url
          var idcardFront = ''
          var idcardBack = ''
          var businessLicense = ''
          for (let i = 0; i < detailResp.certificateFiles.length; i++) {
            const certificateFile = detailResp.certificateFiles[i]
            if (certificateFile.fileKind === 1) {
              // 身份证正面
              idcardFront = certificateFile.path
            } else if (certificateFile.fileKind === 2) {
              // 身份证反面
              idcardBack = certificateFile.path
            } else {
              // 营业执照
              businessLicense = certificateFile.path
            }
          }
          that.checkParam.roleId = detailResp.accountInfo.roleId
          that.checkParam.name = detailResp.accountInfo.name
          that.checkParam.cardNo = detailResp.userInfo.cardNo
          that.checkParam.job = detailResp.userInfo.job
          that.checkParam.phone = detailResp.accountInfo.phone
          that.checkParam.organization = detailResp.userInfo.organization
          that.checkParam.city = detailResp.userInfo.city
          that.checkParam.email = detailResp.userInfo.email
          that.checkParam.accountId = detailResp.userInfo.accountId
          that.checkParam.address = detailResp.userInfo.address
          that.checkParam.isPass = 0
          that.checkParam.description = detailResp.userInfo.description
          that.checkParam.updateTime = detailResp.userInfo.updateTime
          that.checkParam.idcardFront = idcardFront
          that.checkParam.idcardBack = idcardBack
          that.checkParam.businessLicense = businessLicense
          // 显示修改对话框
          that.detailDialogVisible = true
        },
        function(e) {
          // failure
          console.error(e)
          Message({
            message: '初始化信息异常',
            type: 'error',
            duration: 1000
          })
        }
      )
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
