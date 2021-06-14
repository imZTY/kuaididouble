<template>
  <div>
    <!-- 进件审核对话框 -->
    <el-dialog
      title="进件审核"
      :visible.sync="checkDialogVisible"
      width="60%"
      :before-close="cancel"
    >
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">法人姓名</span>
        <span style="width: 70%; text-align: left">{{ checkParma.name }}</span>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">证件类型</span>
        <span style="width: 70%; text-align: left">{{ checkParma.cardType == 1 ? "身份证" : "其他" }}</span>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">证件号</span>
        <span style="width: 70%; text-align: left">{{ checkParma.cardNo }}</span>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">联系电话</span>
        <span style="width: 70%; text-align: left">{{ checkParma.phone }}</span>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">联系Email</span>
        <span style="width: 70%; text-align: left">{{ checkParma.email }}</span>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">用户职位</span>
        <span style="width: 70%; text-align: left">{{ checkParma.job }}</span>
      </div>

      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">公司名称</span>
        <span style="width: 70%; text-align: left">{{ checkParma.organization }}</span>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">公司所在城市</span>
        <span style="width: 70%; text-align: left">{{ checkParma.city }}</span>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">公司详细地址</span>
        <span style="width: 70%; text-align: left">{{ checkParma.address }}</span>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">身份证正面</span>
        <img :src="checkParma.idcardFront" min-width="70" height="70" />
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">身份证背面</span>
        <img :src="checkParma.idcardBack" min-width="70" height="70" />
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">营业执照</span>
        <img :src="checkParma.businessLicense" min-width="70" height="70" />
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">修改时间</span>
        <span style="width: 70%; text-align: left">{{ checkParma.updateTime }}</span>
      </div>

      <div
        style="display: flex; align-items: center; margin: 16px; height: 28px"
      >
        <span style="width: 30%; text-align: center">是否通过</span>
        <el-radio-group v-model="checkParma.isPass" style="width: 70%">
          <el-radio :label="1">通过</el-radio>
          <el-radio :label="0">不通过</el-radio>
        </el-radio-group>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">备注信息</span>
        <el-input
          style="width: 70%"
          v-model="checkParma.description"
          size="mini"
          placeholder="请输入内容"
        ></el-input>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button
          type="primary"
          :disabled="checkParma.roleId == 3"
          @click="checkInterface"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { setRole } from '@/api/certificate'
import { Message } from 'element-ui'
export default {
  name: 'CheckDialog',
  inheritAttrs: false,
  components: {},
  props: {
    checkDialogVisible: {
      type: Boolean,
      default: false
    },
    checkParma: {
      type: Object,
      default: function() {
        return {
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
    }
  },
  data() {
    return {
      
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    cancel() {
      this.$emit('cancel')
    },
    // 审批通过
    checkInterface() {
      //编辑接口
      var data = this.checkParma;
      setRole({
        accountId: data.accountId,
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
          this.cancel()
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