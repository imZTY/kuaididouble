<template>
  <div style="padding:16px">
    <el-card style="margin-bottom: 16px">
      <div slot="header">
        <span>接口管理</span>
        <el-button
          type="info"
          size="small"
          @click="openTips"
          style="display: inline-block;margin:-6px 20px;float:right"
        >使用说明</el-button>
      </div>
      <div style="position: relative;margin-bottom: 12px">
        <el-button
          type="primary"
          size="mini"
          @click="showAddDialog"
          style="display: inline-block;"
        >新增</el-button>
        <el-button type="warning" size="mini" style="display: inline-block;">查询</el-button>
        <el-pagination
          @size-change="interfaceSizeChange"
          @current-change="interfaceCurrentChange"
          :page-size="5"
          layout="total, prev, pager, next"
          :total="interfaceTotal"
          style="display: inline-block;position: absolute;right: 0;"
          :current-page.sync="interfacePage"
        ></el-pagination>
      </div>
      <el-table :data="interfaceData" border style="text-align: center;">
        <el-table-column prop="id" label="设备id" align="center" />
        <el-table-column prop="name" label="设备名称" align="center" />
        <el-table-column prop="organization" label="设备所属组织" align="center" />
        <el-table-column prop="ipAddress" label="IP地址" align="center" />
        <el-table-column prop="deviceKey" label="秘钥" align="center" />
        <el-table-column prop="disableString" label="状态" align="center" />
        <el-table-column label="操作" style="text-align: center;" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary "
              size="mini"
              @click="showEditDialog(scope.$index, scope.row)"
            >修改</el-button>
            <!-- <el-button type="danger" size="mini" @click="deleteInterface(scope.$index, scope.row)">删除</el-button> -->
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-card>
      <div slot="header">
        <span>请求记录</span>
      </div>
      <div style="position: relative;margin-bottom: 12px">
        <el-button type="primary" size="mini" @click="refresh" style="display: inline-block;">刷新</el-button>
        <el-button type="warning" size="mini" style="display: inline-block;">查询</el-button>
        <el-pagination
          @size-change="recordSizeChange"
          @current-change="recordCurrentChange"
          :page-size="5"
          layout="total, prev, pager, next"
          :total="requestTotal"
          style="display: inline-block;position: absolute;right: 0;"
          :current-page.sync="recordPage"
        ></el-pagination>
      </div>
      <el-table :data="requestRecord" border style="text-align: center;">
        <el-table-column prop="id" label="设备id" align="center" />
        <el-table-column prop="name" label="设备名称" align="center" />
          <el-table-column prop="organization" label="设备所属组织" align="center" />
        <el-table-column prop="requestJson" label="请求详细" align="center" />
        <el-table-column prop="createTime" label="请求时间" align="center" />
        <el-table-column prop="responseJson" label="相应结果" align="center" />
        <el-table-column prop="ipAddress" label="请求IP" align="center" />
      </el-table>
    </el-card>
    <!-- 修改设备对话框 -->
    <el-dialog
      title="修改设备"
      :visible.sync="editDialogVisible"
      width="30%"
      :before-close="editDialogClose"
    >
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">设备名称</span>
        <el-input style="width: 70%;" v-model="editParma.name" size="mini" placeholder="请输入内容"></el-input>
      </div>
       <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">设备所属组织</span>
        <el-input style="width: 70%;" v-model="editParma.organization" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">IP地址</span>
        <el-input style="width: 70%;" v-model="editParma.ipAddress" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">秘钥</span>
        <el-input style="width: 70%;" v-model="editParma.deviceKey" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;height: 28px">
        <span style="width: 30%;text-align: center;">是否可用</span>
        <el-radio-group v-model="editParma.disable" style="text-align: center;width: 70%">
          <el-radio :label="0">可用</el-radio>
          <el-radio :label="1">不可用</el-radio>
        </el-radio-group>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editInterface">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新增设备对话框 -->
    <el-dialog
      title="新增设备"
      :visible.sync="addDialogVisible"
      width="30%"
      :before-close="addDialogClose"
    >
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">设备名称</span>
        <el-input style="width: 70%;" v-model="addParma.name" size="mini" placeholder="请输入内容"></el-input>
      </div>
       <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">设备所属组织</span>
        <el-input style="width: 70%;" v-model="addParma.organization" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">IP地址</span>
        <el-input style="width: 70%;" v-model="addParma.ipAddress" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <div style="display: flex;align-items: center;margin: 16px;">
        <span style="width: 30%;text-align: center;">秘钥</span>
        <el-input style="width: 70%;" v-model="addParma.deviceKey" size="mini" placeholder="请输入内容"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addInterface">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  reqGetDeviceHistory,
  reqEdiMachine,
  reqDelMachine,
  reqGetMachine,
  reqAddMachine
} from "@/api/device";

export default {
  data() {
    return {
      addDialogVisible: false, //新增设备对话框
      editDialogVisible: false, //修改设备对话框
      interfaceTotal: 0, //接口设备总数
      requestTotal: 0, //请求总数
      recordPage: 1,
      interfacePage: 1,
      addParma: {
        name: "",
        ipAddress: "",
        organization:"",
        deviceKey: "",
        disable: 0,
        disableString: ""
      },
      editParma: {
        id: "",
        name: "",
        ipAddress: "",
        organization:"",
        deviceKey: "",
        disable: 1
      },
      interfaceData: [
        //模拟接口数据
      ],
      requestRecord: [
        //模拟请求数据
      ]
    };
  },
  mounted() {
    this.getHistoryData(1);
    this.getMachineData(1);
  },
  methods: {
    init() {
      var that = this;
      reqGetDeviceHistory({
        page: p,
        rows: 5
      }).then(
        function(res) {
          // success
          // console.log(res)
          that.requestPage = res.data.count;
        },
        function(e) {
          // failure
          console.log(e);
        }
      );
    },
    getHistoryData(p) {
      var that = this;
      reqGetDeviceHistory({
        page: p,
        rows: 5
      }).then(
        function(res) {
          // success
          // console.log(res)
          that.requestTotal = res.data.data.count;
          that.requestRecord = res.data.data.data;
          that.requestRecord.forEach(e => {
            let data = JSON.parse(e.responseJson);
            if (data.result) e.responseJson = JSON.stringify(data.result);
          });
        },
        function(e) {
          // failure
          console.log(e);
        }
      );
    },
    getMachineData(p) {
      var that = this;
      reqGetMachine({
        page: p,
        rows: 5
      }).then(
        function(res) {
          // success
          console.log('interface',res.data.data.data)
          that.interfaceTotal = res.data.data.count;
          that.interfaceData = res.data.data.data;
          for (var i = 0; i < that.interfaceData.length; i++) {
            if (that.interfaceData[i].disable == 0) {
              that.interfaceData[i].disableString = "可用";
            } else {
              that.interfaceData[i].disableString = "不可用";
            }
          }
        },
        function(e) {
          // failure
          console.log(e);
        }
      );
    },
    addInterface() {
      //新增接口
      var data = this.addParma;
      var that = this;
      reqAddMachine({
        name: data.name,
        ipAddress: data.ipAddress,
        organization:data.organization,
        deviceKey: data.deviceKey
      }).then(
        function(res) {
          // success
          // console.log(res)
          that.getMachineData(1);
          that.interfacePage = 1;
          that.addDialogVisible = false;
        },
        function(e) {
          // failure
          console.log(e);
        }
      );
    },
    editInterface() {
      //编辑接口
      var data = this.editParma;
      var that = this;
      reqEdiMachine({
        id: data.id, //设备id
        name: data.name, //设备名称
        ipAddress: data.ipAddress, //IP地址
        organization:data.organization,
        deviceKey: data.deviceKey, //秘钥
        disable: data.disable //是否可用(0=可用,1=不可用) { id:2 disable:0 //取消删除,启动为"可用" }
      }).then(
        function(res) {
          // success
          that.getMachineData(that.interfacePage);
          that.editDialogVisible = false;
        },
        function(e) {
          // failure
          console.log(e);
        }
      );
    },
    deleteInterface(index, row) {
      //删除接口
      var that = this;
      reqDelMachine({
        id: row.id //单个id
      }).then(
        function(res) {
          // success
          // console.log(res)
          that.getMachineData(that.interfacePage);
        },
        function(e) {
          // failure
          console.log(e);
        }
      );
    },
    interfaceSizeChange() {
      //接口分页每页显示条数变化
      console.log("interfaceSizeChange");
    },
    interfaceCurrentChange() {
      //接口选中变化
      this.getMachineData(this.interfacePage);
    },
    refresh() {
      this.getHistoryData(this.recordPage);
      // console.log('refresh')
    },
    recordSizeChange() {
      //请求分页每页显示条数变化
      console.log("recordSizeChange");
    },
    recordCurrentChange() {
      //请求选中变化
      this.getHistoryData(this.recordPage);
    },
    addDialogClose() {
      // console.log('addDialogClose')
    },
    showAddDialog() {
      //显示新增对话框
      this.addDialogVisible = true;
    },
    showEditDialog(index, row) {
      //显示修改对话框
      this.editDialogVisible = true;
      // console.log(index,row)
      this.editParma.id = row.id;
      this.editParma.name = row.name;
      this.editParma.ipAddress = row.ipAddress;
      this.editParma.organization = row.organization;
      this.editParma.deviceKey = row.deviceKey;
      this.editParma.disable = row.disable;
    },
    addDialogClose() {
      this.addDialogVisible = false;
    },
    editDialogClose() {
      this.editDialogVisible = false;
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
