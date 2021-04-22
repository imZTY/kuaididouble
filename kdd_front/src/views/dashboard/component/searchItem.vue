<template>
	<div style="display: flex;">
	  <el-select 
	  	v-model="searchValue" 
	  	value-key="id" 
	  	placeholder="请选择" 
	    @change="optionChange"
	    style="flex:1;margin-right: 10px;"
	  >
	    <el-option
	      v-for="item in options"
	      :key="item.id"
	      :label="item.label"
	      :value="item"
	     />
	  </el-select>
	  <el-select
	  	v-model="limitValue" 
	  	placeholder="请选择" 
	    style="flex:1;margin-right: 10px"
	  	>
	    <el-option
	      v-for="item in limit"
	      :key="item.label"
	      :label="item.label"
	      :value="item.value"
	      :disabled="item.disabled"
	     />
	  </el-select>
	  <el-input 
	  	v-model="value" 
	  	:placeholder="placeholder" 
	  	minlength='120px'
	  	maxlength='120px'
	  	style="flex:1"
	  	@change="valueReturn"
		/>
		<div style="flex-direction: column;">

<!-- 			<el-button 
				icon="el-icon-circle-check"	
				type="text" 
				@click="valueReturn"
	  		style="flex:1"
			/>
 -->			
 			<el-button 
				icon="el-icon-remove-outline"	
				type="text"
				@click="deleteItem"
	  		style="flex:1"
			/>

		</div>

	 </div>
</template>
<script>
	export default {
		name: 'searchItem',
		props: ['index'],
		data(){
			return{
				limitValue:null,
				searchValue:null,
				value:null,
				placeholder:'',
				returnValue:{  //搜索结果
					value:null,
					search:'',
					isEqual:false,
					searchBp:null
				},
				limit:[	//条件选择项
					{
						label:'≥',
						value:1,
						disabled:false,
					},
					{
						label:'=',
						value:0
					},
					{
						label:'≤',
						value:-1,
						disabled:false,
					} 
				],
				options: [  //属性选择项
	        {
	          label: 'dl',
	          min: 'dlMin',
	          max: 'dlMax',
	          id:0,
	          placeholder:'0'
	        },
	        {
	          label: 'dp',
	          min: 'dpMin',
	          max: 'dpMax',
	          id:1,
	          placeholder:'0'
	        },
	        {
	          label: 'ds',
	          min: 'dsMin',
	          max: 'dsMax',
	          id:2,
	          placeholder:'0'
	        },
	        {
	          label: 'ed',
	          min: 'edMin',
	          max: 'edMax',
	          id:3,
	          placeholder:'0'
	        },
	        {
	          label: 'fasttime',
	          min: 'fasttimeMin',
	          max: 'fasttimeMax',
	          id:4,
	          placeholder:'0'
	        },
	        {
	          label: 'fastvalue',
	          min: 'fastvalueMin',
	          max: 'fastvalueMax',
	          id:5,
	          placeholder:'0'
	        },
	        {
	          label: 'fhrcycle',
	          min: 'fhrcycleMin',
	          max: 'fhrcycleMax',
	          id:6,
	          placeholder:'0'
	        },
	        {
	          label: 'fhrfast',
	          min: 'fhrfastMin',
	          max: 'fhrfastMax',
	          id:7,
	          placeholder:'0'
	        },
	        {
	          label: 'fhrfast_evaluation_0',
	          min: 'fhrfast_evaluation_0Min',
	          max: 'fhrfast_evaluation_0Max',
	          id:8,
	          placeholder:'0'
	        },
	        {
	          label: 'fhrfast_evaluation_1',
	          min: 'fhrfast_evaluation_1Min',
	          max: 'fhrfast_evaluation_1Max',
	          id:9,
	          placeholder:'0'
	        },
	        {
	          label: 'fhrvar',
	          min: 'fhrvarMin',
	          max: 'fhrvarMax',
	          id:10,
	          placeholder:'0'
	        },
	        {
	          label: 'fhslow_evaluation',
	          min: 'fhslow_evaluationMin',
	          max: 'fhslow_evaluationMax',
	          id:11,
	          placeholder:'0'
	        },
	        {
	          label: 'fmove',
	          min: 'fmoveMin',
	          max: 'fmoveMax',
	          id:12,
	          placeholder:'0'
	        },
	        {
	          label: 'frh_evaluation',
	          min: 'frh_evaluationMin',
	          max: 'frh_evaluationMax',
	          id:13,
	          placeholder:'0'
	        },
	        {
	          label: 'haveaccdec',
	          min: 'haveaccdecMin',
	          max: 'haveaccdecMax',
	          id:14,
	          placeholder:'0'
	        },
	        {
	          label: 'highltv',
	          min: 'highltvMin',
	          max: 'highltvMax',
	          id:15,
	          placeholder:'0'
	        },
	        {
	          label: 'ld',
	          min: 'ldMin',
	          max: 'ldMax',
	          id:16,
	          placeholder:'0'
	        },
	        {
	          label: 'lowltv',
	          min: 'lowltvMin',
	          max: 'lowltvMax',
	          id:17,
	          placeholder:'0'
	        },
	        {
	          label: 'nst',
	          min: 'nstMin',
	          max: 'nstMax',
	          id:18,
	          placeholder:'0'
	        },
	        {
	          label: 'nst_evaluation',
	          min: 'nst_evaluationMin',
	          max: 'nst_evaluationMax',
	          id:19,
	          placeholder:'0.00'
	        },
	        {
	          label: 'stv',
	          min: 'stvMin',
	          max: 'stvMax',
	          id:20,
	          placeholder:'0'
	        },
	        {
	          label: 'ucktime',
	          min: 'ucktimeMin',
	          max: 'ucktimeMax',
	          id:21,
	          placeholder:'0'
	        },
	        {
	          label: 'ucnext',
	          min: 'ucnextMin',
	          max: 'ucnextMax',
	          id:22,
	          placeholder:'0'
	        },
	        {
	          label: 'uctimes',
	          min: 'uctimesMin',
	          max: 'uctimesMax',
	          id:23,
	          placeholder:'0'
	        },
	        {
	          label: 'var_evaluation',
	          min: 'var_evaluationMin',
	          max: 'var_evaluationMax',
	          id:24,
	          placeholder:'0'
	        },
	        {
	          label: 'vd',
	          min: 'vdMin',
	          max: 'vdMax',
	          id:25,
	          placeholder:'0'
	        },
	        {
	          label: 'vdtime',
	          min: 'vdtimeMin',
	          max: 'vdtimeMax',
	          id:26,
	          placeholder:'0'
	        },
	        {
	          label: 'age',
	          min: 'ageMin',
	          max: 'ageMax',
	          id:27,
	          placeholder:'0'
	        },
	        {
	          label: 'base_line',
	          min: 'base_lineMin',
	          max: 'base_lineMax',
	          id:28,
	          placeholder:'0'
	        },
	        // {
	        //   label: 'check_date',
	        //   min: 'check_dateMin',
	        //   max: 'check_dateMax',
	        //   id:29
	        // },
	        {
	          label: 'expert_read_begin',
	          min: 'expert_read_beginMin',
	          max: 'expert_read_beginMax',
	          id:30,
	          placeholder:'0'
	        },
	        {
	          label: 'expert_read_end',
	          min: 'expert_read_endMin',
	          max: 'expert_read_endMax',
	          id:31,
	          placeholder:'0'
	        },
	        {
	          label: 'gestationalAge',
	          min: 'gestationalAgeMin',
	          max: 'gestationalAgeMax',
	          id:32,
	          placeholder:'0.00'
	        },
	        // {
	        //   label: 'judge_date',
	        //   min: 'judge_dateMin',
	        //   max: 'judge_dateMax',
	        //   id:33
	        // },
	        {
	          label: 'lost_rate',
	          min: 'lost_rateMin',
	          max: 'lost_rateMax',
	          id:34,
	          placeholder:'0'
	        },
	        {
	          label: 'up_time',
	          min: 'up_timeMin',
	          max: 'up_timeMax',
	          id:35,
	          placeholder:'0'
	        },
	        {
	          label: 'watch_time',
	          min: 'watch_timeMin',
	          max: 'watch_timeMax',
	          id:36,
	          placeholder:'0'
	        },  

	        {
	          label: 'packageNumber',
	          min: 'packageNumber',
	          max: 'packageNumber',
	          id:37,
	          placeholder:'0'
	        },  

	        {
	          label: 'ctgNumber',
	          min: 'ctgNumber',
	          max: 'ctgNumber',
	          id:38,
	          placeholder:'0'
	        },  

	        {
	          label: 'file_name',
	          min: 'file_name',
	          max: 'file_name',
	          id:39,
	          placeholder:'0'
	        },  

	        {
	          label: 'check_place',
	          min: 'check_place',
	          max: 'check_place',
	          id:40,
	          placeholder:'0'
	        },  

	        {
	          label: 'device_batch',
	          min: 'device_batch',
	          max: 'device_batch',
	          id:41,
	          placeholder:'0'
	        },  

	        {
	          label: 'device_brand',
	          min: 'device_brand',
	          max: 'device_brand',
	          id:42,
	          placeholder:'三瑞'
	        },  

	        {
	          label: 'gravida_id',
	          min: 'gravida_id',
	          max: 'gravida_id',
	          id:43,
	          placeholder:'0'
	        },  

	      ],
	 
			}
		},
		methods:{
			valueReturn(){ //传递至search组件
				this.returnValue.value=this.value
				if((this.limitValue||this.searchValue||this.value)==null){
				}
				else{
					if(this.limitValue>0){
						this.returnValue.search=this.searchValue.min
					}
					else if(this.limitValue<0){
						this.returnValue.search=this.searchValue.max
					}
					else{
						this.returnValue.isEqual=true
						this.returnValue.search=this.searchValue.max
						this.returnValue.searchBp=this.searchValue.min
					}
	      	this.$emit('valueReturn', this.returnValue)
				}
			},
			// cleanItem(){
			// 	this.returnValue.value=null

			// 	this.$emit('cleanItem', this.returnValue)
			// },
			deleteItem(e){
				this.returnValue.value=null
				this.$emit('deleteItem', this.returnValue,this.index)
				// this.value=null
			},
			cleanValue(e){  //搜索成功后清除第一行数据
				this.limitValue=null
				this.searchValue=null
				this.value=null
			},
			optionChange(e){  //控制大于小于
				if(e!=null){
					if(this.searchValue!=null){
						this.placeholder=this.searchValue.placeholder
					}
					if(
						e.label=='packageNumber'||
						e.label=='ctgNumber'||
						e.label=='file_name'||
						e.label=='check_place'||
						e.label=='device_batch'||
						e.label=='device_brand'||
						e.label=='gravida_id'
						){
						this.limit[0].disabled=true
					this.limit[2].disabled=true
				}
				else{
					this.limit[0].disabled=false
					this.limit[2].disabled=false
				}
			}
		},
		}
	}
</script>
<style>
</style>