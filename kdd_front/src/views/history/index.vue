<template>
  <div style="padding: 16px">
    <el-card style="margin-bottom: 16px">
      <div slot="header">
        <span>接口请求历史</span>
        <el-button
          type="info"
          size="small"
          @click="openTips"
          style="display: inline-block; margin: -6px 20px; float: right"
          >使用说明</el-button
        >
      </div>
      <div style="position: relative; margin-bottom: 12px">
        <el-pagination
          @size-change="historySizeChange"
          @current-change="historyCurrentChange"
          :page-size="15"
          layout="total, prev, pager, next"
          :total="historyTotal"
          style="display: inline-block; position: relative; right: 0"
          :current-page.sync="currentHistoryPage"
        ></el-pagination>
      </div>
      <el-table :data="historyData" border style="text-align: center">
        <el-table-column prop="messageId" label="消息号" align="center" />
        <el-table-column prop="accountId" label="请求账号" align="center" />
        <el-table-column
          prop="thirdApiCompany"
          label="快递公司"
          :formatter="parseThirdApiCompany"
          align="center"
        />
        <el-table-column prop="isError" :formatter="parseIsError" label="是否异常" align="center" />
        <el-table-column prop="costTime" :formatter="addMsUnit" label="接口耗时" align="center" />
        <el-table-column prop="createTime" :formatter="timestampToDateStr" label="创建时间" align="center" />
        <el-table-column label="操作" style="text-align: center" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="showCheckDialog(scope.$index, scope.row)"
              >查看报文</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <detail-dialog
      :detail-dialog-visible='detailDialogVisible'
      :form='form'
      @cancel='cancel'
      @refresh='init(1)'
    />
  </div>
</template>
<script>
import { pageList } from "@/api/queryLog"
import DetailDialog from './components/detailDialog'
import moment from "moment"

export default {
  components: { DetailDialog },
  data() {
    return {
      detailDialogVisible: false,
      historyTotal: 0,
      currentHistoryPage: 1,
      form: {
        messageId: '',
        requestMsg: '',
        responseMsg: ''
      },
      historyData: [
        //模拟接口数据
      ],
    };
  },
  mounted() {
    this.getUnfinishPage(1)
  },
  methods: {
    parseThirdApiCompany(rowdata) {
      if (rowdata.thirdApiCompany == 1) {
        return '顺丰速运'
      }
      if (rowdata.thirdApiCompany == 2) {
        return '优速快递'
      }
      if (rowdata.thirdApiCompany == 3) {
        return '中通快递'
      }
      if (rowdata.thirdApiCompany == 4) {
        return '百世快递'
      }
      if (rowdata.thirdApiCompany == 5) {
        return '圆通快递'
      }
      if (rowdata.thirdApiCompany == 6) {
        return '德邦快递'
      }
      if (rowdata.thirdApiCompany == 7) {
        return '快递鸟'
      }
      return '未知('+rowdata.thirdApiCompany+')'
    },
    parseIsError(rowdata) {
      if (rowdata.isError === 0) {
        return '正常'
      } else {
        return '异常'
      }
    },
    addMsUnit(rowdata) {
      return rowdata.costTime + 'ms'
    },
    timestampToDateStr(rowdata) {
      if (rowdata === undefined || rowdata.createTime === undefined || rowdata.createTime === null) {
        return "";
      }
      return moment(rowdata.createTime).format("yyyy/MM/DD hh:mm:ss")
    },
    getUnfinishPage(p) {
      var that = this;
      pageList({
        page: p,
        rows: 15,
      }).then(
        function (res) {
          // success
          // console.log('pageList',res.data.data)
          that.historyTotal = res.data.count
          that.historyData = res.data.data
        },
        function (e) {
          // failure
          console.error(e)
        }
      );
    },
    // 查看详情
    showCheckDialog(index, row) {
      this.form.messageId = row.messageId
      this.form.requestMsg = row.requestMsg
      this.form.responseMsg = row.responseMsg
      this.detailDialogVisible = true
    },
    cancel() {
      this.detailDialogVisible = false
      this.historyCurrentChange()
    },
    historySizeChange() {
      //接口分页每页显示条数变化
      console.log("historySizeChange")
    },
    historyCurrentChange() {
      //接口选中变化
      this.getUnfinishPage(this.currentHistoryPage)
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
        dangerouslyUseHTMLString: true,
      })
        .then(() => {})
        .catch(() => {})
    },
  },
};
</script>
<style>
</style>
