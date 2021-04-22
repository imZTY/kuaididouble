<template>
  <div>
    <el-dialog title="模型数据" :visible.sync="dialogVisible" width="54%" :before-close="handleClose">
      <ul class="model-name-box">
        <li>
          <p>模型一</p>
          <p>模型二</p>
        </li>
        <li>
          <p>模型三</p>
          <p>模型四</p>
        </li>
      </ul>

      <div id="myChart" />
    </el-dialog>
    <el-drawer title="包号编号" :visible.sync="drawer" :direction="direction">
      <div v-for="name in packageList" :key="name" class="text item">{{ name }}</div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    option: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      drawer: false,
      direction: 'rtl',
      packageList: [
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
    }
  },
  watch: {
    dialogVisible(val) {
      console.log(val)
      if (val) {
        this.$nextTick(() => {
          this.drawLine()
        })
      }
    }
  },
  methods: {
    handleClose() {
      this.$emit('close')
    },
    drawLine() {
      const myChart = this.$echarts.init(document.getElementById('myChart'))
      myChart.setOption(this.option)
      myChart.on('click', param => {
        this.packageList = param.data.package
        this.drawer = true
      })
    }
  }
}
</script>

<style scoped lang="scss">
#myChart {
  width: 635px;
  height: 500px;
}
.text {
  font-size: 14px;
}
.item {
  margin-bottom: 18px;
}
.model-name-box {
  list-style: none;
  width: 635px;
  height: 500px;
  //   border: 1px solid #000;
  margin: 0;
  padding: 0;
  position: absolute;
  top: 74px;
  li {
    width: 100%;
    text-align: center;
    // background: rgba(0,0,0,.5);
    height: 250px;
    padding: 0;
    margin: 0;
    display: flex;
    justify-content: center;
    align-items: flex-end;
    p {
      padding: 0;
      margin: 0;
      color: #000;
      font-weight: bold;
      //   background: blue;
      width: 100%;
    }
  }
}
</style>
