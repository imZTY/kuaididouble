<template>
  <div>
    <el-form>
      <el-form-item class="item-box">
        <el-input v-model="key" placeholder="秘钥" />
        <el-button @click="test(1)">TEST1</el-button>
        <el-button @click="test(2)">TEST2</el-button>
        <el-button @click="test(3)">TEST3</el-button>
        <el-button @click="test(4)">TEST4</el-button>
        <el-button @click="test(5)">TEST5</el-button>
        <el-button @click="test(6)">TEST6</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData.testData" style="width: 100%">
      <el-table-column
        v-for="(item,index) in tableData.column"
        :prop="item.prop"
        :label="item.label"
        width="180"
      />
    </el-table>
    <div>{{result}}</div>
    <div>{{resultMes}}</div>
  </div>
</template>

<script>
import { reqGetSingleModel } from "@/api/model";
export default {
  data() {
    return {
      tableData: {
        column: [
          { prop: "baohao", label: "包号" },
          { prop: "bianhao", label: "编号" },
          { prop: "yunzhou", label: "孕周" },
          { prop: "jixian", label: "基线" },
          { prop: "cbianyi", label: "长变异" },
          { prop: "zhouqibianyi", label: "周期变异" },
          { prop: "jiashu", label: "加速" },
          { prop: "jianshu", label: "减速" },
          { prop: "zjian", label: "早减" },
          { prop: "wjian", label: "晚减" },
          { prop: "bianyijianshu", label: "变异减速" },
          { prop: "yanchangjianshu", label: "延长减速" },
          { prop: "qiandujianshu", label: "浅度减速" },
          { prop: "zhongdujianshu", label: "重度减速" },
          { prop: "taidong", label: "胎动" },
          { prop: "gscishu", label: "宫缩次数" },
          { prop: "jiashushijian", label: "加速时间" },
          { prop: "jiashufudu", label: "加速幅度" },
          { prop: "duanbianyi", label: "短变异" },
          { prop: "gaobianyishijian", label: "高变异时间" },
          { prop: "dibianyishijian", label: "低变异时间" },
          { prop: "youjiajianshushijian", label: "有加减速时间" },
          { prop: "diushilv", label: "丢失率" },
          { prop: "gsqiangdu", label: "宫缩强度" },
          { prop: "gsjiangeshijian", label: "宫缩间隔时间" },
          { prop: "gschixushijian", label: "宫缩持续时间" },
          { prop: "bianyijianshushijian", label: "变异减速时间" }
        ],
        testData: [
          {
            baohao: "15",
            bianhao: "7688",
            yunzhou: "28",
            jixian: "144",
            cbianyi: "16",
            zhouqibianyi: "2",
            jiashu: "6",
            jianshu: "0",
            zjian: "0",
            wjian: "0",
            bianyijianshu: "0",
            yanchangjianshu: "0",
            qiandujianshu: "0",
            zhongdujianshu: "0",
            taidong: "8",
            gscishu: "0",
            jiashushijian: "14",
            jiashufudu: "18",
            duanbianyi: "8.8",
            gaobianyishijian: "11",
            dibianyishijian: "0",
            youjiajianshushijian: "9",
            diushilv: "0",
            gsqiangdu: "0",
            gsjiangeshijian: "0",
            gschixushijian: "0",
            bianyijianshushijian: "0"
          },
          {
            baohao: "15",
            bianhao: "7689",
            yunzhou: "28",
            jixian: "142",
            cbianyi: "18",
            zhouqibianyi: "3",
            jiashu: "7",
            jianshu: "1",
            zjian: "0",
            wjian: "0",
            bianyijianshu: "1",
            yanchangjianshu: "0",
            qiandujianshu: "1",
            zhongdujianshu: "0",
            taidong: "5",
            gscishu: "0",
            jiashushijian: "13",
            jiashufudu: "13",
            duanbianyi: "9.48",
            gaobianyishijian: "9",
            dibianyishijian: "0",
            youjiajianshushijian: "11",
            diushilv: "0",
            gsqiangdu: "0",
            gsjiangeshijian: "0",
            gschixushijian: "0",
            bianyijianshushijian: "24"
          },
          {
            baohao: "15",
            bianhao: "7835",
            yunzhou: "41",
            jixian: "129",
            cbianyi: "21",
            zhouqibianyi: "3",
            jiashu: "6",
            jianshu: "0",
            zjian: "0",
            wjian: "0",
            bianyijianshu: "0",
            yanchangjianshu: "0",
            qiandujianshu: "0",
            zhongdujianshu: "0",
            taidong: "6",
            gscishu: "2",
            jiashushijian: "18",
            jiashufudu: "29",
            duanbianyi: "15.51",
            gaobianyishijian: "9",
            dibianyishijian: "0",
            youjiajianshushijian: "11",
            diushilv: "1",
            gsqiangdu: "60",
            gsjiangeshijian: "584",
            gschixushijian: "69",
            bianyijianshushijian: "0"
          },
          {
            baohao: "15",
            bianhao: "7910",
            yunzhou: "28",
            jixian: "145",
            cbianyi: "14",
            zhouqibianyi: "3",
            jiashu: "7",
            jianshu: "0",
            zjian: "0",
            wjian: "0",
            bianyijianshu: "0",
            yanchangjianshu: "0",
            qiandujianshu: "0",
            zhongdujianshu: "0",
            taidong: "7",
            gscishu: "2",
            jiashushijian: "11",
            jiashufudu: "12",
            duanbianyi: "7.9",
            gaobianyishijian: "9",
            dibianyishijian: "0",
            youjiajianshushijian: "11",
            diushilv: "0",
            gsqiangdu: "23",
            gsjiangeshijian: "424",
            gschixushijian: "82",
            bianyijianshushijian: "0"
          },
          {
            baohao: "15",
            bianhao: "8080",
            yunzhou: "41.2",
            jixian: "143",
            cbianyi: "11",
            zhouqibianyi: "5",
            jiashu: "1",
            jianshu: "0",
            zjian: "0",
            wjian: "0",
            bianyijianshu: "0",
            yanchangjianshu: "0",
            qiandujianshu: "0",
            zhongdujianshu: "0",
            taidong: "1",
            gscishu: "3",
            jiashushijian: "13",
            jiashufudu: "15",
            duanbianyi: "5.26",
            gaobianyishijian: "10",
            dibianyishijian: "1",
            youjiajianshushijian: "9",
            diushilv: "1",
            gsqiangdu: "25",
            gsjiangeshijian: "332",
            gschixushijian: "52",
            bianyijianshushijian: "0"
          },
          {
            baohao: "15",
            bianhao: "8141",
            yunzhou: "28",
            jixian: "141",
            cbianyi: "11",
            zhouqibianyi: "4",
            jiashu: "3",
            jianshu: "0",
            zjian: "0",
            wjian: "0",
            bianyijianshu: "0",
            yanchangjianshu: "0",
            qiandujianshu: "0",
            zhongdujianshu: "0",
            taidong: "7",
            gscishu: "0",
            jiashushijian: "12",
            jiashufudu: "24",
            duanbianyi: "4.79",
            gaobianyishijian: "8",
            dibianyishijian: "1",
            youjiajianshushijian: "11",
            diushilv: "2",
            gsqiangdu: "0",
            gsjiangeshijian: "0",
            gschixushijian: "0",
            bianyijianshushijian: "0"
          }
        ]
      },
      key: "",
      result:'',
      resultMes:''
    };
  },
  methods: {
    test(index) {
      var that=this
      reqGetSingleModel(this.key, this.tableData.testData[index]).then(
        function(res) {
          that.result=res.data.data
          that.resultMes=res.data.resultMsg
        },
        function(e) {
          console.log(e);
        }
      );
    }
  }
};
</script>

<style lang='scss'>
.item-box {
  .el-form-item__content {
    display: flex;
    margin: auto;
  }
}
</style>