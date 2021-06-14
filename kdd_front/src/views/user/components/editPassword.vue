<template>
  <div>
    <el-dialog title="修改密码" :visible.sync="editPwModalVisible" :before-close="cancel">
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="用户名称：" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off" disabled />
        </el-form-item>

        <el-form-item label="密码：" :label-width="formLabelWidth" prop="password">
          <el-input v-model="form.password" type="password" autocomplete="off" />
        </el-form-item>

        <el-form-item label="确认密码" :label-width="formLabelWidth" prop="checkPass">
          <el-input v-model="checkPass" type="password" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
        <el-button type="primary" @click="add('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { reqEditAccount } from '@/api/user'
import { Message } from 'element-ui'
export default {
  name: 'EditUser',
  props: {
    editPwModalVisible: {
      type: Boolean,
      default: false
    },
    form: {
      type: Object,
      default: function() {
        return {
          name: '',
          account: '',
          roleId: '',
          disable: '',
          remark: '',
          rules: {
            name: [
              { required: true, message: '请输入用户名称', trigger: 'blur' }
            ],
            password: [
              { validator: validatePass, trigger: 'blur', required: true }
            ]
          }
        }
      }
    }
  },
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (value.length < 6) {
          callback(new Error('密码不能少于6位'))
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (this.checkPass === '') {
        callback(new Error('请再次输入密码'))
      } else if (this.checkPass !== this.form.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      formLabelWidth: '120px',
      checkPass: '',
      rules: {
        name: [{ required: true, message: '请输入用户名称', trigger: 'blur' }],
        password: [{ validator: validatePass, trigger: 'blur' }],
        checkPass: [{ validator: validatePass2, trigger: 'blur' }]
      }
    }
  },
  methods: {
    cancel() {
      this.$emit('cancel')
      this.checkPass = ''
    },
    refresh() {
      this.$emit('refresh')
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.checkPass = ''
    },
    add(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          reqEditAccount(this.form).then(res => {
            this.cancel()
            this.refresh()
            if (res.data.resultCode === 200) {
              Message({
                message: '修改成功',
                type: 'success',
                duration: 1000
              })
            } else {
              Message({
                message: '修改失败',
                type: 'error',
                duration: 1000
              })
            }
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>
