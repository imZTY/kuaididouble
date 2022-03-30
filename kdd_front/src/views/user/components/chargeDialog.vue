<template>
  <div>
    <el-dialog 
      v-bind="$attrs" 
      v-on="$listeners" 
      @open="onOpen" 
      @close="onClose" 
      title="余额充值"
      :visible.sync="chargeModalVisible"
      width="60%"
      :before-close="cancel"
    >
      <el-form ref="form" :model="form" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="充值单号" prop="thirdOrderNo">
          <el-input v-model="form.thirdOrderNo" placeholder="请输入充值单号" clearable :style="{width: '100%'}">
          </el-input>
        </el-form-item>
        <el-form-item label="充值方式" prop="orderMethod">
          <el-select v-model="form.orderMethod" placeholder="请选择充值方式" clearable :style="{width: '100%'}">
            <el-option v-for="(item, index) in orderMethodOptions" :key="index" :label="item.label"
              :value="item.value" :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="充值条数" prop="balanceChange">
          <el-input-number v-model="form.balanceChange" placeholder="充值条数"></el-input-number>
        </el-form-item>
        <el-form-item label="订单金额" prop="orderAmount">
          <el-input-number v-model="form.orderAmount" placeholder="订单金额" :precision='2'></el-input-number>
        </el-form-item>
        <el-form-item label="优惠金额" prop="discountAmount">
          <el-input-number v-model="form.discountAmount" placeholder="优惠金额" :precision='2'></el-input-number>
        </el-form-item>
        <el-form-item label="实付金额" prop="actualAmount">
          <el-input-number v-model="form.actualAmount" placeholder="实付金额" :precision='2'></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="handelConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { charge } from '@/api/order'
import { Message } from 'element-ui'
export default {
  name: 'ChargeDialog',
  inheritAttrs: false,
  components: {},
  props: {
    chargeModalVisible: {
      type: Boolean,
      default: false
    },
    form: {
      type: Object,
      default: function() {
        return {
          accountId: 0,    // 从父组件传入
          orderMethod: 1,   // 1=支付宝,2=微信,3=银行卡,4=人工
          orderType: 1,   // 1=充值,2=退款(从父组件传入)
          thirdOrderNo: '',
          balanceChange: 0,
          orderAmount: 0,
          discountAmount: 0,
          actualAmount: 0,
        }
      }
    }
  },
  data() {
    return {
      rules: {
        thirdOrderNo: [{
          required: true,
          message: '请输入充值单号',
          trigger: 'blur'
        }],
        orderMethod: [{
          required: true,
          message: '请选择充值方式',
          trigger: 'change'
        }],
        balanceChange: [{
          required: true,
          message: '充值条数',
          trigger: 'blur'
        }],
        orderAmount: [{
          required: true,
          message: '订单金额',
          trigger: 'blur'
        }],
        discountAmount: [{
          required: false,
          message: '优惠金额',
          trigger: 'blur'
        }],
        actualAmount: [{
          required: true,
          message: '实付金额',
          trigger: 'blur'
        }],
      },
      orderMethodOptions: [{
        "label": "支付宝转账",
        "value": 1
      }, {
        "label": "微信转账",
        "value": 2
      }, {
        "label": "银行卡转账",
        "value": 3
      }, {
        "label": "人工交易",
        "value": 4
      }],
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    onOpen() {},
    onClose() {
      this.$refs['form'].resetFields()
    },
    cancel() {
      this.$emit('cancel')
    },
    handelConfirm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.orderAmount = this.form.orderAmount == null ? 0 : this.form.orderAmount * 100
          this.form.actualAmount = this.form.actualAmount == null ? 0 : this.form.actualAmount * 100
          this.form.discountAmount = this.form.discountAmount == null ? 0 : this.form.discountAmount * 100
          charge(this.form).then(res => {
            if (res.data.resultCode === 200) {
              Message({
                message: '充值成功',
                type: 'success',
                duration: 1000
              })
            } else {
              Message({
                message: '充值失败',
                type: 'error',
                duration: 1000
              })
            }
          })
        } else {
          return false
        }
        this.cancel()
      })
    },
  }
}

</script>
<style>
</style>
