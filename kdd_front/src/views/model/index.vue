<template>
  <div class="container">
    <div class="upload-history">
      <el-card>
        <el-form :inline="true">
          <el-form-item>
            <el-upload
              class="upload-demo"
              accept=".csv"
              action="/"
              :show-file-list="false"
              :before-upload="beforeCtgUpload"
              style="margin-right:20px;"
            >
              <el-button size="small" type="primary" style="margin-top:4px;">上传模型数据</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <History :history-data="historyData" :total="total" @changePage="getHistory" />
      </el-card>
    </div>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import History from './component/history'
import { reqGetHistory, uploadModel } from '@/api/ctg'
import axios from 'axios'
axios.defaults.withCredentials = true
export default {
  components: {
    History
  },
  data() {
    return {
      fileList: [],
      historyData: [],
      total: null,
      fullscreenLoading: false,
      name: null
    }
  },
  mounted() {
    this.getHistory(1)
  },
  methods: {
    fileChange(file) {
      this.files.push(file.raw) // 上传文件变化时将文件对象push进files数组
    },
    // 上传CTG数据
    beforeCtgUpload(file) {
      const name = '导入模型数据' + file.name
      const isLarge = file.size / 1024 > 20480
      if (isLarge) {
        this.$message({
          type: 'error',
          message: '文件大小超过20M！'
        })
        return false
      }
      const loading = this.$loading({
        lock: true,
        text: '拼命上传中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      const formData = new FormData()
      formData.append('upFile', file)
      formData.append('name', name)
      uploadModel(formData).then(res => {
        loading.close()
        if (res.data.resultCode === 200) {
          Message({
            type: 'success',
            message: res.data.data
          })
        } else {
          Message({
            type: 'error',
            message: res.data.data || '上传失败'
          })
        }
        this.getHistory(1)
      })
      return false
    },
    getHistory(page) {
      reqGetHistory({ page, rows: 10, kind: 2 }).then(res => {
        this.total = res.data.count
        this.historyData = res.data.data
      })
    }
  }
}
</script>

<style scoped>
.container {
  padding-top: 16px;
}
.upload-history {
  width: 100%;
  padding: 0 16px 16px 16px;
  margin-bottom: 16px;
}
</style>
