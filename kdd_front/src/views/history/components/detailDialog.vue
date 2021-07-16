<template>
  <div>
    <el-dialog
     v-bind="$attrs" 
     v-on="$listeners" 
     @open="onOpen" 
     @close="onClose" 
     title="请求报文"
     :visible.sync="detailDialogVisible"
     width="60%"
     :before-close="cancel"
    >
      <el-form ref="elForm" :model="form" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="消息号" prop="messageId">
          <el-input v-model="form.messageId" placeholder="请输入消息号" readonly clearable
            :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="请求报文" prop="requestMsg">
          <el-input v-model="form.requestMsg" type="textarea" placeholder="请输入请求报文" readonly
            :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="响应报文" prop="responseMsg">
          <el-input v-model="form.responseMsg" type="textarea" placeholder="请输入响应报文" readonly
            :autosize="{minRows: 6, maxRows: 15}" :style="{width: '100%'}"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="cancel">确定</el-button>
      </div>
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
    form: {
      type: Object,
      default: function() {
        return {
          messageId: '',
          requestMsg: '',
          responseMsg: ''
        }
      }
    }
  },
  data() {
    return {
      rules: {
        messageId: [],
        requestMsg: [],
        responseMsg: [],
      },
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    onOpen() {},
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    cancel() {
      this.$emit('cancel')
    },
    handelConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.cancel()
      })
    },
  }
}

</script>
<style>
</style>
