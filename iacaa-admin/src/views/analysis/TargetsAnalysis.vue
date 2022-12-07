<template>
  <div>
    <el-form :inline="true" :model="selectGrade" class="demo-form-inline" style="height: 100px;padding-top: 20px">
      <el-form-item>
        选择统计年级
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="selectGrade"
          placeholder="选择年级"
          @change="getGrade($event)"
        >
          <el-option
            v-for="item in gradeList"
            :key="item.grade"
            :label="item.grade"
            :value="{cultivationId: item.cultivationId, grade:item.grade }"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div class="historyLabel">
      <span style="float: right;margin-right: 180px">
        <el-button type="primary" @click="getGrade">同步实时数据</el-button>
      </span>
        <div class="elinkScatterBar">
          <div id="elinkScatterPlot" class="elinkScatterPlot"></div>
        </div>
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import {requestByClient} from '@/utils/HttpUtils'
import {supplierConsumer} from '@/utils/HttpUtils'
import {Loading} from 'element-ui'

export default {
  name: "TargetsAnalysis",
  data() {
    return {
      gradeList:[],
      selectGrade:null,
      selectCultivationId:null,
      evaluateList:null,
      attributeList:[],
      serchForm: {
        id: '',
        word:'',
        reqId:''
      },
      options: [],
      dialogVisible: false,
      targetChartForm: {
        title: '',
        score: '',
        courseTargets: [],
        courseObjectives: []
      },
      reqs:[],
      lastFiveYears: [],
      viewingTargetsList: []
    }
  },
  watch: {
    '$store.state.settings.editYear': 'getList'
  },
  mounted() {
    this.getList()
    this.setCheckLinkScoreBar()
  },
  methods: {
    getGrade(event){
      // eslint-disable-next-line no-console
      console.log('trans parameters')
      // eslint-disable-next-line no-console
      console.log(event)
      this.selectCultivationId=event.cultivationId

      requestByClient(supplierConsumer, 'POST', 'target/listEvaluate', {
        cultivationId: this.selectCultivationId
      }, res => {
        if (res.data.succ) {
          this.evaluateList=res.data.data
          // eslint-disable-next-line no-console
          console.log('listEvaluate')
          // eslint-disable-next-line no-console
          console.log(this.evaluateList)
          this.setCheckLinkScoreBar()
        }
      })
    },
    getList() {
      this.dialogVisible = false
      requestByClient(supplierConsumer, 'POST', 'student/listGrade', { }, res => {
        if (res.data.succ) {
          this.gradeList = res.data.data
          // eslint-disable-next-line no-console
          console.log('get student grade info')
          // eslint-disable-next-line no-console
          console.log(res.data.data)
        }
      })
      requestByClient(supplierConsumer, 'POST', 'target/list', {
        year: this.$store.state.settings.editYear,
        id: this.serchForm.id,
        word: this.serchForm.word,
        reqId: this.serchForm.reqId
      }, res => {
        if (res.data.succ) {
          this.viewingTargetsList = res.data.data
          this.setChartData()
        }
        this.loading = false
      })
    },

    open() {
      this.$nextTick(() => {
        this.setCheckLinkScoreBar()
      })
    },
    setCheckLinkScoreBar() {
      let chartDom = document.getElementById('elinkScatterPlot');
      let myChart = echarts.init(chartDom);
      let option;
      let max_value=[]
      let avg_value=[]
      let min_value=[]
      let indicator_radar=[];
      this.evaluateList.forEach(item=>{
        let string=item.attributeId.toString()+'.'+item.subAttributeId.toString()+' '
        let name={
          name: string,
          max: 1.00
        }
        indicator_radar.push(name)
        max_value.push(item.max)
        avg_value.push(item.avg)
        min_value.push(item.min)
      })
      // eslint-disable-next-line no-console
      console.log('echarts data')
      // eslint-disable-next-line no-console
      console.log(max_value)
      // eslint-disable-next-line no-console
      console.log(avg_value)
      // eslint-disable-next-line no-console
      console.log(min_value)
      // eslint-disable-next-line no-console
      console.log(indicator_radar)
      option = {
        //backgroundColor: 'rgba(204,204,204,0.7)',  //配置背景色，默认无背景
        title: {  //配置标题组件
          text: '各教育阶段男女人数统计',
          target: 'blank', top: '10', left: '160', textStyle: { color: 'blue', fontSize: 18, }
        },
        legend: {  //配置图例组件
          show: true,  //设置是否显示图例
          icon: 'rect',  //icon.'circle'|'rect'|'roundRect'|'triangle'|'diamond'|'pin'|'arrow'|'none'
          top: '14',  //设置图例距离顶部边距
          left: 430,  //设置图例距离左侧边距
          itemWidth: 10,  //设置图例标记的图形宽度
          itemHeight: 10,  //设置图例标记的图形高度
          itemGap: 30,  //设置图例每项之间的间隔
          orient: 'horizontal',  //设置图例列表的布局朝向，'horizontal'|'vertical'
          textStyle: { fontSize: 15, color: '#fff' }, //设置图例的公用文本样式
          data: [  //设置图例的数据数组，数组项通常为字符串，每项代表一个系列name
            {
              name: '最高值', icon: 'rect',
              textStyle: { color: 'rgba(51,0,255,1)', fontWeight: 'bold' }
            },  //设置图例项的文本样式
            {
              name: '最低值', icon: 'rect',
              textStyle: { color: 'rgba(255,0,0,1)', fontWeight: 'bold' }
            }  //'normal'|'bold'|'bolder'|'lighter'
            ,  //设置图例项的文本样式
            {
              name: '平均值', icon: 'rect',
              textStyle: { color: 'rgba(0,0,0,1)', fontWeight: 'bold' }
            }
          ],
        },
        tooltip: {  //配置详情提示框组件
          //设置雷达图的tooltip不会超出div，也可设置position属性
          //设置定位不会随着鼠标移动而变化
          confine: true,  //设置是否将tooltip框限制在图表的区域内
          enterable: true,  //设置鼠标是否可以移动到tooltip区域内
        },
        radar: [{  //配置雷达图坐标系组件，只适用于雷达图
          center: ['50%', '56%'],  //设置中心坐标，数组的第1项是横坐标，第2项是纵坐标
          radius: 160,  //设置圆的半径，数组的第一项是内半径，第二项是外半径
          startAngle: 90,  //设置坐标系起始角度，也就是第一个指示器轴的角度
          name: {  //设置（圆外的标签）雷达图每个指示器名称
            formatter: '{value}',
            textStyle: { fontSize: 15, color: '#000' }
          },
          nameGap: 2,  //设置指示器名称和指示器轴的距离，默认为15
          splitNumber: 2,  //设置指示器轴的分割段数，default
          //shape:'circle',  //设置雷达图绘制类型，支持'polygon','circle'
          //设置坐标轴轴线设置
          axisLine: { lineStyle: { color: '#fff', width: 1, type: 'solid', } },
          //设置坐标轴在grid区域中的分隔线
          splitLine: { lineStyle: { color: '#fff', width: 1, } },
          splitArea: {
            show: true,
            areaStyle: { color: ['#abc', '#abc', '#abc', '#abc'] }
          },  //设置分隔区域的样式
          indicator: indicator_radar

        }],
        series: [{
          name: '雷达图',  //系列名称，用于tooltip的显示，图例筛选
          type: 'radar',  //系列类型: 雷达图
          //拐点样式，'circle'|'rect'|'roundRect'|'triangle'|'diamond'|'pin'|'arrow'|'none'
          symbol: 'triangle',
          itemStyle: {  //设置折线拐点标志的样式
            normal: { lineStyle: { width: 1 }, opacity: 0.2 },  //设置普通状态时的样式
            emphasis: { lineStyle: { width: 5 }, opacity: 1 }  //设置高亮时的样式
          },
          data: [  //设置雷达图的数据是多变量(维度)
            {   //设置第1个数据项
              name: '最高值',  //数据项名称
              value: max_value,  //value是具体数据
              symbol: 'triangle',
              symbolSize: 5,  //设置单个数据标记的大小
              //设置拐点标志样式
              itemStyle: { normal: { borderColor: 'blue', borderWidth: 3 } },
              //设置单项线条样式
              lineStyle: { normal: { color: 'red', width: 1, opacity: 0.9 } },
              //areaStyle: {normal:{color:'red'}}  //设置单项区域填充样式
            },
            {   //设置第2个数据项
              name: '最低值',
              value: min_value,
              symbol: 'circle',
              symbolSize: 5,  //设置单个数据标记的大小
              itemStyle: { normal: { borderColor: 'rgba(51,0,255,1)', borderWidth: 3, } },
              lineStyle: { normal: { color: 'blue', width: 1, opacity: 0.9 } },
              //areaStyle:{normal:{color:'blue'}}  //设置单项区域填充样式
            },
            {   //设置第2个数据项
              name: '平均值',
              value: avg_value,
              symbol: 'circle',
              symbolSize: 5,  //设置单个数据标记的大小
              itemStyle: { normal: { borderColor: 'rgba(51,0,255,1)', borderWidth: 3, } },
              lineStyle: { normal: { color: 'blue', width: 1, opacity: 0.9 } },
              //areaStyle:{normal:{color:'blue'}}  //设置单项区域填充样式
            }
          ]
        },]
      }
      myChart.setOption(option);
    },

    setChartData() {
      let vue = this
      let data = this.viewingTargetsList
      let names = data.map(i => {
        return i.discribe
      })
      let stuGrades = data.map(i => {
        return (i.stuGrade*100).toFixed(2)
      })
      let sysGrades = data.map(i => {
        return (i.sysGrade*100).toFixed(2)
      })
      const chartDom = document.getElementById('historyData')
      const myChart = echarts.init(chartDom)
      let option
      option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        dataZoom: [
          {
            id: 'dataZoomX',
            type: 'slider',
            xAxisIndex: [0],
            filterMode: 'filter'
          }
        ],
        toolbox: {
          show: true,
          feature: {
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        calculable: true,
        title: {
          text: '指标点成绩对比分析',
          subtext: ''
        },
        xAxis: {
          type: 'category',
          data: names,
          axisLabel: {
            interval: 0,
            rotate: 90
          }
        },
        yAxis: {
          type: 'value',
          max: 100
        },
        series: [{
          name: '系统成绩',
          data: sysGrades,
          type: 'bar',
          barGap: 0,
          itemStyle: {
            normal: {
              color: '#ba0028'
            }
          },
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180,180,180,0.2)'
          },
          markPoint: {
            data: [
              {type: 'max', name: '最大值'}
            ]
          },
          markLine: {
            data: [
              {type: 'average', name: '平均值'}
            ]
          }
        }, {
          name: '学生评价',
          data: stuGrades,
          type: 'bar',
          itemStyle: {
            normal: {
              color: '#00216c'
            }
          },
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180,180,180,0.2)'
          },
          markPoint: {
            data: [
              {type: 'max', name: '最大值'}
            ]
          },
          markLine: {
            data: [
              {type: 'average', name: '平均值'}
            ]
          }
        }]
      }
      option && myChart.setOption(option)
      //点击事件
      myChart.on('click', function (params) {
        vue.handleChange(vue.viewingTargetsList[params.dataIndex].id)
      });
    },
    open2() {
      this.$nextTick(() => {
        this.setTargetChart()
        this.setTargetChartBar()
      })
    },
    handleChange(value) {
      requestByClient(supplierConsumer, 'POST', 'target/getOne', {
        id: value
      }, res => {
        if (res.data.succ) {
          if (res.data.data.sysGrade > 0) {
            this.targetChartForm.title = res.data.data.discribe + '(' + (res.data.data.sysGrade * 100).toFixed(2) + ')'
            this.targetChartForm.score = res.data.data.sysGrade
            requestByClient(supplierConsumer, 'POST', 'courseTarget/voList', {
              targetId: value
            }, res => {
              if (res.data.succ) {
                this.targetChartForm.courseTargets = res.data.data
                requestByClient(supplierConsumer, 'POST', 'courseObjective/voList', {
                  targetId: value,
                }, res => {
                  if (res.data.succ) {
                    this.targetChartForm.courseObjectives = res.data.data
                    this.dialogVisible = true
                  }
                })
              }
            })
          } else {
            this.$message({
              message: '该指标点暂无成绩',
              type: 'warning'
            })
            return false
          }
        }
      })

    },
    setTargetChartBar() {
      let chartDom = document.getElementById('targetBar');
      const myChart = echarts.init(chartDom)
      let option
      let courseObjectives = this.targetChartForm.courseObjectives
      let colors = [
        '#199237',
        '#196292',
        '#c11a9d',
        '#e5da14',
        '#b89220',
        '#1c977a',
        '#9a5a2b',
      ]
      let tasksName = courseObjectives.map(i => {
        return i.course.name + ':' + i.describes
      })

      let tasksScores = courseObjectives.map(i => {
        return (i.sysGrade * 100).toFixed(2)
      })

      let stuScores = courseObjectives.map(i => {
        return (i.stuGrade * 100).toFixed(2)
      })

      option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        dataZoom: [
          {
            id: 'dataZoomX',
            type: 'slider',
            xAxisIndex: [0],
            filterMode: 'filter'
          }
        ],
        calculable: true,
        title: {
          text: '其支撑课程目标',
          subtext: ''
        },
        xAxis: {
          type: 'category',
          data: tasksName,
          axisLabel: {
            interval: 0,
            rotate: 90
          }
        },
        yAxis: {
          type: 'value',
          max: 100
        },
        series: [{
          name: '系统成绩',
          data: tasksScores,
          type: 'bar',
          barGap: 0,
          itemStyle: {
            normal: {
              color: '#b60092'
            }
          },
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180,180,180,0.2)'
          }
        },{
          name: '学生评价',
          data: stuScores,
          type: 'bar',
          itemStyle: {
            normal: {
              color: '#0551bf'
            }
          },
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180,180,180,0.2)'
          }
        }]
      }
      option && myChart.setOption(option)
    },
    setTargetChart() {
      let chartDom = document.getElementById('targetPie');
      let myChart = echarts.init(chartDom);
      let option;
      let courseTargets = this.targetChartForm.courseTargets
      let dtataNames = []
      let chartData = new Array(courseTargets.length)
      for (let courseTarget of courseTargets) {
        dtataNames.push(courseTarget.course.name)
        chartData.push({
          value: courseTarget.mix.toFixed(2),
          name: courseTarget.course.name
        })
      }
      let courseObjectives = this.targetChartForm.courseObjectives
      let tasksDta = new Array(courseObjectives.length)
      for (let courseObjective of courseObjectives) {
        dtataNames.push(courseObjective.describes)
        let courseTaskMix = 0
        for (let courseTarget of courseTargets) {
          if (courseObjective.course.id === courseTarget.course.id) {
            courseTaskMix = courseObjective.mix * courseTarget.mix
          }
        }
        tasksDta.push({
          value: courseTaskMix.toFixed(2),
          name: courseObjective.describes
        })
      }
      option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} <br/>权重: {c} <br/>占比:({d}%)'
        },
        legend: {
          data: dtataNames,
          top: 'bottom'
        },
        series: [
          {
            name: '课程支撑权重',
            type: 'pie',
            selectedMode: 'single',
            radius: ['5%', '30%'],
            label: {
              position: 'inner',
              fontSize: 14,
            },
            labelLine: {
              show: false
            },
            data: chartData
          },
          {
            name: '课程目标',
            type: 'pie',
            radius: ['45%', '60%'],
            labelLine: {
              length: 30,
            },
            label: {
              formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
              backgroundColor: '#F6F8FC',
              borderColor: '#8C8D8E',
              borderWidth: 1,
              borderRadius: 4,

              rich: {
                a: {
                  color: '#6E7079',
                  lineHeight: 22,
                  align: 'center'
                },
                hr: {
                  borderColor: '#8C8D8E',
                  width: '100%',
                  borderWidth: 1,
                  height: 0
                },
                b: {
                  color: '#4C5058',
                  fontSize: 14,
                  fontWeight: 'bold',
                  lineHeight: 33
                },
                per: {
                  color: '#fff',
                  backgroundColor: '#4C5058',
                  padding: [3, 4],
                  borderRadius: 4
                }
              }
            },
            data: tasksDta
          }
        ]
      };

      option && myChart.setOption(option)
    },
    refreshData() {
      const loadingInstance = Loading.service({
        background: 'rgba(0, 0, 0, 0.7)',
        text: '加载中，请稍后。。。',
        target: 'document.body',
        body: true
      })
      requestByClient(supplierConsumer, 'POST', 'target/summaryAll', {
        year: 2021
      }, res => {
        if (res.data.succ) {
          this.$message({
            message: '数据已刷新',
            type: 'success'
          })
          this.getList()
          // 关闭加载动画
          this.$nextTick(() => {
            loadingInstance.close()
          })
        }
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
.historyLabel {
  padding: 20px;
  margin: 20px;
  width: 97%;
  height: 830px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .05)
}
.elinkScatterBar {
  width: 100%;
  height: 400px;
}

.elinkScatterPlot {
  width: 100%;
  height: 100%;
}
.historyCanvas {
  width: 100%;
  height: 700px;
  padding: 0;
  margin: 0;
}

.targetPie {
  width: 1300px;
  height: 750px;
  padding: 10px;
  display: inline-block;
}

.targetBar {
  margin-left: 50px;
  width: 400px;
  height: 750px;
  padding: 10px;
  display: inline-block;
}
</style>

