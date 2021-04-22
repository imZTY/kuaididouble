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
          @size-change="interfaceSizeChange"
          @current-change="interfaceCurrentChange"
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
        <el-table-column prop="userInfo.updateTime" label="修改时间" align="center" />
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

    <!-- 进件审核对话框 -->
    <el-dialog
      title="进件审核"
      :visible.sync="checkDialogVisible"
      width="60%"
      :before-close="checkDialogClose"
    >
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">法人姓名</span>
        <span style="width: 70%;text-align: left;">{{checkParma.name}}</span>
      </div>
       <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">证件类型</span>
        <span style="width: 70%;text-align: left;">{{checkParma.cardType == 1 ? '身份证' : '其他'}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">证件号</span>
        <span style="width: 70%;text-align: left;">{{checkParma.cardNo}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">联系电话</span>
        <span style="width: 70%;text-align: left;">{{checkParma.phone}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">联系Email</span>
        <span style="width: 70%;text-align: left;">{{checkParma.email}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">用户职位</span>
        <span style="width: 70%;text-align: left;">{{checkParma.job}}</span>
      </div>

      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">公司名称</span>
        <span style="width: 70%;text-align: left;">{{checkParma.organization}}</span>
      </div>
       <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">公司所在城市</span>
        <span style="width: 70%;text-align: left;">{{checkParma.city}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">公司详细地址</span>
        <span style="width: 70%;text-align: left;">{{checkParma.address}}</span>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">身份证正面</span>
        <img :src="checkParma.idcardFront"  min-width="70" height="70" />
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">身份证背面</span>
        <img :src="checkParma.idcardBack"  min-width="70" height="70" />
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">营业执照</span>
        <img :src="checkParma.businessLicense"  min-width="70" height="70" />
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">修改时间</span>
        <span style="width: 70%;text-align: left;">{{checkParma.updateTime}}</span>
      </div>

      <div style="display: flex;align-items: center;margin: 16px;height: 28px">
        <span style="width: 30%;text-align: center;">是否通过</span>
        <el-radio-group v-model="checkParma.isPass" style="width: 70%">
          <el-radio :label="1">通过</el-radio>
          <el-radio :label="0">不通过</el-radio>
        </el-radio-group>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">备注信息</span>
        <el-input style="width: 70%;" v-model="checkParma.description" size="mini" placeholder="请输入内容"></el-input>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="checkDialogVisible = false">取 消</el-button>
        <el-button type="primary" :disabled="checkParma.roleId == 3" @click="checkInterface">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import {
  pageListUnfinish,
  setRole
} from "@/api/certificate";
import { Message } from 'element-ui';

export default {
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
    this.getUnfinishPage(1);
  },
  methods: {
    getUnfinishPage(p) {
      var that = this;
      pageListUnfinish({
        page: p,
        rows: 15
      }).then(
        function(res) {
          // success
          console.log('pageListUnfinish',res.data.data)
          that.certificateTotal = res.data.count;
          that.certificateData = res.data.data;
        },
        function(e) {
          // failure
          console.log(e);
        }
      );
    },
    // 审批通过
    checkInterface() {
      //编辑接口
      var data = this.checkParma;
      var that = this;
      setRole({
        accountId: data.accountId, //设备id
        isPass: data.isPass == 1,
        description: data.description
      }).then(
        function(res) {
          // success
          Message({
            message: '处理成功',
            type: 'success',
            duration: 1000
          })
          that.getUnfinishPage(that.certificatePage);
          that.checkDialogVisible = false;
        },
        function(e) {
          // failure
          console.error(e);
          Message({
            message: '处理异常',
            type: 'error',
            duration: 1000
          })
        }
      );
    },
    // 查看详情
    showCheckDialog(index, row) {
      //显示修改对话框
      // console.log(index,row)
      this.checkDialogVisible = true;
      // 按文件类型获取图片url
      var idcardFront = '';
      var idcardBack = '';
      var businessLicense = '';
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
    },
    checkDialogClose() {
      this.checkDialogVisible = false;
    },
    interfaceSizeChange() {
      //接口分页每页显示条数变化
      console.log("interfaceSizeChange");
    },
    interfaceCurrentChange() {
      //接口选中变化
      this.getUnfinishPage(this.certificatePage);
    },
    recordSizeChange() {
      //请求分页每页显示条数变化
      console.log("recordSizeChange");
    },
    openTips() {
      this.$alert("<span>使用说明文字</span>", "使用说明", {
        confirmButtonText: "确定",
        type: "info",
        center: true,
        dangerouslyUseHTMLString: true
      })
        .then(() => {})
        .catch(() => {});
    }
  }
};
</script>
<style>
</style>
