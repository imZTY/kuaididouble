<template>
  <div class="container">
    <div class="upload-history">
      <el-card>
        <el-form :inline="true">
          <el-form-item>
            <el-input
              v-model="packageNumber"
              placeholder="请输入包号"
              autofocus="true"
              size="small"
              style="width:100px;"
            />
          </el-form-item>
          <el-form-item>
            <el-upload
              class="upload-demo"
              accept=".xls, .zip"
              action="/"
              :show-file-list="false"
              :before-upload="beforeLinesUpload"
              style="margin-right:20px;"
            >
              <el-button size="small" type="success" style="margin-top:4px;">上传曲线数据文件</el-button>
            </el-upload>
          </el-form-item>

          <el-form-item>
            <el-upload
              class="upload-demo"
              accept=".xls, .xlsx, .zip"
              action="/"
              :show-file-list="false"
              :before-upload="beforeCtgUpload"
            >
              <el-button size="small" type="primary">上传CTG数据文件</el-button>
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
import { reqGetHistory, uploadCtg, uploadLines } from '@/api/ctg'
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
      packageNumber: null
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
      uploadCtg(formData).then(res => {
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
    // 上传曲线图数据
    beforeLinesUpload(file) {
      if (!this.packageNumber) {
        Message({
          type: 'error',
          message: '请输入包号！'
        })
        return false
      }
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
      formData.append('packageNumber', this.packageNumber)
      formData.append('upFile', file)
      uploadLines(formData).then(res => {
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
      reqGetHistory({ page, rows: 10, kind: 1 }).then(res => {
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
