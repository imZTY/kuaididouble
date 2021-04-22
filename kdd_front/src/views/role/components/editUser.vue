<template>
  <div>
    <el-dialog
      title="修改角色"
      :visible.sync="addModalVisible"
      :before-close="cancel"
    >
      <el-form
        :model="form"
        :rules="rules"
      >

        <el-form-item
          label="角色名称："
          :label-width="formLabelWidth"
          prop="name"
        >
          <el-input
            v-model="form.name"
            autocomplete="off"
          />
        </el-form-item>

        <el-form-item
          label="权限："
          :label-width="formLabelWidth"
          prop="rights"
        >
          <el-checkbox-group
            v-model="form.rights"
            @change="handleCheckedRightsChange"
          >
            <el-checkbox
              v-for="right in rights"
              :key="right"
              :label="right"
            >{{ right }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item
          label="角色信息："
          :label-width="formLabelWidth"
          prop="message"
        >
          <el-input
            v-model="form.message"
            autocomplete="off"
          />
        </el-form-item>
      </el-form>

      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="cancel">取 消</el-button>
        <el-button
          type="primary"
          @click="add"
        >确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { reqEditRole } from '@/api/role'
import { Message } from 'element-ui'
const rightOptions = ['添加用户', '删除用户', '编辑用户', '上传数据', '删除数据']
import { RIGHTS2 } from '@/utils/role'
export default {
  name: 'AddUser',
  props: {
    addModalVisible: {
      type: Boolean,
      default: false
    },
    form: {
      type: Object,
      default: () => {
        return {
          name: '',
          message: '',
          rights: []
        }
      }
    }
  },
  data() {
    return {

      formLabelWidth: '120px',
      rules: {
        name: [
          { required: true, message: '请输入用户名称', trigger: 'blur' }
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
      rights.map(item => {
        res.push(RIGHTS2[item])
      })
      this.form.rights = res.join('|')
      reqEditRole(this.form).then(res => {
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
    }
  }
}
</script>

