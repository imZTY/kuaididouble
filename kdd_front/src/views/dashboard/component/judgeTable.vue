<template>
  <div>
    <el-table
      :data="judgeList"
      border
      max-height="250"
      style="width: 100%"
      :header-cell-style="{'text-align':'center','padding':'0','height':'0'}"
      :cell-style="{'padding':'0','text-align':'center'}"
    >
      <div v-for="(value,key,index) in columns" :key="index">

        <el-table-column
          v-if="key === 'nst'"
          :prop="key"
          :label="value"
          :formatter="getNST"
          width="120"
        />

        <el-table-column
          v-else
          :prop="key"
          :label="value"
          width="120"
        />
      </div>
    </el-table>
  </div>
</template>

<script>
const columns = {

  baseLine: '基线值（bpm）',
  fhrvar: '变异（bpm）',
  fhrcycle: '周期变异（bpm）',
  fhrfast: '加速（次）',
  fhslow: '减速（次）',
  ed: '早减（次）',
  ld: '晚减（次）',
  vd: '变异减速（次）',
  dp: '延长减速（次）',
  dl: '轻量减速（次）',
  ds: '严重减速（次）',
  fmove: '胎动（次）',
  uctimes: '子宫收缩（次）',
  fasttime: '加速时间（秒）',
  fastvalue: '加速幅度（bpm)',
  judgeDate: '判读日期',
  nst: '结果', // 0：不满意； 1：有反应 ；2：可疑; 3:无反应; 4 :未判读
  notes: '备注',
  expertId: '医师编号',
  name: '医师姓名',
  upTime: '上传时间',
  ctgCode: 'CTG编码'
}
export default {
  props: {
    judgeList: {
      type: Array,
      default: () => {}
    }
  },
  data() {
    return {
      columns,
      nstDictionary: {
        0: '不满意',
        1: '有反应',
        2: '可疑',
        3: '无反应',
        4: '未判读'
      }
    }
  },
  methods: {
    getNST(row) {
      return this.nstDictionary[row.nst]
    }
  }
}
</script>

<style scoped lang="scss">
</style>
