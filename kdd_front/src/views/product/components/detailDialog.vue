<template>
  <div>
    <!-- 显示详情对话框 -->
    <el-dialog
      title="显示详情"
      :visible.sync="detailDialogVisible"
      width="45%"
      :before-close="cancel"
    >
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">产品名称</span>
        <span v-if="detailParam.type == 3" style="width: 70%; text-align: left">{{ detailParam.name }}</span>
        <el-input v-else v-model="detailParam.name" placeholder="请输入产品名称" :maxlength="15" show-word-limit clearable
           :style="{width: '100%'}"></el-input>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">父产品id</span>
        <span v-if="detailParam.type == 3" style="width: 70%; text-align: left">{{ detailParam.parentId }}</span>
        <el-select v-else v-model="detailParam.parentId" placeholder="请选择父产品" clearable :style="{width: '100%'}">
          <el-option v-for="(item, index) in parentOptions" :key="index" :label="item.label"
            :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 25%; text-align: center;">排序级别</span>
        <span v-if="detailParam.type == 3" style="width: 70%; text-align: left">{{ detailParam.sortIndex }}</span>
        <div v-else style="width: 70%; text-align: left; padding: 0 15px">
          <el-input-number  v-model="detailParam.sortIndex" clearable style="width: 50%; text-align: left" placeholder="请输入排序级别"></el-input-number>
        </div>
      </div>
      <div style="display: flex; align-items: center; margin: 16px">
        <span style="width: 30%; text-align: center">产品描述</span>
        <span v-if="detailParam.type == 3" style="width: 70%; text-align: left">{{ detailParam.description }}</span>
        <el-input v-else v-model="detailParam.description" placeholder="请输入产品描述" :maxlength="100" show-word-limit
         :style="{width: '100%'}"></el-input>
      </div>

      <div style="display: flex; align-items: center; margin: 16px">
        <el-transfer
          :titles="['未绑定收费规则', '已绑定收费规则']"
          @change="handleChange"
          v-model="linkedCharge"
          :data="chargeData">
        </el-transfer>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button
          v-if="detailParam.type == 3"
          type="primary"
          @click="cancel"
          >确 定</el-button
        >
        <el-button
          v-else-if="detailParam.type == 2"
          type="primary"
          @click="confirmUpdate"
          >确 定</el-button
        >
        <el-button
          v-else-if="detailParam.type == 1"
          type="primary"
          @click="confirmAdd"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { add, update } from "@/api/product"
import { getAll, pageList, link, unlink } from "@/api/charge"
import { Message } from 'element-ui'

export default {
  name: 'DetailDialog',
  inheritAttrs: false,
  components: {},
  props: {
    detailDialogVisible: {
      type: Boolean,
      default: false
    },
    detailParam: {
      type: Object,
      default: function() {
        return {
          type: 0,  // type: 0=未知，1=新增，2=修改，3=详情
          id: 0,
          name: "",  //产品名称
          parentId: 0,  //父产品
          description: "",  //描述
          sortIndex: 0  //排序级别
        }
      }
    }
  },
  data() {
    const generateData = _ => {
      const data = []
      var chargeInfoList = []
      // 获取所有规则
      pageList({
        page: 1,
        rows: 50
      }).then(
        function(res) {
          // success
          // console.log(res.data.data)
          chargeInfoList = res.data.data
          // 组装data
          chargeInfoList.forEach((chargeInfo, index) => {
            data.push({
              label: chargeInfo.chargeName,
              key: chargeInfo.id
            });
          });
        },
        function(e) {
          // failure
          console.error(e)
        }
      )
      return data;
    };
    const generateLinked = _ => {
      const data = []
      if (this.detailParam.id == null || this.detailParam.id == 0) {
        return []
      }
      var linkedCharge = []
      // 获取所有规则
      getAll({
        productId: this.detailParam.id
      }).then(
        function(res) {
          // success
          // console.log(res.data.data)
          linkedCharge = res.data.data
          // 组装data
          linkedCharge.forEach((chargeInfo, index) => {
            data.push({
              label: chargeInfo.chargeName,
              key: chargeInfo.id
            });
          });
        },
        function(e) {
          // failure
          console.error(e)
        }
      )
      return data;
    };
    return {
      chargeData: generateData(),
      linkedCharge: [],  //将key放进来即可
      parentOptions: [{
        "label": "根",
        "value": 0
      },{
        "label": "限时促销",
        "value": 1
      },{
        "label": "电子面单",
        "value": 2
      },{
        "label": "物流查询",
        "value": 3
      }]
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    getLinked() {
      var data = []
      var linkedCharge = []
      // 获取所有规则
      getAll({
        productId: this.detailParam.id
      }).then(
        function(res) {
          // success
          // console.log(res.data.data)
          linkedCharge = res.data.data
          // 组装data
          linkedCharge.forEach((chargeInfo, index) => {
            data.push(chargeInfo.id);
          });
        },
        function(e) {
          // failure
          console.error(e)
        }
      )
      this.linkedCharge = data
    },
    cancel() {
      this.$emit('cancel')
    },
    confirmAdd() {
      var that = this
      //编辑接口
      var data = this.detailParam;
      add({
        name: data.name,
        sortIndex: data.sortIndex,
        description: data.description,
        parentId: data.parentId
      }).then(
        function(res) {
          // success
          Message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          })
          that.cancel()
        },
        function(e) {
          // failure
          console.error(e);
          Message({
            message: '处理异常',
            type: 'error',
            duration: 1000
          })
        }
      )
    },
    confirmUpdate() {
      var that = this
      //编辑接口
      var data = this.detailParam;
      update({
        id: data.id,
        name: data.name,
        sortIndex: data.sortIndex,
        description: data.description,
        parentId: data.parentId
      }).then(
        function(res) {
          // success
          Message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          })
          that.cancel()
        },
        function(e) {
          // failure
          console.error(e);
          Message({
            message: '处理异常',
            type: 'error',
            duration: 1000
          })
        }
      )
    },
    handleChange(value, direction, movedKeys) {
      if (direction == 'right') {
        console.log("link " + movedKeys[0])
        link({
          chargeId: movedKeys[0],
          productId: this.detailParam.id
        }).then(
          function(res) {
            // success
            Message({
              message: '绑定收费规则成功',
              type: 'success',
              duration: 1000
            })
          },
          function(e) {
            // failure
            console.error(e);
            Message({
              message: '处理异常',
              type: 'error',
              duration: 1000
            })
          }
        )
      } else if (direction == 'left'){
        console.log("unlink " + movedKeys[0])
        unlink({
          chargeId: movedKeys[0],
          productId: this.detailParam.id
        }).then(
          function(res) {
            // success
            Message({
              message: '解绑收费规则成功',
              type: 'success',
              duration: 1000
            })
          },
          function(e) {
            // failure
            console.error(e);
            Message({
              message: '处理异常',
              type: 'error',
              duration: 1000
            })
          }
        )
      } else {
        console.error("unknow direction " + direction)
      }
    }
  }
}

</script>
<style>
</style>