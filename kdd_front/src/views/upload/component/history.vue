<template>
  <div>
    <el-table
      :data="historyData"
      style="width: 100%"
      border
      :cell-style="{padding:'0'}"
    >
      <el-table-column prop="name" label="任务名称" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="状态">
        <template slot-scope="scope">
          <p>{{ scope.row.status==1?'处理完成':'处理中' }}</p>
        </template>
      </el-table-column>
      <el-table-column label="处理结果">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.errorUrl"
            type="text"
            size="small"
            @click="downloadErrorMsg(scope.row)"
          >下载错误信息</el-button>
          <p v-else />

        </template>
      </el-table-column>
    </el-table>

    <div v-show="historyData" class="pagination-wrapper">
      <el-pagination
        background
        :page-size="10"
        layout="total,prev, pager, next"
        :total="total"
        @current-change="changePage"
      />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    historyData: {
      type: Array,
      default: () => {
        []
      }
    },
    total: {
      type: Number,
      default: () => {}
    }
  },
  data() {
    return {}
  },
  methods: {
    downloadErrorMsg(url) {
      var tempwindow = window.open('_blank')
      tempwindow.location = `http://192.168.158.43/api/ctg/download/errorFile?filename=${url.errorUrl}`
    },
    changePage(e) {
      this.$emit('changePage', e)
    }
  }
}
</script>

<style scoped lang="scss">
.pagination-wrapper {
  width: 100%;
  text-align: right;
  margin-top: 20px;
}
</style>
