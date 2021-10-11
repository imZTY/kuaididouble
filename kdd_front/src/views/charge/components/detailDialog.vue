<template>
  <div>
    <!-- 显示详情对话框 -->
    <el-dialog
      title="显示详情"
      :visible.sync="detailDialogVisible"
      width="60%"
      :before-close="cancel"
    >
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">规则名称</span>
        <span v-if="detailParam.type == 3" style="width: 70%; text-align: left">{{ detailParam.chargeName }}</span>
        <el-input v-else v-model="detailParam.chargeName" placeholder="请输入规则名称" :maxlength="15" show-word-limit clearable
           :style="{width: '100%'}"></el-input>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">充值币种</span>
        <span v-if="detailParam.type == 3" style="width: 70%; text-align: left">{{ detailParam.chargeCurr }}</span>
        <el-select v-else v-model="detailParam.chargeCurr" placeholder="请选择充值币种" clearable :style="{width: '100%'}">
          <el-option v-for="(item, index) in chargeCurrs" :key="index" :label="item.label"
            :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 25%; text-align: center;">充值金额(元)</span>
        <span v-if="detailParam.type == 3" style="width: 70%; text-align: left">{{ detailParam.chargePriceYuan }}</span>
        <div v-else style="width: 70%; text-align: left; padding: 0 15px">
          <el-input-number  v-model="detailParam.chargePriceYuan" clearable style="width: 50%; text-align: left" placeholder="请输入充值金额"></el-input-number>
        </div>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 25%; text-align: center;">充值笔数</span>
        <span v-if="detailParam.type == 3" style="width: 70%; text-align: left">{{ detailParam.amount }}</span>
        <div v-else style="width: 70%; text-align: left; padding: 0 15px">
          <el-input-number  v-model="detailParam.amount" clearable style="width: 50%; text-align: left" placeholder="请输入充值笔数"></el-input-number>
        </div>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">规则描述</span>
        <span v-if="detailParam.type == 3" style="width: 70%; text-align: left">{{ detailParam.description }}</span>
        <el-input v-else v-model="detailParam.description" placeholder="请输入规则描述" :maxlength="100" show-word-limit
         :style="{width: '100%'}"></el-input>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button
          v-if="detailParam.type == 3"
          type="primary"
          @click="cancel"
          >确 定</el-button
        >
        <el-button
          v-else-if="detailParam.type == 2"
          type="primary"
          @click="confirmUpdate"
          >确 定</el-button
        >
        <el-button
          v-else-if="detailParam.type == 1"
          type="primary"
          @click="confirmAdd"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { add, update } from "@/api/charge"
import { Message } from 'element-ui'
export default {
  name: 'DetailDialog',
  inheritAttrs: false,
  components: {},
  props: {
    detailDialogVisible: {
      type: Boolean,
      default: false
    },
    detailParam: {
      type: Object,
      default: function() {
        return {
          type: 0,  // type: 0=未知，1=新增，2=修改，3=详情
          chargeName: "",  //规则名称
          chargeCurr: "",  //充值币种
          chargePriceYuan: 0,  //充值金额(元)
          chargePrice: 0,  //充值金额(元)
          amount: 0,  //充值数量
          description: "",  //描述
        }
      }
    }
  },
  data() {
    return {
      chargeCurrs: [{
        "label": "人民币",
        "value": "CNY"
      }]
    }
  },
  computed: {},
  watch: {},
  created() {
  },
  mounted() {
  },
  methods: {
    cancel() {
      this.$emit('cancel')
    },
    confirmAdd() {
      var that = this
      //编辑接口
      var data = this.detailParam;
      add({
        chargeName: data.chargeName,
        chargeCurr: data.chargeCurr,
        chargePrice: data.chargePriceYuan * 100,
        amount: data.amount,
        description: data.description
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
    confirmUpdate() {
      var that = this
      //编辑接口
      var data = this.detailParam;
      update({
        id: data.id,
        chargeName: data.chargeName,
        chargeCurr: data.chargeCurr,
        chargePrice: data.chargePriceYuan * 100,
        amount: data.amount,
        description: data.description
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
    }
  }
}

</script>
<style>
</style>