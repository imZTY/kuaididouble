<template>
  <div>
    <el-dialog title="添加角色" :visible.sync="addModalVisible" :before-close="cancel">
      <el-form :model="form" :rules="rules">
        <el-form-item label="角色名称：" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>

        <el-form-item label="权限：" :label-width="formLabelWidth" prop="rights">
          <el-checkbox-group v-model="form.rights" @change="handleCheckedRightsChange">
            <el-checkbox v-for="right in rights" :key="right" :label="right">{{ right }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="角色信息：" :label-width="formLabelWidth" prop="message">
          <el-input v-model="form.message" autocomplete="off" />
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
import { reqAddRole } from '@/api/role'
import { Message } from 'element-ui'
const rightOptions = [
  '添加用户',
  '删除用户',
  '编辑用户',
  '上传数据',
  '删除数据'
]
import { RIGHTS2 } from '@/utils/role'
export default {
  name: 'AddUser',
  props: {
    addModalVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      form: {
        name: '',
        message: '',
        rights: []
      },
      formLabelWidth: '120px',
      rules: {
        name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
        message: [
          { required: true, message: '请输入角色信息', trigger: 'blur' }
        ]
      },

      rights: rightOptions,
      isIndeterminate: true
    }
  },

  methods: {
    cancel() {
      this.$emit('cancel')
    },
    refresh() {
      this.$emit('refresh')
    },
    handleCheckedRightsChange(value) {
      // const checkedCount = value.length
      // this.isIndeterminate = checkedCount > 0 && checkedCount < this.rights.length
    },
    add() {
      const rights = this.form.rights
      const res = []
      if (rights.length > 0) {
        rights.map(item => {
          res.push(RIGHTS2[item])
        })
      }

      this.form.rights = res.join('|')
      reqAddRole(this.form).then(res => {
        this.cancel()
        this.refresh()
        if (res.data.resultCode === 200) {
          Message({
            message: '添加成功',
            type: 'success',
            duration: 1000
          })
          this.form = {
            name: '',
            message: '',
            rights: []
          }
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

