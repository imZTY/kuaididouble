<template>
  <div>
    <el-dialog title="编辑用户" :visible.sync="editModalVisible" :before-close="cancel">
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="用户名称：" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" readonly autocomplete="off" />
        </el-form-item>

        <el-form-item label="手机号：" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.phone" readonly autocomplete="off" />
        </el-form-item>

        <el-form-item label="角色：" :label-width="formLabelWidth">
          <el-select v-model="form.roleId" placeholder="请选择用户角色">
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.name"
              :value="item.id.toString()"
            />
          </el-select>
        </el-form-item>

        <!-- <el-form-item label="是否启用：" :label-width="formLabelWidth">
          <el-select v-model="form.disable" disabled="true" placeholder="请选择启用或禁用">
            <el-option label="启用" value="0" />
            <el-option label="禁用" value="1" />
          </el-select>
        </el-form-item> -->
        <el-form-item label="备注：" :label-width="formLabelWidth" prop="remark">
          <el-input v-model="form.remark" readonly autocomplete="off" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="update('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { reqSetRole } from '@/api/user'
import { Message } from 'element-ui'
export default {
  name: 'EditUser',
  props: {
    editModalVisible: {
      type: Boolean,
      default: false
    },
    form: {
      type: Object,
      default: function() {
        return {
          name: '',
          phone: '',
          roleId: '',
          disable: '',
          remark: '',
          rules: {
            name: [
              { required: true, message: '请输入用户名称', trigger: 'blur' }
            ],
            phone: [
              { required: true, message: '请输入手机号', trigger: 'blur' }
            ]
          }
        }
      }
    },
    roleList: {
      type: Array,
      default: function() {
        return []
      }
    }
  },
  data() {
    return {
      formLabelWidth: '120px',
      rules: {
        name: [{ required: true, message: '请输入用户名称', trigger: 'blur' }]
      }
    }
  },
  methods: {
    cancel() {
      this.$emit('cancel')
    },
    refresh() {
      this.$emit('refresh')
    },
    update(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          reqSetRole(this.form).then(res => {
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
