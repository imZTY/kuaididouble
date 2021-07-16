<template>
  <div style="padding:16px">
    <el-card style="margin-bottom: 16px">
      <div slot="header">
        <span>待审核列表</span>
        <el-button
          type="info"
          size="small"
          @click="openTips"
          style="display: inline-block;margin:-6px 20px;float:right"
        >使用说明</el-button>
      </div>
      <div style="position: relative;margin-bottom: 12px">
        <el-pagination
          @size-change="certificateSizeChange"
          @current-change="certificateCurrentChange"
          :page-size="15"
          layout="total, prev, pager, next"
          :total="certificateTotal"
          style="display: inline-block;position: relative;right: 0;"
          :current-page.sync="certificatePage"
        ></el-pagination>
      </div>
      <el-table :data="certificateData" border style="text-align: center;">
        <el-table-column prop="accountInfo.phone" label="手机号" align="center" />
        <el-table-column prop="accountInfo.name" label="用户名称" align="center" />
        <el-table-column prop="userInfo.organization" label="公司组织" align="center" />
        <el-table-column prop="userInfo.email" label="邮箱地址" align="center" />
        <el-table-column prop="userInfo.updateTime" :formatter="timestampToDateStr" label="修改时间" align="center" />
        <el-table-column prop="userInfo.description" label="备注" align="center" />
        <el-table-column label="操作" style="text-align: center;" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="showCheckDialog(scope.$index, scope.row)"
            >资料审核</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 审核弹窗 -->
    <check-dialog
      :check-dialog-visible='checkDialogVisible'
      :check-parma='checkParma'
      @cancel='cancel'
      @refresh='init(1)'
    />
  </div>
</template>
<script>
import { pageListUnfinish } from "@/api/certificate"
import CheckDialog from './components/checkDialog'
import moment from "moment"

export default {
  components: {
    CheckDialog
  },
  data() {
    return {
      checkDialogVisible: false, //进件审核对话框
      certificateTotal: 0, //需认证的总数
      certificatePage: 1,
      checkParma: {
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
      certificateData: [
        //模拟接口数据
      ]
    };
  },
  mounted() {
    this.getUnfinishPage(1)
  },
  methods: {
    timestampToDateStr(rowdata) {
      if (rowdata === undefined || rowdata.userInfo.updateTime === undefined || rowdata.userInfo.updateTime === null) {
        return "";
      }
      return moment(rowdata.userInfo.updateTime).format("yyyy/MM/DD hh:mm:ss");
    },
    getUnfinishPage(p) {
      var that = this
      pageListUnfinish({
        page: p,
        rows: 15
      }).then(
        function(res) {
          // success
          // console.log('pageListUnfinish',res.data.data)
          that.certificateTotal = res.data.count
          that.certificateData = res.data.data
        },
        function(e) {
          // failure
          console.error(e)
        }
      );
    },
    // 查看详情
    showCheckDialog(index, row) {
      //显示修改对话框
      // console.log(index,row)
      // 按文件类型获取图片url
      var idcardFront = ''
      var idcardBack = ''
      var businessLicense = ''
      for (let i = 0; i < row.certificateFiles.length; i++) {
        const certificateFile = row.certificateFiles[i];
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
      this.checkParma.roleId = row.accountInfo.roleId
      this.checkParma.name = row.accountInfo.name
      this.checkParma.cardNo = row.userInfo.cardNo
      this.checkParma.job = row.userInfo.job
      this.checkParma.phone = row.accountInfo.phone
      this.checkParma.organization = row.userInfo.organization
      this.checkParma.city = row.userInfo.city
      this.checkParma.email = row.userInfo.email
      this.checkParma.accountId = row.userInfo.accountId
      this.checkParma.address = row.userInfo.address
      this.checkParma.isPass = 0
      this.checkParma.description = row.userInfo.description
      this.checkParma.updateTime = row.userInfo.updateTime
      this.checkParma.idcardFront = idcardFront
      this.checkParma.idcardBack = idcardBack
      this.checkParma.businessLicense = businessLicense
      this.checkDialogVisible = true
    },
    cancel() {
      this.checkDialogVisible = false
      this.certificateCurrentChange()
    },
    certificateSizeChange() {
      //接口分页每页显示条数变化
      console.log("certificateSizeChange")
    },
    certificateCurrentChange() {
      //接口选中变化
      this.getUnfinishPage(this.certificatePage)
    },
    recordSizeChange() {
      //请求分页每页显示条数变化
      console.log("recordSizeChange")
    },
    openTips() {
      this.$alert("<span>使用说明文字</span>", "使用说明", {
        confirmButtonText: "确定",
        type: "info",
        center: true,
        dangerouslyUseHTMLString: true
      })
        .then(() => {})
        .catch(() => {})
    }
  }
};
</script>
<style>
</style>
