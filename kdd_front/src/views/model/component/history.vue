<template>
  <div>
    <el-table :data="historyData" style="width: 100%" border :cell-style="{padding:'0'}">
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
          <el-button v-else type="text" size="small" @click="showPieDialog(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div v-show="historyData.length>0" class="pagination-wrapper">
      <el-pagination
        background
        :page-size="10"
        layout="total,prev, pager, next"
        :total="total"
        @current-change="changePage"
      />
    </div>
    <pie :dialog-visible="dialogVisible" :option="option" @close="handleClose" />
  </div>
</template>

<script>
import Pie from './pie'
export default {
  components: {
    Pie
  },
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
    return {
      dialogVisible: false,
      option: {
        backgroundColor: '#f7f7f7', // 背景颜色
        tooltip: {
          show: true,
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          x: 'center',
          data: ['满意', '不满意', '有反应', '无反应']
        },
        // toolbox: {
        //   show: false,
        //   feature: {
        //     mark: { show: true },
        //     dataView: { show: true, readOnly: false },
        //     restore: { show: true },
        //     saveAsImage: { show: true }
        //   }
        // },
        calculable: false,
        series: [
          {
            name: '模型一',
            type: 'pie',
            center: ['25%', '30%'],
            radius: [40, 50],
            data: [
              {
                value: 214,
                name: '满意',
                package: [
                  '包号15 编号7688',
                  '包号15 编号7689',
                  '包号15 编号7738',
                  '包号15 编号7835',
                  '包号15 编号7910',
                  '包号15 编号7974',
                  '包号15 编号7987',
                  '包号15 编号7994',
                  '包号15 编号7998',
                  '包号15 编号8022',
                  '包号15 编号8141'
                ]
              },
              {
                value: 214,
                name: '不满意',
                package: {}
              }, {
                value: 214,
                name: '有反应',
                package: {}
              },
              {
                value: 214,
                name: '无反应',
                package: {}
              }
            ],
            itemStyle: {
              // 系列级个性化
              normal: {
                color: function(params) {
                  var colorList = ['#339CD1', '#FE8463', '#9BCA63', '#FAD860']
                  return colorList[params.dataIndex]
                },
                labelLine: {
                  // 饼图不显示线条
                  length: 2,
                  show: true
                },
                label: {
                  // 饼图不显示文字
                  show: true,
                  position: 'inner', // 饼图图上显示百分比
                  formatter: function(params) {
                    return (params.percent - 0).toFixed(0) + '%'
                  },
                  textStyle: {
                    fontSize: 14
                  }
                }
              }
            }
          },
          {
            name: '模型二',
            type: 'pie',
            center: ['75%', '30%'],
            radius: [40, 50],
            data: [
              {
                value: 214,
                name: '满意',
                package: {}
              },
              {
                value: 214,
                name: '不满意',
                package: {}
              }, {
                value: 214,
                name: '有反应',
                package: {}
              },
              {
                value: 214,
                name: '无反应',
                package: {}
              }
            ],
            itemStyle: {
              // 系列级个性化
              normal: {
                color: function(params) {
                  var colorList = ['#339CD1', '#FE8463', '#9BCA63', '#FAD860']
                  return colorList[params.dataIndex]
                },
                labelLine: {
                  // 饼图不显示线条
                  length: 2,
                  show: false
                },
                label: {
                  // 饼图不显示文字
                  show: true,
                  position: 'inner', // 饼图图上显示百分比
                  formatter: function(params) {
                    return (params.percent - 0).toFixed(0) + '%'
                  },
                  textStyle: {
                    fontSize: 14
                  }
                }
              }
            }
          },
          {
            name: '模型三',
            type: 'pie',
            center: ['25%', '75%'],
            radius: [40, 50],
            data: [
              {
                value: 214,
                name: '满意',
                package: {}
              },
              {
                value: 214,
                name: '不满意',
                package: {}
              }, {
                value: 214,
                name: '有反应',
                package: {}
              },
              {
                value: 214,
                name: '无反应',
                package: {}
              }
            ],
            itemStyle: {
              // 系列级个性化
              normal: {
                color: function(params) {
                  var colorList = ['#339CD1', '#FE8463', '#9BCA63', '#FAD860']
                  return colorList[params.dataIndex]
                },
                labelLine: {
                  // 饼图不显示线条
                  length: 2,
                  show: false
                },
                label: {
                  // 饼图不显示文字
                  show: true,
                  position: 'inner', // 饼图图上显示百分比
                  formatter: function(params) {
                    return (params.percent - 0).toFixed(0) + '%'
                  },
                  textStyle: {
                    fontSize: 14
                  }
                }
              }
            }
          },
          {
            name: '模型四',
            type: 'pie',
            center: ['75%', '75%'],
            radius: [40, 50],
            data: [
              {
                value: 214,
                name: '满意',
                package: {}
              },
              {
                value: 214,
                name: '不满意',
                package: {}
              }, {
                value: 214,
                name: '有反应',
                package: {}
              },
              {
                value: 214,
                name: '无反应',
                package: {}
              }
            ],
            itemStyle: {
              // 系列级个性化
              normal: {
                color: function(params) {
                  var colorList = ['#339CD1', '#FE8463', '#9BCA63', '#FAD860']
                  return colorList[params.dataIndex]
                },
                labelLine: {
                  // 饼图不显示线条
                  length: 2,
                  show: true
                },
                label: {
                  // 饼图不显示文字
                  show: true,
                  position: 'inner', // 饼图图上显示百分比
                  formatter: function(params) {
                    return (params.percent - 0).toFixed(0) + '%'
                  },
                  textStyle: {
                    fontSize: 14
                  }
                }
              }
            }
          }
        ]
      }
    }
  },
  methods: {
    downloadErrorMsg(url) {
      var tempwindow = window.open('_blank')
      tempwindow.location = `http://192.168.158.43/ctg/download/errorFile?filename=${url.errorUrl}`
    },
    // 展示拼图
    showPieDialog(row) {
      // const id = row.id
      // 发送请求获取当前任务对应的处理结果（饼图数据）
      this.dialogVisible = true
    },
    handleClose() {
      this.dialogVisible = false
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
