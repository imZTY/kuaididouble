<template>
  <div style="padding:16px">
    <el-card style="margin-bottom: 16px">
      <div slot="header">
        <span>收费规则列表</span>
        <el-button
          type="info"
          size="small"
          @click="openTips"
          style="display: inline-block;margin:-6px 20px;float:right"
        >使用说明</el-button>
      </div>
      <el-button
        type='primary'
        icon='el-icon-plus'
        size='small'
        style='marginBottom:10px;'
        @click='showDetailDialog(0, null, 1)'
      >新增</el-button>
      <div style="position: relative;margin-bottom: 12px">
        <el-pagination
          @size-change="certificateSizeChange"
          @current-change="currentChange"
          :page-size="15"
          layout="total, prev, pager, next"
          :total="certificateTotal"
          style="display: inline-block;position: relative;right: 0;"
          :current-page.sync="currentPage"
        ></el-pagination>
      </div>
      <el-table :data="chargeList" border style="text-align: center;">
        <el-table-column prop="chargeName" label="规则名称" align="center" />
        <el-table-column prop="description" label="产品描述" align="center" />
        <el-table-column prop="chargeCurr" label="充值币种" align="center" />
        <el-table-column prop="chargePrice" :formatter="fenToYuan" label="充值金额(元)" align="center" />
        <el-table-column prop="amount" label="充值条数" align="center" />
        <el-table-column prop="disbaled" :formatter="parseDisabled" label="状态" align="center" />
        <el-table-column prop="createTime" :formatter="timestampToDateStr" label="创建时间" align="center" />
        <el-table-column prop="updateTime" :formatter="timestampToDateStr" label="修改时间" align="center" />
        <el-table-column label="操作" style="text-align: center;" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="showDetailDialog(scope.$index, scope.row, 2)"
            >编辑</el-button>
            <el-button
              v-if="scope.row.disabled == 0"
              type="danger"
              size="mini"
              @click="disable(scope.$index, scope.row, 1)"
            >禁用</el-button>
            <el-button
              v-else-if="scope.row.disabled == 1"
              type="success"
              size="mini"
              @click="disable(scope.$index, scope.row, 0)"
            >启用</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 详情弹窗 -->
    <detail-dialog
      :detail-dialog-visible='detailDialogVisible'
      :detail-param='detailParam'
      @cancel='cancel'
      @refresh='init(1)'
    />
  </div>
</template>
<script>
import { pageList, update } from "@/api/charge"
import { Message } from 'element-ui'
import DetailDialog from './components/detailDialog'
import moment from "moment"

export default {
  components: {
    DetailDialog
  },
  data() {
    return {
      detailDialogVisible: false, //进件审核对话框
      certificateTotal: 0, //需认证的总数
      currentPage: 1,
      detailParam: {
        type: 0,  // type: 0=未知，1=新增，2=修改，3=详情
        chargeName: "",  //规则名称
        description: "",  //描述
        chargeCurr: "",  //充值币种
        chargePrice: 0,  //充值金额
        chargePriceYuan: 0,  //充值金额(元)
        amount: 0  //充值条数
      },
      chargeList: [
        //模拟接口数据
      ]
    };
  },
  mounted() {
    this.getListByPage(1)
  },
  methods: {
    fenToYuan(rowdata) {
      return rowdata.chargePrice / 100;
    },
    parseDisabled(rowdata) {
      if (rowdata.disabled == 0) {
        return "启用"
      } else if (rowdata.disabled == 1) {
        return "禁用"
      } else {
        return "未知"
      }
    },
    timestampToDateStr(rowdata) {
      if (rowdata === undefined || rowdata.createTime === undefined || rowdata.createTime === null) {
        return "";
      }
      return moment(rowdata.createTime).format("yyyy/MM/DD hh:mm:ss");
    },
    getListByPage(p) {
      var that = this
      pageList({
        page: p,
        rows: 15
      }).then(
        function(res) {
          // success
          // console.log('pageList',res.data.data)
          that.certificateTotal = res.data.count
          that.chargeList = res.data.data
        },
        function(e) {
          // failure
          console.error(e)
        }
      );
    },
    disable(index, row, disabled) {
      var that = this
      update({
        id: row.id,
        disabled: disabled
      }).then(
        function(res) {
          // success
          Message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          })
          that.cancel()
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
      )
    },
    // 查看详情
    showDetailDialog(index, row, type) {
      //显示修改对话框
      this.detailParam.type = type  // type: 0=未知，1=新增，2=修改，3=详情
      if (type == 1) {
        this.detailParam.chargeName = null
        this.detailParam.description = null
        this.detailParam.chargeCurr = "CNY"
        this.detailParam.chargePrice = 0
        this.detailParam.chargePriceYuan = this.detailParam.chargePrice / 100
        this.detailParam.amount = 0
      } else {
        this.detailParam.id = row.id
        this.detailParam.chargeName = row.chargeName
        this.detailParam.description = row.description
        this.detailParam.chargeCurr = row.chargeCurr
        this.detailParam.chargePrice = row.chargePrice
        this.detailParam.chargePriceYuan = this.detailParam.chargePrice / 100
        this.detailParam.amount = row.amount
      }
      this.detailDialogVisible = true
    },
    cancel() {
      this.detailDialogVisible = false
      this.currentChange()
    },
    certificateSizeChange() {
      //接口分页每页显示条数变化
      console.log("certificateSizeChange")
    },
    currentChange() {
      //接口选中变化
      this.getListByPage(this.currentPage)
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
