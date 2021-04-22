<template>
  <div>
    <el-dialog title="筛选CTG数据" :visible.sync="dialogFormVisible" :before-close="cancel">


    	<el-form :model="list" :inline="true" style="max-height:350px;overflow-y:scroll;" size="mini">

    		<el-form-item>
    			<el-col>
    				<searchItem
    				v-for="(item,index) in searchBox"
    				:key="index"
    				:index="index"
    				:items="item"
    				@valueReturn="valueReturn"
    				@deleteItem="deleteItem"
            ref="searchItem"
    				style="margin-bottom: 10px"
    				/>

    			</el-col>
    		</el-form-item>
    		<el-button 
    		icon="el-icon-circle-plus-outline"	
    		type="text"
    		@click="itemAdd" 
    		style="display: block; margin: auto;position: relative;top: -20px"
    		size="medium "
    		/>
        <div class="time-item">
          <span style="display: inline-block;margin-right: 10px;width: 72px;">check_date</span>
          <el-date-picker
          v-model="check_dateSE"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change='timeItem'
          size="mini"
          format="yyyy 年 MM 月 dd 日"
          value-format="yyyy-MM-dd"
          />          
        </div>

        <div class="time-item">
          <span style="display: inline-block;margin-right: 10px;width: 72px;">judge_date</span>
          <el-date-picker
          v-model="judge_dateSE"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change='timeItem'
          size="mini"
          format="yyyy 年 MM 月 dd 日"
          value-format="yyyy-MM-dd"
          />
          
        </div>

      <!--
        <el-form-item v-for="item in options" :key="item.label" :label="item.label" label-width="120px">
          <el-col :span="11">
            <el-input v-model="list[item.min]" placeholder="最小值" style="width: 100%;" />
          </el-col>
          <el-col class="line" :span="2" style="text-align:center;">-</el-col>
          <el-col :span="11">
            <el-input v-model="list[item.max]" placeholder="最大值" style="width: 100%;" />
          </el-col>
        </el-form-item>
        <el-form-item label="packageNumber" label-width="120px">
          <el-input v-model="list.packageNumber" />
        </el-form-item>
        <el-form-item label="ctgNumber" label-width="120px">
          <el-input v-model="list.ctgNumber" />
        </el-form-item>
        <el-form-item label="file_name" label-width="120px">
          <el-input v-model="list.file_name" />
        </el-form-item>

        <el-form-item label="check_place" label-width="120px">
          <el-input v-model="list.check_place" />
        </el-form-item>

        <el-form-item label="device_batch" label-width="120px">
          <el-input v-model="list.device_batch" />
        </el-form-item>

        <el-form-item label="device_brand" label-width="120px">
          <el-input v-model="list.device_brand" />
        </el-form-item>

        <el-form-item label="gravida_id" label-width="120px">
          <el-input v-model="list.gravida_id" />
        </el-form-item>
        <el-form-item label-width="120px">
          <el-checkbox v-model="list.nst_different">nst与医师结果不一致</el-checkbox>
          <el-checkbox v-model="list.nst_same">nst和医师结果一致</el-checkbox>
          <el-checkbox v-model="list.ys_different">医师和其他医师不同的</el-checkbox>
        </el-form-item> 
      -->
      <el-radio-group v-model="radio" style="margin: 30px 0 10px 0; display: flex; flex-wrap: wrap;">
        <el-radio :label="1" style="width: 50%;margin-right: 0px;">nst与医师结果不一致</el-radio>
        <el-radio :label="2" style="width: 50%;margin-right: 0px;">nst和医师结果一致</el-radio>
        <div style="flex: 1">
          <el-radio :label="3" style="margin-right: 0px;" >医师和其他医师不同的</el-radio>
          <el-button type="text" @click="doctorDialogVisible=true">进入详细筛选</el-button>

        </div>
        <el-radio :label="4" style="flex: 1;margin-top: 10px">不选择</el-radio>
      </el-radio-group>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="search">确 定</el-button>
    </div>
  </el-dialog>
  <el-dialog
  title="详细筛选"
  :visible.sync="doctorDialogVisible"
  width="30%"
  >
  <!-- <span>这是一段信息</span> -->
  <span slot="footer" class="dialog-footer">
    <el-button @click="doctorDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doctorDialogVisible = false">确 定</el-button>
  </span>
</el-dialog>

</div>
</template>

<script>

  import searchItem from "./searchItem";

  export default {
    props: {
      dialogFormVisible: {
        type: Boolean,
        default: false
      }
    },
    components: {
      searchItem
    },
    data() {
      return {
        radio: 4,
        searchBox:[{id:0}],
        searchNum:1,
        doctorDialogVisible:false, //医生详细筛选对话框
        options: [
        {
          label: 'dl',
          min: 'dlMin',
          max: 'dlMax'
        },
        {
          label: 'dp',
          min: 'dpMin',
          max: 'dpMax'
        },
        {
          label: 'ds',
          min: 'dsMin',
          max: 'dsMax'
        },
        {
          label: 'ed',
          min: 'edMin',
          max: 'edMax'
        },
        {
          label: 'fasttime',
          min: 'fasttimeMin',
          max: 'fasttimeMax'
        },
        {
          label: 'fastvalue',
          min: 'fastvalueMin',
          max: 'fastvalueMax'
        },
        {
          label: 'fhrcycle',
          min: 'fhrcycleMin',
          max: 'fhrcycleMax'
        },
        {
          label: 'fhrfast',
          min: 'fhrfastMin',
          max: 'fhrfastMax'
        },
        {
          label: 'fhrfast_evaluation_0',
          min: 'fhrfast_evaluation_0Min',
          max: 'fhrfast_evaluation_0Max'
        },
        {
          label: 'fhrfast_evaluation_1',
          min: 'fhrfast_evaluation_1Min',
          max: 'fhrfast_evaluation_1Max'
        },
        {
          label: 'fhrvar',
          min: 'fhrvarMin',
          max: 'fhrvarMax'
        },
        {
          label: 'fhslow_evaluation',
          min: 'fhslow_evaluationMin',
          max: 'fhslow_evaluationMax'
        },
        {
          label: 'fmove',
          min: 'fmoveMin',
          max: 'fmoveMax'
        },
        {
          label: 'frh_evaluation',
          min: 'frh_evaluationMin',
          max: 'frh_evaluationMax'
        },
        {
          label: 'haveaccdec',
          min: 'haveaccdecMin',
          max: 'haveaccdecMax'
        },
        {
          label: 'highltv',
          min: 'highltvMin',
          max: 'highltvMax'
        },
        {
          label: 'ld',
          min: 'ldMin',
          max: 'ldMax'
        },
        {
          label: 'lowltv',
          min: 'lowltvMin',
          max: 'lowltvMax'
        },
        {
          label: 'nst',
          min: 'nstMin',
          max: 'nstMax'
        },
        {
          label: 'nst_evaluation',
          min: 'nst_evaluationMin',
          max: 'nst_evaluationMax'
        },
        {
          label: 'stv',
          min: 'stvMin',
          max: 'stvMax'
        },
        {
          label: 'ucktime',
          min: 'ucktimeMin',
          max: 'ucktimeMax'
        },
        {
          label: 'ucnext',
          min: 'ucnextMin',
          max: 'ucnextMax'
        },
        {
          label: 'uctimes',
          min: 'uctimesMin',
          max: 'uctimesMax'
        },
        {
          label: 'var_evaluation',
          min: 'var_evaluationMin',
          max: 'var_evaluationMax'
        },
        {
          label: 'vd',
          min: 'vdMin',
          max: 'vdMax'
        },
        {
          label: 'vdtime',
          min: 'vdtimeMin',
          max: 'vdtimeMax'
        },
        {
          label: 'age',
          min: 'ageMin',
          max: 'ageMax'
        },
        {
          label: 'base_line',
          min: 'base_lineMin',
          max: 'base_lineMax'
        },
        {
          label: 'check_date',
          min: 'check_dateMin',
          max: 'check_dateMax'
        },
        {
          label: 'expert_read_begin',
          min: 'expert_read_beginMin',
          max: 'expert_read_beginMax'
        },
        {
          label: 'expert_read_end',
          min: 'expert_read_endMin',
          max: 'expert_read_endMax'
        },
        {
          label: 'gestationalAge',
          min: 'gestationalAgeMin',
          max: 'gestationalAgeMax'
        },
        {
          label: 'judge_date',
          min: 'judge_dateMin',
          max: 'judge_dateMax'
        },
        {
          label: 'lost_rate',
          min: 'lost_rateMin',
          max: 'lost_rateMax'
        },
        {
          label: 'up_time',
          min: 'up_timeMin',
          max: 'up_timeMax'
        },
        {
          label: 'watch_time',
          min: 'watch_timeMin',
          max: 'watch_timeMax'
        }

        ],
        list: {
          nst_different: 0,
          nst_same: 0,
          ys_different: 0,

          packageNumber: null,
          ctgNumber: null,

          dlMin: null,
          dlMax: null,

          dpMin: null,
          dpMax: null,

          dsMin: null,
          dsMax: null,

          edMin: null,
          edMax: null,

          fasttimeMin: null,
          fasttimeMax: null,

          fastvalueMin: null,
          fastvalueMax: null,

          fhrcycleMin: null,
          fhrcycleMax: null,

          fhrfastMin: null,
          fhrfastMax: null,

          fhrfast_evaluation_0Min: null,
          fhrfast_evaluation_0Max: null,

          fhrfast_evaluation_1Min: null,
          fhrfast_evaluation_1Max: null,

          fhrvarMin: null,
          fhrvarMax: null,

          fhslow_evaluationMin: null,
          fhslow_evaluationMax: null,

          fmoveMin: null,
          fmoveMax: null,

          frh_evaluationMin: null,
          frh_evaluationMax: null,

          haveaccdecMin: null,
          haveaccdecMax: null,

          highltvMin: null,
          highltvMax: null,

          ldMin: null,
          ldMax: null,

          lowltvMin: null,
          lowltvMax: null,

          nstMin: null,
          nstMax: null,

          nst_evaluationMin: null,
          nst_evaluationMax: null,

          stvMin: null,
          stvMax: null,

          ucktimeMin: null,
          ucktimeMax: null,

          ucnextMin: null,
          ucnextMax: null,

          uctimesMin: null,
          uctimesMax: null,

          var_evaluationMin: null,
          var_evaluationMax: null,

          vdMin: null,
          vdMax: null,

          vdtimeMin: null,
          vdtimeMax: null,

          ageMin: null,
          ageMax: null,

          base_lineMax: null,
          check_dateMin: null,

          check_dateMax: null,
          check_place: null,

          device_batch: null,
          device_brand: null,

          expert_read_beginMin: null,
          expert_read_beginMax: null,

          expert_read_endMin: null,
          expert_read_endMax: null,

          file_name: null,
          gestationalAgeMin: null,

          gestationalAgeMax: null,
          gravida_id: null,

          judge_dateMin: null,
          judge_dateMax: null,

          lost_rateMin: null,
          lost_rateMax: null,

          up_timeMin: null,
          up_timeMax: null,

          watch_timeMin: null,
          watch_timeMax: null
        },
        judge_dateSE:[],
        check_dateSE:[]
      }
    },
    methods: {
      search() {
        if (this.radio === 1) {
          this.list.nst_different = 1
          this.list.nst_same = 0
          this.list.ys_different = 0
        } else if (this.radio === 2) {
          this.list.nst_different = 0
          this.list.nst_same = 1
          this.list.ys_different = 0
        } else if (this.radio === 3) {
          this.list.nst_different = 0
          this.list.nst_same = 0
          this.list.ys_different = 1
        } else {
          this.list.nst_different = 0
          this.list.nst_same = 0
          this.list.ys_different = 0
        }
        this.$emit('search', this.list)
        this.radio = 4;
        this.searchBox=[]
        this.$refs.searchItem[0].cleanValue('调用子组件方法')
        this.searchBox=[{id:0}]
        this.list = {
          nst_different: 0,
          nst_same: 0,
          ys_different: 0,

          packageNumber: null,
          ctgNumber: null,

          dlMin: null,
          dlMax: null,

          dpMin: null,
          dpMax: null,

          dsMin: null,
          dsMax: null,

          edMin: null,
          edMax: null,

          fasttimeMin: null,
          fasttimeMax: null,

          fastvalueMin: null,
          fastvalueMax: null,

          fhrcycleMin: null,
          fhrcycleMax: null,

          fhrfastMin: null,
          fhrfastMax: null,

          fhrfast_evaluation_0Min: null,
          fhrfast_evaluation_0Max: null,

          fhrfast_evaluation_1Min: null,
          fhrfast_evaluation_1Max: null,

          fhrvarMin: null,
          fhrvarMax: null,

          fhslow_evaluationMin: null,
          fhslow_evaluationMax: null,

          fmoveMin: null,
          fmoveMax: null,

          frh_evaluationMin: null,
          frh_evaluationMax: null,

          haveaccdecMin: null,
          haveaccdecMax: null,

          highltvMin: null,
          highltvMax: null,

          ldMin: null,
          ldMax: null,

          lowltvMin: null,
          lowltvMax: null,

          nstMin: null,
          nstMax: null,

          nst_evaluationMin: null,
          nst_evaluationMax: null,

          stvMin: null,
          stvMax: null,

          ucktimeMin: null,
          ucktimeMax: null,

          ucnextMin: null,
          ucnextMax: null,

          uctimesMin: null,
          uctimesMax: null,

          var_evaluationMin: null,
          var_evaluationMax: null,

          vdMin: null,
          vdMax: null,

          vdtimeMin: null,
          vdtimeMax: null,

          ageMin: null,
          ageMax: null,

          base_lineMax: null,
          check_dateMin: null,

          check_dateMax: null,
          check_place: null,

          device_batch: null,
          device_brand: null,

          expert_read_beginMin: null,
          expert_read_beginMax: null,

          expert_read_endMin: null,
          expert_read_endMax: null,

          file_name: null,
          gestationalAgeMin: null,

          gestationalAgeMax: null,
          gravida_id: null,

          judge_dateMin: null,
          judge_dateMax: null,

          lost_rateMin: null,
          lost_rateMax: null,

          up_timeMin: null,
          up_timeMax: null,

          watch_timeMin: null,
          watch_timeMax: null
        }
        this.judge_dateSE=[];
        this.check_dateSE=[]
      },
      cancel() {
        this.$emit('cancel')
      },
    valueReturn(data){  //获取组件值并赋值
    	var isEqual=data.isEqual
    	var value=data.value
    	var search=data.search
    	var searchBp=data.searchBp
    	if(isEqual){
    		this.list[search]=value
    		this.list[searchBp]=value
    	}
    	else{
    		this.list[search]=value
    	}
    },
    itemAdd(){  //添加搜索条件
     this.searchBox.push({
      id:this.searchNum
    })
     this.searchNum++;
   },
   deleteItem(data,index){
     this.valueReturn(data)
     if (this.searchBox.length > 1) {
      this.searchBox.splice(index,1)
    }
  },
  timeItem(){
    if (this.judge_dateSE!=null&&this.judge_dateSE.length) {
      let timeEnd=new Date(this.judge_dateSE[1])   //时间最大边界加一天
      timeEnd=timeEnd.setDate(timeEnd.getDate()+1)
      timeEnd=new Date(timeEnd)
      let time=''
      let year = timeEnd.getFullYear()
      let month = timeEnd.getMonth() + 1
      let day = timeEnd.getDate()
      time=year+'-'+month+'-'+day

      this.list['judge_dateMin']=this.judge_dateSE[0]
      this.list['judge_dateMax']=time
      // this.list['judge_dateMax']=this.judge_dateSE[1]
    }
    else{
      this.list['judge_dateMin']=null
      this.list['judge_dateMax']=null      
    }
    if(this.check_dateSE!=null&&this.check_dateSE.length){
      let timeEnd=new Date(this.check_dateSE[1])
      timeEnd=timeEnd.setDate(timeEnd.getDate()+1)
      timeEnd=new Date(timeEnd)
      let time=''
      let year = timeEnd.getFullYear()
      let month = timeEnd.getMonth() + 1
      let day = timeEnd.getDate()
      time=year+'-'+month+'-'+day

      this.list['check_dateMin']=this.check_dateSE[0]
      this.list['check_dateMax']=time
    }
    else{
      this.list['check_dateMin']=null
      this.list['check_dateMax']=null
    }

  }
}
}
</script>
<style >
.time-item{
  margin-bottom: 20px;
}
</style>
