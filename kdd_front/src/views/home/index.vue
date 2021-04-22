<template>
  <div class='container'>
    <el-card>
       <span style='width: 20%;text-align: center;'>剩余条数: {{balanceNum}}</span>
       <el-button
            type='warning'
            icon='el-icon-plus'
            size='small'
            style='marginBottom:10px;'
            @click='openChargeTips'
        >充 值</el-button>
    </el-card>
    <el-card>
       <span style='width: 20%;text-align: center;'>当前身份: {{getRoleStr}}</span>
       <el-button
            type='danger'
            size='small'
            style='marginBottom:10px;'
            @click='showEditDialog'
        >资料上报</el-button>
       <el-button
            type='primary'
            size='small'
            style='marginBottom:10px;'
            @click='showMsgDialog'
        >查看资料</el-button>
    </el-card>
    <el-card>
       <span style='width: 20%;text-align: center;'>秘钥信息: ******</span>
       <el-button
            type='primary'
            size='small'
            style='marginBottom:10px;'
            @click='openSecretMsgs'
        >查看秘钥</el-button>
    </el-card>

    <!-- 各弹窗组件 -->
    
    <!-- 查看信息对话框 -->
    <el-dialog
      title="进件信息"
      :visible.sync="showMsgDialogVisible"
      width="60%"
      :before-close="showMsgDialogClose"
    >
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">当前身份</span>
        <span style="width: 70%;text-align: left;">{{getRoleStr}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">法人姓名</span>
        <span style="width: 70%;text-align: left;">{{checkParam.name}}</span>
      </div>
       <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">证件类型</span>
        <span style="width: 70%;text-align: left;">{{checkParam.cardType == 1 ? '身份证' : '其他'}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">证件号</span>
        <span style="width: 70%;text-align: left;">{{checkParam.cardNo}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">联系电话</span>
        <span style="width: 70%;text-align: left;">{{checkParam.phone}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">联系Email</span>
        <span style="width: 70%;text-align: left;">{{checkParam.email}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">用户职位</span>
        <span style="width: 70%;text-align: left;">{{checkParam.job}}</span>
      </div>

      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">公司名称</span>
        <span style="width: 70%;text-align: left;">{{checkParam.organization}}</span>
      </div>
       <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">公司所在城市</span>
        <span style="width: 70%;text-align: left;">{{checkParam.city}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">公司详细地址</span>
        <span style="width: 70%;text-align: left;">{{checkParam.address}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">身份证正面</span>
        <img :src="checkParam.idcardFront"  min-width="70" height="70" />
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">身份证背面</span>
        <img :src="checkParam.idcardBack"  min-width="70" height="70" />
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">营业执照</span>
        <img :src="checkParam.businessLicense"  min-width="70" height="70" />
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">修改时间</span>
        <span style="width: 70%;text-align: left;">{{checkParam.updateTime}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">备注信息</span>
        <span style="width: 70%;text-align: left;">{{checkParam.description}}</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="showMsgDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="showMsgDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 进件审核对话框 -->
    <el-dialog
      title="进件审核"
      :visible.sync="showEditDialogVisible"
      width="60%"
      :before-close="showEditDialogClose"
    >
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">当前身份</span>
        <span style="width: 70%;text-align: left;">{{getRoleStr}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">法人姓名</span>
        <el-input style="width: 70%;" v-model="checkParam.name" size="mini" placeholder="请输入内容"></el-input>
      </div>
       <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">证件类型</span>
        <span style="width: 70%;text-align: left;">{{checkParam.cardType == 1 ? '身份证' : '其他'}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">证件号</span>
        <el-input style="width: 70%;" v-model="checkParam.cardNo" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">手机号</span>
        <span style="width: 70%;text-align: left;">{{checkParam.phone}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">Email</span>
        <el-input style="width: 70%;" v-model="checkParam.email" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">用户职位</span>
        <el-input style="width: 70%;" v-model="checkParam.job" size="mini" placeholder="请输入内容"></el-input>
      </div>

      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">公司名称</span>
        <el-input style="width: 70%;" v-model="checkParam.organization" size="mini" placeholder="请输入内容"></el-input>
      </div>
       <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">公司所在城市</span>
        <el-input style="width: 70%;" v-model="checkParam.city" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">公司详细地址</span>
        <el-input style="width: 70%;" v-model="checkParam.address" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">身份证正面</span>
        <el-upload
          class="upload-demo"
          accept=".jpg,.jpeg,.png"
          action="#"
          :show-file-list="false"
          :before-upload="uploadIdcardFront"
          style="margin-right:20px;"
        >
          <el-button size="small" type="primary" style="margin-top:4px;">上传正面</el-button>
        </el-upload>
        <img :src="checkParam.idcardFront"  min-width="70" height="70" />
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">身份证背面</span>
        <el-upload
          class="upload-demo"
          accept=".jpg,.jpeg,.png"
          action="#"
          :show-file-list="false"
          :before-upload="uploadIdcardBack"
          style="margin-right:20px;"
        >
          <el-button size="small" type="primary" style="margin-top:4px;">上传背面</el-button>
        </el-upload>
        <img :src="checkParam.idcardBack"  min-width="70" height="70" />
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">营业执照</span>
        <el-upload
          class="upload-demo"
          accept=".jpg,.jpeg,.png"
          action="#"
          :show-file-list="false"
          :before-upload="uploadBusinessLicense"
          style="margin-right:20px;"
        >
          <el-button size="small" type="primary" style="margin-top:4px;">上传执照</el-button>
        </el-upload>
        <img :src="checkParam.businessLicense"  min-width="70" height="70" />
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">修改时间</span>
        <span style="width: 70%;text-align: left;">{{checkParam.updateTime}}</span>
      </div>

      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">审核批注</span>
        <span style="width: 70%;text-align: left;">{{checkParam.description}}</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="showEditDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmBeforeUpdate">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { getDetail, uploadFile, getSecrect } from "@/api/certificate";
import { reqEditUser } from "@/api/user";
import { Message } from 'element-ui'
import axios from 'axios'
axios.defaults.withCredentials = true
export default {
  name: '',
  components: {
  },
  data() {
    return {
      showMsgDialogVisible: false, //进件审核对话框
      showEditDialogVisible: false, //进件审核对话框
      roleId: 2,
      balanceNum: 0,  //剩余请求条数
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
      },
      customerCode: "",
      secretKey: ""
    }
  },
  computed: {
    getRoleStr: function () {
      if (this.roleId == 1) {
        return '超级管理员'
      } else if (this.roleId == 2) {
        return '未认证用户'
      } else if (this.roleId == 3) {
        return '已认证用户'
      } else if (this.roleId == 4) {
        return '进件审核员'
      } else {
        return '无法识别'
      }
    }
  },
  mounted() {
    this.getMine()
  },
  methods: {
    uploadIdcardFront(file) {
      this.reqUploadFile(file, 1)
    },
    uploadIdcardBack(file) {
      this.reqUploadFile(file, 2)
    },
    uploadBusinessLicense(file) {
      this.reqUploadFile(file, 3)
    },
    // 上传文件
    reqUploadFile(file, fileType) {
      const isLarge = file.size / 1024 > 20480
      if (isLarge) {
        this.$message({
          type: 'error',
          message: '文件大小超过20M！'
        })
        return false
      }
      const loading = this.$loading({
        lock: true,
        text: '拼命上传中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      const formData = new FormData()
      formData.append('uploadFile', file)
      formData.append('fileKind', fileType)
      var that = this;
      uploadFile(formData).then(res => {
        loading.close()
        if (res.data.resultCode === 200) {
          console.log(res.data)
          if (fileType == 1) {
            that.checkParam.idcardFront = res.data.data.path
          } else if (fileType == 2) {
            that.checkParam.idcardBack = res.data.data.path
          } else {
            that.checkParam.businessLicense = res.data.data.path
          }
          Message({
            type: 'success',
            message: res.data.data
          })
        } else {
          Message({
            type: 'error',
            message: res.data.data || '上传失败'
          })
        }
      })
      return false
    },
    openChargeTips() {
      this.$alert('<div style="text-align: left;">' + 
                '<p>价  格：0.01元/条</p>' + 
                '<p>充值方式：转账到支付宝13818040096（郭凯）并备注好【注册手机号】_【姓名】_【kdd充值】，1日内会有专人处理充值</p>' + 
                '</div>',

       '充值说明', {
        confirmButtonText: '确定',
        type: 'info',
        center: true,
        dangerouslyUseHTMLString: true
      })
        .then(() => {})
        .catch(() => {})
    },
    openSecretMsgs() {
      var customerCode = this.customerCode
      var secretKey = this.secretKey
      if (customerCode == '' || secretKey == '') {
        var that = this
        // 请求获取秘钥
        getSecrect().then(res => {
          if (res.data.resultCode === 200) {
            console.log(res.data)
            customerCode = res.data.data.customerCode
            secretKey = res.data.data.secretKey
            that.customerCode = customerCode
            that.secretKey = secretKey
            // Message({
            //   type: 'success',
            //   message: res.data.data
            // })
          } else {
            Message({
              type: 'error',
              message: res.data.data || '获取秘钥失败'
            })
          }
        }).then(() => {
          this.$alert('<div style="text-align: left;">' + 
                  '<p>customerCode: '+customerCode+'</p>' + 
                  '<p>secretKey: '+secretKey+'</p>' + 
                  '</div>', 
            '秘钥信息', {
            confirmButtonText: '确定',
            type: 'info',
            center: true,
            dangerouslyUseHTMLString: true
          })
            .then(() => {})
            .catch(() => {})
        })
      } else {
        this.$alert('<div style="text-align: left;">' + 
                '<p>customerCode: '+customerCode+'</p>' + 
                '<p>secretKey: '+secretKey+'</p>' + 
                '</div>', 
          '秘钥信息', {
          confirmButtonText: '确定',
          type: 'info',
          center: true,
          dangerouslyUseHTMLString: true
        })
          .then(() => {})
          .catch(() => {})
      }
    },
    confirmBeforeUpdate() {
      var that = this
      this.$alert('<span>资料修改后将需要重新审核，审核通过前无法使用秘钥调用API，确定要修改？</span>', '重要提醒', {
          confirmButtonText: '确定修改',
          type: 'warn',
          center: true,
          dangerouslyUseHTMLString: true
        })
        .then(() => {
          console.log('确认')
          that.updateUser()
        }).catch((e) => {
          console.error(e)
          console.log('取消')
        })
    },
    getMine() {
      var that = this;
      getDetail({
        accountId: 0   //无论传什么，都会强制使用当前登录账号
      }).then(
        function(res) {
          // success
          // console.log('getDetail ',res.data.data)
          var detailResp = res.data.data
          // 按文件类型获取图片url
          var idcardFront = '';
          var idcardBack = '';
          var businessLicense = '';
          for (let i = 0; i < detailResp.certificateFiles.length; i++) {
            const certificateFile = detailResp.certificateFiles[i];
            if (certificateFile.fileKind == 1) {
              // 身份证正面
              idcardFront = certificateFile.path
            } else if (certificateFile.fileKind == 2) {
              // 身份证反面
              idcardBack = certificateFile.path
            } else {
              // 营业执照
              businessLicense = certificateFile.path
            }
          }
          that.roleId = detailResp.accountInfo.roleId
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
        },
        function(e) {
          // failure
          console.error(e);
          Message({
            message: '初始化信息异常',
            type: 'error',
            duration: 1000
          })
        }
      );
    },
    updateUser() {
      var that = this
      // 清除无法转换的格式
      that.checkParam.updateTime = undefined
      reqEditUser(that.checkParam).then(
        function(res) {
          // success
          Message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          })
          that.getMine();
          that.showEditDialogVisible = false;
        },
        function(e) {
          // failure
          console.error(e);
          Message({
            message: '修改异常',
            type: 'error',
            duration: 1000
          })
        }
      );
    },
    // 查看详情
    showMsgDialog() {
      //显示资料详情对话框
      this.showMsgDialogVisible = true;
    },
    showMsgDialogClose() {
      this.showMsgDialogVisible = false;
    },
    // 查看详情
    showEditDialog() {
      //显示资料详情对话框
      this.showEditDialogVisible = true;
    },
    showEditDialogClose() {
      this.showEditDialogVisible = false;
    },
  }
}
</script>

<style lang='scss' scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.container {
  padding: 16px;
}
.pagination-wrapper {
  width: 100%;
  text-align: right;
  margin-top: 20px;
}
</style>

