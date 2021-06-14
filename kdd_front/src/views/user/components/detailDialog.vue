<template>
  <div>
      <!-- 进件审核对话框 -->
    <el-dialog
      title="进件审核"
      :visible.sync="detailDialogVisible"
      width="60%"
      :before-close="cancel"
    >
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
        <el-button type="primary" @click="cancel">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'DetailDialog',
  inheritAttrs: false,
  components: {},
  props: {
    detailDialogVisible: {
      type: Boolean,
      default: false
    },
    checkParam: {
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
    refresh() {
      this.$emit('refresh')
    }
  }
}

</script>
<style>
</style>
