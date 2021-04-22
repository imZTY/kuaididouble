<template>
  <el-button type="warning" size="mini">
    <a ref="link" @click="handleClick">导出</a>
  </el-button>
</template>
<script type="text/javascript">
import { reqGetCtgData } from '@/api/ctg'
export default {
  props: {
    header: {
      type: Array,
      default: function() {
        return []
      }
    },
    fileName: {
      type: String,
      default: 'ctg.csv'
    },
    searchForm: {
      type: Object,
      default: () => {}
    },
    total: {
      type: Number,
      default: () => {}
    }
  },
  data() {
    return {
      data: []
    }
  },
  computed: {
    headerLabel: function() {
      let result
      result = this.header
      result = result.join(',')
      return result
    },
    headerProp: function() {
      return this.header
    }
  },
  methods: {
    getAllData() {
      return new Promise((resolve, reject) => {
        const data = this.searchForm
        data.page = 1
        data.rows = this.total
        reqGetCtgData(data).then(res => {
          this.data = res.data.data
          resolve()
        })
      })
    },
    handleClick: function() {
      this.getAllData().then(() => {
        if (!this.data || this.data.length <= 0) {
          this.$message({
            message: '请先查询CTG数据,并等待查询结果返回!',
            type: 'error'
          })
          return
        }
        var csvContent = 'data:text/csv;charset=utf-8,\ufeff'
        csvContent += this.headerLabel + '\n'
        this.data.forEach((item, index) => {
          let dataString = ''
          for (let i = 0; i < this.headerProp.length; i++) {
            dataString += item[this.headerProp[i]] + ','
          }
          csvContent +=
            index < this.data.length
              ? dataString.replace(/,$/, '\n')
              : dataString.replace(/,$/, '')
        })
        this.$refs.link.setAttribute('href', encodeURI(csvContent))
        this.$refs.link.setAttribute('download', this.fileName)
      })
    }
  }
}
</script>
