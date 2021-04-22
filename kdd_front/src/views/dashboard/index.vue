// <template>
//   <div class="dashboard-container">
//     <div class="search-wrapper">
//       <el-card>
//         <el-row style="margin-bottom:10px;display:flex;align-item:center;">
//           <el-button type="primary" size="mini" @click="()=>{dialogFormVisible=true}">筛选</el-button>
//           <el-button type="danger" size="mini" @click="reset">重置</el-button>
//           <ExportCsv
//             :header="Object.keys(columns)"
//             :data="exportData"
//             :search-form="searchForm"
//             :total="total"
//           />
//           <el-row style="display:flex;align-item:center;margin-left:30px;">
//             <el-checkbox v-model="toggleCtg" @change="handleToggleCtg">CTG</el-checkbox>
//             <el-checkbox v-model="toggleJudge" @change="handleToggleJudge">医师判读数据</el-checkbox>
//             <el-checkbox v-model="toggleLine" @change="handleToggleLine">曲线图</el-checkbox>
//           </el-row>

//           <transition name="fade">
//             <div v-show="toggleCtg" style="margin-left:30px;">
//               <el-pagination
//                 background
//                 :page-size="20"
//                 layout="total,prev, pager, next"
//                 :total="total"
//                 :current-page="elcpage"
//                 :page-count="pageCount"
//                 @current-change="changePage"
//               />
//             </div>
//           </transition>
//         </el-row>
//         <transition name="fade">
//           <div v-show="toggleCtg" ref="ctg" class="ctg-wrapper">
//             <el-table
//               ref="singleTable"
//               :data="ctgData"
//               border
//               max-height="200"
//               style="width: 100%;margin-bottom:10px;"
//               class="ctg-table"
//               :header-cell-style="{'text-align':'center','padding':'0','height':'0'}"
//               :cell-style="{'padding':'0','text-align':'center'}"
//               highlight-current-row
//               type="index"
//               :row-class-name="tableRowClassName"
//               @current-change="handleCurrentChange"
//               @row-click="getColumn"
//             >
//               <el-table-column type="index" />

//               <div v-for="(value,key,index) in columns" :key="index">
//                 <el-table-column
//                   v-if="key === 'nst'"
//                   :prop="key"
//                   label="机器判读"
//                   :formatter="getNST"
//                   width="100%"
//                 />
//                 <el-table-column
//                   v-else-if="key === 'checkPlace'"
//                   :prop="key"
//                   label="检查地点"
//                   :formatter="getCheckPlace"
//                   width="100%"
//                 />
//                 <el-table-column v-else :prop="key" :label="key" width="100%" />
//               </div>
//               <el-table-column prop="checkDate" label="checkDate" width="300" />
//               <el-table-column prop="judgeDate" label="judgeDate" width="300" />
//               <el-table-column prop="deviceEdition" label="deviceEdition" width="300" />
//             </el-table>
//           </div>
//         </transition>
//       </el-card>
//     </div>

//     <transition name="fade">
//       <div v-show="toggleJudge" class="judge-wrapper">
//         <el-card>
//           <judge-table :judge-list="judgeList" />
//         </el-card>
//       </div>
//     </transition>

//     <el-card>
//       <div>
//         <div id="dqsj" style="background-color:#fff">
//           <throatButton
//             :throat="1500"
//             :can-click="lastDisable"
//             style="height:32px;font-size:12px;padding:7px 15px "
//             @click="lastbtn"
//           >上一条</throatButton>

//           <throatButton
//             :throat="1500"
//             :can-click="nextDisable"
//             style="height:32px;font-size:12px;padding:7px 15px "
//             @click="nextbtn"
//           >下一条</throatButton>
//         </div>
//         <el-collapse id="elcollapse">
//           <el-collapse-item title="当前数据">
//             <div class="access">
//               <el-table
//                 :data="currentRowArray"
//                 border
//                 max-height="200"
//                 style="width: 100%;margin-bottom:10px;"
//                 class="ctg-table access-table"
//                 :header-cell-style="{'text-align':'center','padding':'0','height':'0'}"
//                 :cell-style="{'padding':'5','text-align':'center'}"
//                 highlight-current-row
//                 type="index"
//                 size="mini"
//               >
//                 <el-table-column type="index" />

//                 <div v-for="(value,key,index) in columns" :key="index">
//                   <el-table-column
//                     v-if="key === 'nst'"
//                     :prop="key"
//                     label="机器判读"
//                     :formatter="getNST"
//                     width="100%"
//                   />
//                   <el-table-column
//                     v-else-if="key === 'checkPlace'"
//                     :prop="key"
//                     label="检查地点"
//                     :formatter="getCheckPlace"
//                     width="100%"
//                   />
//                   <el-table-column v-else :prop="key" :label="key" width="100%" />
//                 </div>
//                 <el-table-column prop="checkDate" label="checkDate" width="300" />
//                 <el-table-column prop="judgeDate" label="judgeDate" width="300" />
//                 <el-table-column prop="deviceEdition" label="deviceEdition" width="300" />
//               </el-table>
//             </div>
//           </el-collapse-item>
//         </el-collapse>
//       </div>
//     </el-card>

//     <transition name="fade">
//       <div v-show="toggleLine" class="chart-t-wrapper" style="margin-top:6px;">
//         <el-card>
//           <div class="chart-box">
//             <div class="chart-left">
//               <div id="myChart" />
//               <div id="myChart2" />
//             </div>
//              <el-form ref="fhrForm"   :inline="false" :model="markForm" :rules="rules" style="width:16%;">

//                 <el-form-item label="曲线" prop="type">
//                 <el-select v-model="markForm.type" size="small">
//                   <el-option label="胎心率" value="fhrForm">胎心率</el-option>
//                   <el-option label="宫缩" value="ucForm">宫缩</el-option>
//                 </el-select>
//                </el-form-item>
//               <el-form-item label="类型" prop="name">
//                 <el-select v-model="markForm.name" size="small">
//                   <el-option label="加速" value="1">加速</el-option>
//                   <el-option label="减速" value="2">减速</el-option>
//                 </el-select>
//                </el-form-item>

//                     <el-form-item label="起点横坐标" prop="start">
//                       <el-input v-model="markForm.start" size="small" />
//                     </el-form-item>

//                     <el-form-item label="终点横坐标" prop="end">
//                       <el-input v-model="markForm.end" size="small" />
//                     </el-form-item>

//                     <el-form-item>
//                       <el-button type="primary" size="small" @click="handleAddMark()">添加标注</el-button>
//                     </el-form-item>
//                   </el-form>
//           </div>
      
     

//           <el-collapse id="elcollapse">
//             <el-collapse-item title="标注">

//               <el-tabs v-model="activeName" @tab-click="handleMarkClick">
//                 <el-tab-pane label="胎心率" name="fhr">
              
//                   <el-button type="warning" size="mini" class="mb-btn" @click="downloadMarkData">下载标注数据</el-button>

//                   <el-table
//                     :data="fhrMarkData"
//                     border
//                     :header-cell-style="{'text-align':'center','padding':'0','height':'0'}"
//                     :cell-style="{'padding':'5','text-align':'center'}"
//                     highlight-current-row
//                     type="index"
//                     size="mini"
//                   >
//                     <el-table-column type="index" />
//                     <el-table-column prop="name" label="类型" />
//                     <el-table-column prop="start" label="起点坐标" />
//                     <el-table-column prop="end" label="终点坐标" />
//                     <el-table-column fixed="right" label="操作">
//                       <template slot-scope="scope">
//                         <el-button type="danger" size="mini" @click="delMark(scope.row)">删除</el-button>
//                       </template>
//                     </el-table-column>
//                   </el-table>
//                 </el-tab-pane>

//                 <el-tab-pane label="宫缩" name="uc">
                
//                   <el-button type="warning" size="mini" class="mb-btn" @click="downloadMarkData">下载标注数据</el-button>

//                   <el-table
//                     :data="ucMarkData"
//                     border
//                     :header-cell-style="{'text-align':'center','padding':'0','height':'0'}"
//                     :cell-style="{'padding':'5','text-align':'center'}"
//                     highlight-current-row
//                     type="index"
//                     size="mini"
//                   >
//                     <el-table-column type="index" />
//                     <el-table-column prop="name" label="类型" />
//                     <el-table-column prop="start" label="起点坐标" />
//                     <el-table-column prop="end" label="终点坐标" />
//                     <el-table-column fixed="right" label="操作">
//                       <template slot-scope="scope">
//                         <el-button type="danger" size="mini" @click="delMark(scope.row)">删除</el-button>
//                       </template>
//                     </el-table-column>
//                   </el-table>
//                 </el-tab-pane>
//               </el-tabs>
//             </el-collapse-item>
//           </el-collapse>
//         </el-card>
//       </div>
//     </transition>

//     <div class="chart-g-wrapper" />

//     <search-dialog
//       :dialog-form-visible="dialogFormVisible"
//       @search="search"
//       @cancel="()=>{dialogFormVisible=false}"
//     />
//   </div>
// </template>

// <script>
// import ExportCsv from './component/csv'

// const fhrOption = {
//   title: {
//     text: '胎心率'
//   },
//   backgroundColor: '#FBFBFB',
//   tooltip: {
//     trigger: 'axis',
//     axisPointer: {
//       show: true,
//       type: 'cross',
//       lineStyle: {
//         type: 'dashed',
//         width: 1
//       }
//     }
//   },
//   toolbox: {
//     show: true,
//      right:15,
//     itemSize:20,
//     itemGap:15,
//     feature: {
//       // mark: { show: true },
//       // dataView: { show: true, readOnly: false },
//       // restore: { show: true },
//       // saveAsImage: { show: true }
//     brush:{
//       type:['rect','clear'],
     
//       title:{
//         rect:'开始标注',
//         clear:'取消标注'
//       }
//     }
//     }
//   },
//   brush: {
//     // toolbox: ['rect', 'clear'],
//     xAxisIndex: 'all',
//     yAxisIndex: 'all',
//     transformable: true,
//     throttleType: 'debounce',
//     throttleDelay: 500,
//     removeOnClick: true,
//     outOfBrush: {
//       colorAlpha: 0.1
//     }
//     // brushStyle: {
//     //   borderWidth: 1,
//     //   color: "rgba(0,0,0,0.2)",
//     //   borderColor: "#000"
//     // },

//   },
//   calculable: true,
//   dataZoom: {
//     show: true,
//     start: 0,
//     end: 20
//   },
//   xAxis: [
//     {
//       name: '分钟',
//       type: 'value'
//     }
//   ],
//   yAxis: [
//     {
//       name: 'bpm(次/每分钟)',
//       type: 'value',
//       axisLine: {
//         lineStyle: {
//           // color: "#dc143c"
//         }
//       }
//     }
//   ],
//   series: [
//     {
//       name: '数据1',
//       type: 'line',
//       symbol: 'none', // 拐点样式
//       // symbol: 'circle', // 拐点样式
//       // symbolSize: 5, // 拐点大小
//       smooth: true,
//       itemStyle: {
//         normal: {
//           color: 'red', // 改变折线点的颜色
//           lineStyle: {
//             color: '#F56C6C' // 改变折线颜色
//           }
//         }
//       },
//       data: [],
//       markArea: {
//         symbolSize: 20,
//         // 标注文字
//         label: {
//           normal: {
//             position: ['25%', '50%'],
//             color: '#000',
//             fontWeight: 'bold',
//             fontSize: 15,
//             // 自定义富文本样式。利用富文本样式，可以在标签中做出非常丰富的效果
//             rich: {
//               a: {
//                 color: 'red',
//                 lineHeight: 10
//               }
//             }
//           }
//         },
//         itemStyle: {
//           normal: {
//             color: 'rgba(0,0,0,.1)'
//           }
//         },
//         // 起点与终点坐标之间的区域 coord为坐标
//         data: [
//           [
//             {
//               name: '加速',
//               coord: [5, 15]
//             },
//             {
//               coord: [10, 52]
//             }
//           ]
//         ]
//       }
//     }
//   ]
// }

// const ucOption = {
//   title: {
//     text: '宫缩'
//   },
//   backgroundColor: '#FBFBFB',
//   tooltip: {
//     trigger: 'axis',
//     axisPointer: {
//       show: true,
//       type: 'cross',
//       lineStyle: {
//         type: 'dashed',
//         width: 1
//       }
//     }
//   },
//   toolbox: {
//     show: true,
//     right:15,
//     itemSize:20,
//     itemGap:15,
//     feature: {
//       // mark: { show: true },
//       // dataView: { show: true, readOnly: false },
//       // restore: { show: true },
//       // saveAsImage: { show: true }
//        brush:{
//       type:['rect','clear'],
     
//       title:{
//         rect:'开始标注',
//         clear:'取消标注'
//       }
//     }
//     }
//   },
//    brush: {
//     // toolbox: ['rect', 'clear'],
//     xAxisIndex: 'all',
//     yAxisIndex: 'all',
//     transformable: true,
//     throttleType: 'debounce',
//     throttleDelay: 500,
//     removeOnClick: true,
//     outOfBrush: {
//       colorAlpha: 0.1
//     }
//     // brushStyle: {
//     //   borderWidth: 1,
//     //   color: "rgba(0,0,0,0.2)",
//     //   borderColor: "#000"
//     // },

//   },
//   calculable: true,
//   dataZoom: {
//     show: true,
//     start: 0,
//     end: 20
//   },
//   xAxis: [
//     {
//       name: '分钟',
//       type: 'value'
//     }
//   ],
//   yAxis: [
//     {
//       name: 'bpm(次/每分钟)',
//       type: 'value',
//       axisLine: {
//         lineStyle: {
//           // color: "#dc143c"
//         }
//       }
//     }
//   ],
//   series: [
//     {
//       name: '数据1',
//       type: 'line',
//       symbol: 'none', // 拐点样式
//       // symbol: 'circle', // 拐点样式
//       // symbolSize: 5, // 拐点大小
//       // smooth: true,
//       itemStyle: {
//         normal: {
//           color: 'blue', // 改变折线点的颜色
//           lineStyle: {
//             color: '#409EFF' // 改变折线颜色
//           }
//         }
//       },
//       data: [],
//       markArea: {
//         symbolSize: 20,
//         // 标注文字
//         label: {
//           normal: {
//             position: ['25%', '50%'],
//             color: '#000',
//             fontWeight: 'bold',
//             fontSize: 15,
//             // 自定义富文本样式。利用富文本样式，可以在标签中做出非常丰富的效果
//             rich: {
//               a: {
//                 color: 'red',
//                 lineHeight: 10
//               }
//             }
//           }
//         },
//         itemStyle: {
//           normal: {
//             color: 'rgba(0,0,0,.1)'
//           }
//         },
//         // 起点与终点坐标之间的区域 coord为坐标
//         data: [
//           [
//             {
//               name: '加速',
//               coord: [5, 15]
//             },
//             {
//               coord: [10, 52]
//             }
//           ],
//           [
//             {
//               name: '减速',
//               coord: [2, 25]
//             },
//             {
//               coord: [3, 5]
//             }
//           ]
//         ]
//       }
//     }
//   ]
// }

// const columns = {
//   packageNumber: null,
//   currentUID: 0,
//   name: null,
//   id: 33014,
//   ctgNumber: null,
//   gravidaId: null,
//   gestationalAge: null,
//   checkPlace: null,
//   times: 1,
//   watchTime: null,
//   age: 25,
//   // checkDate: null,
//   // judgeDate: null,
//   // deviceEdition: "暂无",
//   fileName: null,
//   deviceBrand: '暂无',
//   deviceBatch: null,
//   expertReadBegin: 0,
//   expertReadEnd: 0,
//   baseLine: 0,
//   fhrvar: 8,
//   fhrcycle: 3,
//   fhrfast: 0,
//   fhslow: 0,
//   ed: 0,
//   ld: 0,
//   vd: 0,
//   dp: 0,
//   dl: 0,
//   ds: 0,
//   fmove: 7,
//   uctimes: 53,
//   fasttime: 0,
//   fastvalue: 0,
//   stv: 4.66,
//   highltv: 5,
//   lowltv: 1,
//   haveaccdec: 14,
//   lostRate: 0,
//   ucstrong: 30,
//   ucnext: 0,
//   ucktime: 0,
//   vdtime: 0,
//   frhEvaluation: 0,
//   varEvaluation: 0,
//   fhslowEvaluation: 0,
//   fhrfastEvaluation1: 0,
//   fhrfastEvaluation0: 0,
//   nstEvaluation: 0,
//   nst: 2,
//   upTime: 0,
//   ctgCode: null
// }

// import { reqGetCtgData, reqGetCtgDetail, addMark, updateMark } from '@/api/ctg'
// import { Message } from 'element-ui'
// import throatButton from './component/throatButton'
// import JudgeTable from './component/judgeTable'
// import SearchDialog from './component/search'

// export default {
//   name: 'Dashboard',
//   components: {
//     JudgeTable,
//     ExportCsv,
//     SearchDialog,
//     throatButton
//   },
//   data() {
//     return {
//       toggleCtg: true,
//       toggleJudge: true,
//       toggleLine: true,
//       toggleMarkForm: false,
//       ctgData: [],
//       exportData: [],
//       dialogFormVisible: false,
//       columns,
//       fhrOption,
//       ucOption,
//       lineData: {
//         fm: [],
//         uc: [],
//         fhr: []
//       },
//       judgeList: [],
//       packageNumber: null, // 包号
//       ctgNumber: null, // 编号
//       ctgCode: null, // 联合编号,
//       page: 1,
//       total: null,
//       currentRow: null,
//       searchForm: {
//         id: null,
//         packageNumber: null,
//         ctgNumber: null,
//         dlMin: null,
//         dlMax: null,
//         dpMin: null,
//         dpMax: null,
//         dsMin: null,
//         dsMax: null,
//         edMin: null,
//         edMax: null,
//         fasttimeMin: null,
//         fasttimeMax: null,
//         fastvalueMin: null,
//         fastvalueMax: null,
//         fhrcycleMin: null,
//         fhrcycleMax: null,
//         fhrfastMin: null,
//         fhrfastMax: null,
//         fhrfast_evaluation_0Min: null,
//         fhrfast_evaluation_0Max: null,
//         fhrfast_evaluation_1Min: null,
//         fhrfast_evaluation_1Max: null,
//         fhrvarMin: null,
//         fhrvarMax: null,
//         fhslow_evaluationMin: null,
//         fhslow_evaluationMax: null,
//         fmoveMin: null,
//         fmoveMax: null,
//         frh_evaluationMin: null,
//         frh_evaluationMax: null,
//         haveaccdecMin: null,
//         haveaccdecMax: null,
//         highltvMin: null,
//         highltvMax: null,
//         ldMin: null,
//         ldMax: null,
//         lowltvMin: null,
//         lowltvMax: null,
//         nstMin: null,
//         nstMax: null,
//         nst_evaluationMin: null,
//         nst_evaluationMax: null,
//         stvMin: null,
//         stvMax: null,
//         ucktimeMin: null,
//         ucktimeMax: null,
//         ucnextMin: null,
//         ucnextMax: null,
//         uctimesMin: null,
//         uctimesMax: null,
//         var_evaluationMin: null,
//         var_evaluationMax: null,
//         vdMin: null,
//         vdMax: null,
//         vdtimeMin: null,
//         vdtimeMax: null,
//         ageMin: null,
//         ageMax: null,
//         base_lineMax: null,
//         check_dateMin: null,
//         check_dateMax: null,
//         check_place: null,
//         device_batch: null,
//         device_brand: null,
//         expert_read_beginMin: null,
//         expert_read_beginMax: null,
//         expert_read_endMin: null,
//         expert_read_endMax: null,
//         file_name: null,
//         gestationalAgeMin: null,
//         gestationalAgeMax: null,
//         gravida_id: null,
//         judge_dateMin: null,
//         judge_dateMax: null,
//         lost_rateMin: null,
//         lost_rateMax: null,
//         up_timeMin: null,
//         up_timeMax: null,
//         watch_timeMin: null,
//         watch_timeMax: null
//       },
//       nstDictionary: {
//         0: '不满意',
//         1: '有反应',
//         2: '可疑',
//         3: '无反应',
//         4: '未判读'
//       },
//       checkPlaceDictionary: {
//         1: '医院',
//         2: '家庭',
//         3: '社区医院',
//         4: '暂无'
//       },
//       rindex: 0,
//       elcpage: 1,
//       nextDisable: false,
//       lastDisable: true,
//       pageCount: null,
//       currentRowArray: [],
//       activeName: 'fhr',
//       markForm: {
//         name: null,
//         type: 'fhrForm',
//         start: null,
//         end: null
//       },

//       fhrMarkData: [],
//       ucMarkData: [],

//       markJson: {
//         fhr: [
//           [
//             {
//               name: '加速aaaa',
//               coord: [5, 150]
//             },
//             {
//               coord: [5.3, 152]
//             }
//           ]
//         ],
//         uc: [
//           [
//             {
//               name: '减速',
//               coord: [1, 5]
//             },
//             {
//               coord: [2, 7]
//             }
//           ]
//         ]
//       },

//       rules: {
//         name: [
//           { required: true, message: '请选择标注类型', trigger: 'blur' }
//         ],
//          type: [
//           { required: true, message: '请选择标注曲线', trigger: 'blur' }
//         ],
//         start: [
//           { required: true, message: '请选择起点横坐标', trigger: 'blur' }
//         ],
//         end: [
//           { required: true, message: '请选择终点横坐标', trigger: 'blur' }
//         ]
//       }
      
//     }
//   },
//   mounted() {
//     this.getCtgData(this.page)
   
//     window.addEventListener('scroll', this.handleScroll)
//   },

//   methods: {

//     handleMarkClick(tab, event) {
//       console.log(event)
//     },
//     handleAddBtn() {
//       this.toggleMarkForm = !this.toggleMarkForm
//     },
//     handleToggleCtg(v) {
//       this.toggleCtg = v
//     },
//     handleToggleJudge(v) {
//       this.toggleJudge = v
//     },
//     handleToggleLine(v) {
//       this.toggleLine = v
//     },

//     changePage(e) {
//       this.getCtgData(e)
//       this.changePageData(e)
//     },
//     customClick() {
//       console.log(1)
//     },

//     changePageData(e) {
//       this.pageCount = Math.ceil(this.total / 20)
//       this.elcpage = e
//       this.check_btn()
//     },

//     search(searchForm) {
//       this.searchForm = searchForm
//       this.getCtgData(this.page)
//       this.dialogFormVisible = false
//     },

//     reset() {
//       this.searchForm = {
//         id: null,
//         packageNumber: null,
//         ctgNumber: null,
//         nst_different: 0,
//         nst_same: 0,
//         ys_different: 0,
//         dlMin: null,
//         dlMax: null,
//         dpMin: null,
//         dpMax: null,
//         dsMin: null,
//         dsMax: null,
//         edMin: null,
//         edMax: null,
//         fasttimeMin: null,
//         fasttimeMax: null,
//         fastvalueMin: null,
//         fastvalueMax: null,
//         fhrcycleMin: null,
//         fhrcycleMax: null,
//         fhrfastMin: null,
//         fhrfastMax: null,
//         fhrfast_evaluation_0Min: null,
//         fhrfast_evaluation_0Max: null,
//         fhrfast_evaluation_1Min: null,
//         fhrfast_evaluation_1Max: null,
//         fhrvarMin: null,
//         fhrvarMax: null,
//         fhslow_evaluationMin: null,
//         fhslow_evaluationMax: null,
//         fmoveMin: null,
//         fmoveMax: null,
//         frh_evaluationMin: null,
//         frh_evaluationMax: null,
//         haveaccdecMin: null,
//         haveaccdecMax: null,
//         highltvMin: null,
//         highltvMax: null,
//         ldMin: null,
//         ldMax: null,
//         lowltvMin: null,
//         lowltvMax: null,
//         nstMin: null,
//         nstMax: null,
//         nst_evaluationMin: null,
//         nst_evaluationMax: null,
//         stvMin: null,
//         stvMax: null,
//         ucktimeMin: null,
//         ucktimeMax: null,
//         ucnextMin: null,
//         ucnextMax: null,
//         uctimesMin: null,
//         uctimesMax: null,
//         var_evaluationMin: null,
//         var_evaluationMax: null,
//         vdMin: null,
//         vdMax: null,
//         vdtimeMin: null,
//         vdtimeMax: null,
//         ageMin: null,
//         ageMax: null,
//         base_lineMax: null,
//         check_dateMin: null,
//         check_dateMax: null,
//         check_place: null,
//         device_batch: null,
//         device_brand: null,
//         expert_read_beginMin: null,
//         expert_read_beginMax: null,
//         expert_read_endMin: null,
//         expert_read_endMax: null,
//         file_name: null,
//         gestationalAgeMin: null,
//         gestationalAgeMax: null,
//         gravida_id: null,
//         judge_dateMin: null,
//         judge_dateMax: null,
//         lost_rateMin: null,
//         lost_rateMax: null,
//         up_timeMin: null,
//         up_timeMax: null,
//         watch_timeMin: null,
//         watch_timeMax: null
//       }
//       this.getCtgData(1)
//     },

//     tableRowClassName({ row, rowIndex }) {
//       // 把每一行的索引放进row
//       row.index = rowIndex
//     },

//     check_btn() {
//       if (
//         this.elcpage == this.pageCount &&
//         this.rindex == this.ctgData.length - 1
//       ) {
//         this.nextDisable = true
//       } else {
//         this.nextDisable = false
//       }
//       if (this.elcpage == 1 && this.rindex == 0) {
//         this.lastDisable = true
//       } else {
//         this.lastDisable = false
//       }
//     },

//     getColumn(row) {
//       this.rindex = row.index
//       this.check_btn()
//     },

//     setCurrent(row) {
//       this.$refs.singleTable.setCurrentRow(row)
//     },

//     paginationClick(e) {},

//     nextbtn(val) {
//       var col = ''
//       this.rindex++
//       var index = this.rindex
//       var page = this.elcpage
//       var len = this.ctgData.length - 1
//       this.check_btn()
//       // 此处能获取到当前行的信息
//       if (index > len) {
//         this.elcpage++
//         this.changePage(this.elcpage)
//         this.rindex = 0
//         index = 0
//         col = this.ctgData[index]
//         this.$refs.singleTable.setCurrentRow(this.ctgData[index])
//       } else {
//         this.$refs.singleTable.setCurrentRow(this.ctgData[index])
//         this.currentRow = this.ctgData[index]
//         col = this.ctgData[index]
//         if (col.packageNumber && col.ctgNumber && col.ctgCode) {
//           const { packageNumber, ctgNumber, ctgCode } = col
//           this.getDetail(packageNumber, ctgNumber, ctgCode)
//         } else {
//           this.$message({
//             type: 'error',
//             message: '当前行CTG数据不完整'
//           })
//         }
//       }
//     },

//     lastbtn(val) {
//       var col = ''
//       this.rindex--
//       var index = this.rindex
//       var page = this.elcpage
//       var len = this.ctgData.length - 1
//       this.check_btn()
//       // 此处能获取到当前行的信息
//       if (index < 0) {
//         this.elcpage--
//         this.changePage(this.elcpage)
//         this.rindex = 19
//         index = 19
//         col = this.ctgData[index]
//         this.$refs.singleTable.setCurrentRow(this.ctgData[index])
//       } else {
//         this.$refs.singleTable.setCurrentRow(this.ctgData[index])
//         this.currentRow = this.ctgData[index]
//         col = this.ctgData[index]
//         if (col.packageNumber && col.ctgNumber && col.ctgCode) {
//           const { packageNumber, ctgNumber, ctgCode } = col
//           this.getDetail(packageNumber, ctgNumber, ctgCode)
//         } else {
//           this.$message({
//             type: 'error',
//             message: '当前行CTG数据不完整'
//           })
//         }
//       }
//     },

//     getCtgData(page) {
//       this.searchForm.page = page
//       this.searchForm.rows = 20
//       reqGetCtgData(this.searchForm)
//         .then(res => {
//           this.total = res.data.count
//           this.ctgData = res.data.data
//           if (this.ctgData[0]) {
//             if (this.ctgData[0].packageNumber) {
//               this.packageNumber = this.ctgData[0].packageNumber
//             }
//             if (this.ctgData[0].ctgNumber) {
//               this.ctgNumber = this.ctgData[0].ctgNumber
//             }
//             if (this.ctgData[0].ctgCode) {
//               this.ctgCode = this.ctgData[0].ctgCode
//             }
//           }
//            this.currentRowArray=[]
//           this.currentRowArray.push(this.ctgData[0])
//         })
//         .then(() => {
//           this.getDetail(this.packageNumber, this.ctgNumber, this.ctgCode)
//         })
//     },

//     handleCurrentChange(val) {
//       if (val) {
//         this.currentRow = val
//         this.currentRowArray = []
//         this.currentRowArray.push(val)
      
//         // 此处能获取到当前行的信息
//         if (val.packageNumber && val.ctgNumber && val.ctgCode) {
//           const { packageNumber, ctgNumber, ctgCode } = val
//           this.getDetail(packageNumber, ctgNumber, ctgCode)
//         } else {
//           this.$message({
//             type: 'error',
//             message: '当前行CTG数据不完整'
//           })
//         }
//       }
//     },
//     getDetail(packageNumber, ctgNumber, ctgCode) {
//       if (packageNumber && ctgNumber && ctgCode) {
//         reqGetCtgDetail({ packageNumber, ctgNumber, ctgCode }).then(res => {
//           const d = res.data.data
//           this.lineData = d.lineData
//           this.judgeList = d.judgeList
//           // todo

//           this.markJson.fhr = eval(d.lineData.fhrJson)
//           this.markJson.uc = eval(d.lineData.ucJson)
//           this.json2Table()
//           this.drawLine()
//         })
//       } else {
//         Message({
//           type: 'error',
//           message: '获取CTG详细数据失败（缺少包名）'
//         })
//       }
//     },
//     // 下载标注文件
//     downloadMarkData() {
//       const url = '/ctg/download/markDataFile?ctgCode=' + this.ctgData[this.rindex].ctgCode
//       window.open(url, '_black')
//     },
//     // 将二维数组转化为表格数据
//     json2Table() {
//       const { fhr, uc } = this.markJson

//       const d1 = []
//       const d2 = []

//       if (fhr) {
//         fhr.map((item, idx) => {
//           const temp = {}
//           temp.idx = idx
//           temp.name = item[0].name
//           temp.markType = item[0].markType
//           temp.start = item[0].coord.join(' , ')
//           temp.end = item[1].coord.join(' , ')
//           // temp.
//           d1.push(temp)
//         })
//       }

//       if (uc) {
//         uc.map((item, idx) => {
//           const temp = {}
//           temp.idx = idx
//           temp.name = item[0].name
//           temp.markType = item[0].markType
//           temp.start = item[0].coord.join(' , ')
//           temp.end = item[1].coord.join(' , ')
//           d2.push(temp)
//         })
//       }
//       this.fhrMarkData = d1
//       this.ucMarkData = d2
//     },
//     data2json(data) {
//       const ret = []
//       data.map(item => {
//         const temp = []
//         temp[0] = {
//           name: item.name,
//           coord: item.start.split(' , '),
//           markType: item.markType
//         }
//         temp[1] = {
//           coord: item.end.split(' , ')
//         }
//         ret.push(temp)
//       })
//       return ret
//     },
//     handleLineData(arr) {
//       const ret = []
//       arr.map((item, index) => {
//         ret.push([0.013 * (index + 1), item])
//       })
//       return ret
//     },
//     getY(x, type) {
//       let y = 0
//       if (type === 'fhrForm') {
//         const idx = Math.round(x / 0.013) - 1
//         y = this.fhrOption.series[0].data[idx][1]
//       } else {
//         const idx = Math.round(x / 0.013) - 1
//         y = this.ucOption.series[0].data[idx][1]
//       }
//       return y
//     },
//     delMark(scope) {
//         const ctgCode = Number(this.currentRowArray[0].ctgCode)
//           const ctgNumber = Number(this.currentRowArray[0].ctgNumber)
//           const packageNumber = Number(this.currentRowArray[0].packageNumber)

//       let d = []
//       // 删除表格中一项
//       if (this.activeName === 'fhr') {
//         this.fhrMarkData.splice(scope.idx, 1)
//         d = this.data2json(this.fhrMarkData)
//       } else {
//         this.ucMarkData.splice(scope.idx, 1)
//         d = this.data2json(this.ucMarkData)
//       }
//       // 将tableData转化内为二维数组
//       const obj = {
//         id: null,
//         json: null
//       }
//       const arr = []
//       d.map(item => {
//         arr.push(JSON.stringify(item))
//       })
//       obj.json = `[${arr.join()}]`
//       obj.id = this.activeName === 'fhr' ? this.fhrOption.id : this.ucOption.id
//       updateMark(obj).then(res => {
//         this.getDetail(packageNumber,ctgNumber,ctgCode)
//         this.$message({
//           type: 'success',
//           message: '删除成功！'
//         })
//       })
//     },
//     drawLine() {
//       // 基于准备好的dom，初始化echarts实例
//       if (!this.lineData) {
//         this.$message({
//           message: '无曲线数据',
//           type: 'error'
//         })
//       }
//       var myChart = this.$echarts.init(document.getElementById('myChart'))
//       var myChart2 = this.$echarts.init(document.getElementById('myChart2'))
//       // 绘制图表

//       if (this.lineData) {
//         const { ucMarkId, ucJson, uc, fhrMarkId, fhr } = this.lineData

//         this.fhrOption.id = fhrMarkId
//         // this.fhrOption.series[0].markArea.data = JSON.parse(fhrJson)
//         this.fhrOption.series[0].markArea.data = this.markJson.fhr

//         this.fhrOption.series[0].data = this.handleLineData(fhr)

//         this.ucOption.id = ucMarkId
//         this.ucOption.series[0].markArea.data = JSON.parse(ucJson)
//         this.ucOption.series[0].data = this.handleLineData(uc)
//       }

//       myChart.setOption(this.fhrOption)
//       myChart2.setOption(this.ucOption)

//       myChart.on('brushselected', this.brushselected)
//       myChart2.on('brushselected', this.brushselected2)
//     },
//     brushselected(params) {
//        this.markForm.type = 'fhrForm'
//       if (params.batch[0].areas[0]) {
//         const coordX = params.batch[0].areas[0].coordRanges[0][0]
//         this.markForm.start = coordX[0].toFixed(2)
//         this.markForm.end = coordX[1].toFixed(2)
//       }
//     },
//       brushselected2(params) {
//         this.markForm.type = 'ucForm'
//       if (params.batch[0].areas[0]) {
//         const coordX = params.batch[0].areas[0].coordRanges[0][0]
//         this.markForm.start = coordX[0].toFixed(2)
//         this.markForm.end = coordX[1].toFixed(2)
//       }
//     },
//     getNST(row) {
//       return this.nstDictionary[row.nst]
//     },
//     getCheckPlace(row) {
//       return this.checkPlaceDictionary[row.checkPlace]
//     },
//     handleScroll() {
//       // 滚动至浏览器顶部固定
//       var scrollTop =
//         window.pageYOffset ||
//         document.documentElement.scrollTop ||
//         document.body.scrollTop
//       var offsetTop = document.querySelector('#elcollapse').offsetTop
//       scrollTop > offsetTop
//         ? document.getElementById('dqsj').classList.add('topFixed')
//         : document.getElementById('dqsj').classList.remove('topFixed')
//     },
//     handleAddMark() {
     
//       const formName = this.markForm.type
  
//       this.$refs['fhrForm'].validate((valid) => {
//         if (valid) {
//           let lineType = 1
//           if (formName !== 'fhrForm') {
//             lineType = 2
//           }
//           const markTypeMap = [
//             '加速',
//             '减速'
//           ]
       
      
//           const ctgCode = Number(this.currentRowArray[0].ctgCode)
//           const ctgNumber = Number(this.currentRowArray[0].ctgNumber)
//           const packageNumber = Number(this.currentRowArray[0].packageNumber)
//  console.log('packageNumber', this.currentRowArray[0].packageNumber)
//           const data = []
//           //  自动匹配纵坐标
//           const startX = this.markForm.start
//           const startY = this.getY(startX, formName)
//           const start = [startX, startY]

//           const endX = this.markForm.end
//           const endY = this.getY(endX, formName)
//           const end = [endX, endY]

//           const temp = [
//             {
//               name: markTypeMap[this.markForm.name - 1],
//               markType: this.markForm.name,
//               // coord: this.markForm.start.split(',')
//               coord: start
//             },
//             {
//               // coord: this.markForm.end.split(',')
//               coord: end
//             }
//           ]
//           data.push(JSON.stringify(temp))
//           const d = {
//             lineType,
//             ctgCode,
//             ctgNumber,
//             packageNumber,
//             json: data
//           }

//           if (this.marked(lineType)) {
//             const obj = {
//               id: null,
//               json: null
//             }
//             if (lineType === 1) {
//               this.markJson.fhr.push(temp)
//               const d = this.markJson.fhr

//               const arr = []
//               d.map(item => {
//                 arr.push(JSON.stringify(item))
//               })
//               obj.json = `[${arr.join()}]`
//               obj.id = this.fhrOption.id
//             } else {
//               this.markJson.uc.push(temp)
//               const d = this.markJson.uc
//               const arr = []
//               d.map(item => {
//                 arr.push(JSON.stringify(item))
//               })
//               obj.json = `[${arr.join()}]`
//               obj.id = this.ucOption.id
//             }

//             updateMark(obj).then(res => {
              
//               this.getDetail(packageNumber, ctgNumber, ctgCode)
//               this.$message({
//                 type: 'success',
//                 message: '添加成功！'
//               })
//               this.clearRect()
//               this.resetForm()
//             })
//           } else {
//             d.json = `[${d.json.join()}]`
//             addMark(d).then(res => {
//               this.getDetail(packageNumber, ctgNumber, ctgCode)
//               this.$message({
//                 type: 'success',
//                 message: '添加成功！'
//               })
//               this.clearRect()
//               this.resetForm()
//             })
//           }
//         } else {
//           return false
//         }
//       })
//     },
//     clearRect() {
//       var myChart = this.$echarts.init(document.getElementById('myChart'))
//       var myChart2 = this.$echarts.init(document.getElementById('myChart2'))
//       myChart.dispatchAction({
//         type: 'brush',
//         areas: []
//       })
//       myChart2.dispatchAction({
//         type: 'brush',
//         areas: []
//       })
//     },
//     resetForm() {
//       this.markForm.name = ''
//       this.markForm.start = ''
//       this.markForm.end = ''
//     },
//     arrToStr(objarr) {
//       var arrLen = objarr.length
//       var row = '['
//       for (var i = 0; i < arrLen; i++) {
//         row += '['
//         for (var j = 0; j < objarr[i].length; j++) {
//           row += objarr[i][j]
//           if (j < objarr[i].length - 1) {
//             row += ','
//           }
//         }
//         row += ']'
//         if (i < arrLen - 1) {
//           row += ','
//         }
//       }
//       row += ']'
//       return row
//     },
//     marked(type) {
//       let ret = false
//       if (type === 1) {
//         ret = this.fhrOption.id
//       } else {
//         ret = this.ucOption.id
//       }
//       return Boolean(ret)
//     }
//   }
// }
// </script>

// <style lang="scss" scoped>
// .dashboard-container {
//   padding: 16px;
//   width: 100%;
//   .ctg-wrapper {
//     .pagination-wrapper {
//       width: 100%;
//       text-align: right;
//     }
//   }
//   .judge-wrapper {
//     margin-top: 5px;
//     margin-bottom: 5px;
//   }
//   .chart-t-wrapper {
//     width: 100%;
//     // border: 1px solid #ddd;
//     .chart-box{
//       display:flex;
//       // justify-content:space-between;
//       .chart-left{
//         width:80%;
//         margin-right:15px;
//         #myChart,#myChart2 {
//                   width: 100%;
//                   margin-right:10px;
//                   height: 300px;
//               }
//       }
//     }
//   }
//   .chart-g-wrapper {
//     width: 100%;
//     // border: 1px solid #ddd;
//     border-top: none;
//     // #myChart2 {
//     //   width: 100%;
//     //   height: 300px;
//     // }
//   }
//   .mb-btn{
//     margin-bottom:6px;
//   }
// }
// </style>

// <style>
// .el-checkbox {
//   color: #606266;
//   margin-left: 5px;
//   margin-right: 5px;
//   line-height: 32px;
// }
// .fade-enter-active,
// .fade-leave-active {
//   transition: opacity 0.5s;
// }
// .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
//   opacity: 0;
// }
// .access {
//   display: flex;
//   align-items: flex-end;
// }
// .access-table {
//   flex: 1;
//   margin-left: 10px;
//   overflow-x: auto;
// }
// .topFixed {
//   position: fixed;
//   z-index: 999;
//   top: 0;
//   display: block;
//   width: 100%;
// }
// .el-collapse-item__content {
//   padding: 0;
// }
// </style>
