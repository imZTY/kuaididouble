<template>
  <div>
    <el-dialog title="添加用户" :visible.sync="addModalVisible" :before-close="cancel">
      <el-form :model="form" :rules="rules">
        <el-form-item label="用户名称：" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>

        <el-form-item label="手机号：" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.phone" autocomplete="off" />
        </el-form-item>

        <el-form-item label="密码：" :label-width="formLabelWidth" prop="password">
          <el-input v-model="form.password" autocomplete="off" show-password />
        </el-form-item>

        <el-form-item label="角色：" :label-width="formLabelWidth">
          <el-select
            v-model="form.roleId"
            placeholder="请选择用户角色"
          >
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />

          </el-select>
        </el-form-item>

        <el-form-item label="备注：" :label-width="formLabelWidth" prop="remark">
          <el-input v-model="form.remark" autocomplete="off" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { reqAddUser } from '@/api/user'
import { Message } from 'element-ui'
export default {
  name: 'AddUser',
  props: {
    addModalVisible: {
      type: Boolean,
      default: false
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
      form: {
        name: '',
        phone: '',
        password: '',
        roleId: 1,
        remark: ''
      },
      formLabelWidth: '120px',
      rules: {
        name: [{ required: true, message: '请输入用户名称', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        roleId: [{ required: true, message: '请选择角色', trigger: 'blur' }]
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
    add() {
      reqAddUser(this.form).then(res => {
        this.cancel()
        this.refresh()
        if (res.data.resultCode === 200) {
          Message({
            message: '添加成功',
            type: 'success',
            duration: 1000
          })
        } else {
          Message({
            message: '添加失败',
            type: 'error',
            duration: 1000
          })
        }
      })
    }
  }
}
</script>

