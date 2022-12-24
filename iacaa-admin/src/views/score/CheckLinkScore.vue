<template>
  <div style="padding: 25px">
    <el-form :inline="true" :model="searchForm" class="demo-form-inline" style="height: 50px">
      <el-form-item label="">
        <el-input v-model="searchForm.word" placeholder="课程名称" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getCourseList">查询</el-button>
      </el-form-item>
        <el-form-item>
          选择学期
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="selectSemester"
            placeholder="选择学期"
            @change="getSemesterList()"
          >
            <el-option
              v-for="item in semesterList"
              :key="item.semesterId"
              :label="item.semesterDescribe"
              :value="item.semesterId"
            />
          </el-select>
        </el-form-item>
    </el-form>

    <el-table
      ref="multipleTable"
      :data="courses"
      style="width: 100%"
      height="750"
      tooltip-effect="dark"
    >
      <el-table-column
        prop="id"
        label="课程编号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="name"
        label="名称"
        width="200">
      </el-table-column>
      <el-table-column
        prop=""
        label="操作"
        width="">
        <template slot-scope="courseScope">
          <el-button type="primary" @click="openScoreEditor(courseScope.row)">成绩管理</el-button>
          <el-button icon="" type="primary" @click="editReport(courseScope.row)" :disabled="reportButtonSituation">查看/打印达成评价报告</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 成绩管理页面 -->
    <el-dialog
      :visible.sync="dialogVisible_editScore"
      :close-on-click-modal="false"
      :title="'《'+viewingCourse.name+'》 “'+semesterDescribe+'” 课程成绩管理'"
      width="90%"
      center>
      <el-form>
        <el-form-item
        >
          <!-- el-button icon="el-icon-download" type="primary" @click="exportTemplate(viewingCourse)">成绩导入模板</el-button -->
          <el-button
            icon="el-icon-upload"
            type="primary"
            @click="clickUploadButton"
          >导入教务系统成绩<input
            type="file"
            style="display: none;"
            id="uploadScore"
            ref="files"
            @change="readExcel"
          /></el-button
          >
          <el-button icon="" type="warning" @click="calculate(viewingCourse) " :disabled="calculateButtonSituation">计算达成度</el-button>
          <el-button icon="" type="primary" @click="editReport(viewingCourse)" :disabled="reportButtonSituation">撰写达成评价报告</el-button>
        </el-form-item>
      </el-form>
      <div>
        {{viewingCourse.name}}  {{ semesterDescribe }}
      </div>
      <el-table
        ref="multipleTable"
        :data="studentScore"
        style="width: 100%"
        tooltip-effect="dark"
      >
        <el-table-column
          prop="studentId"
          label="学号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="studentName"
          label="姓名"
          width="200">
        </el-table-column>
        <!-- 动态生成列 -->
        <el-table-column
          v-for="(item,index) in studentScore[0].score"
          :key="index"
          width="150"
          align="center"
        >
          <template slot="header">
            <div>{{ item.assessmentName }}成绩</div>
          </template>
          <template v-slot="scope"> {{scope.row.score[index].score}}</template>
        </el-table-column>

        <el-table-column
          v-for="(item,index) in studentScore[0].objectiveEvaluate"
          :key="index"
          width="150"
          align="center"
        >
          <template slot="header">
            <div>课程目标{{ item.objectiveId }}达成度</div>
          </template>
          <template v-slot="scope"> {{scope.row.objectiveEvaluate[index].eval}}</template>
        </el-table-column>
        <!-- 动态生成列结束 -->
      </el-table>
    </el-dialog>

    <!-- 报告内容编辑 -->
    <el-dialog
      :visible.sync="dialogVisible_report_edit"
      :close-on-click-modal="false"
      :title="'《'+viewingCourse.name+'》 “'+semesterDescribe+'” 课程目标达成情况报告'"
      width="90%"
      @open="figurePlot"
      center
    >
      <!-- 达成度分布图 -->
      <div class="objectiveEvaluateFigure">
        <div id="objectiveEvaluateChartDOM" style="width:100%;height:100%"></div>
      </div>
      <!-- 编辑本文 -->
      <el-form :model="formData" ref="vForm" :rules="rules" label-position="top" label-width="80px" size="medium"
               @submit.native.prevent>
        <el-form-item label="课程目标达成情况分析" prop="text" style="margin-bottom: 10px;">
          <el-button type="text" @click="edit()">  编辑内容  </el-button>
          <el-button type="text" @click="save()">  保存  </el-button>
          <br />
          <dl v-if="!editing" v-html="formData.text">{{formData.text}}</dl>
          <el-input
            id="TextArea1"
            type="textarea"
            v-if="editing"
            :value="formData.text"
            v-model="formData.text" rows="3"
            placeholder="输入课程目标达成情况分析"
          ></el-input>


        </el-form-item>
        <el-form-item label="持续改进情况与成效" prop="text">
          <el-button type="text" @click="edit()">  编辑内容  </el-button>
          <el-button type="text" @click="save()">  保存  </el-button>
          <br />
          <div v-if="!editing">{{formData}}</div>
          <input type="text"
                 class="form-control"
                 ref="input"
                 v-if="editing"
                 :value="formData.text"
                 :model="formData.text"
                 placeholder="输入持续改进情况与成效"
          >


        </el-form-item>
      </el-form>




      <el-button type="primary" @click="submitReport()">提交</el-button>
      <el-button type="primary" @click="reportGen()">报告预览</el-button>


    </el-dialog>

    <!-- 报告预览页面 -->
    <el-dialog
      :visible.sync="dialogVisible_report"
      :close-on-click-modal="false"
      :title="'报告页面'"
      width="90%"
      center>
      <el-button type="primary" @click="pdfBtn">打印/下载报告</el-button>
      <div id="pdfDom" style="margin:0 auto;width:1200px">
        <h1>课程目标达成情况报告</h1>
        <h2>1．课程基本信息</h2>
        <p>课号：{{viewingCourse.id}} 课名：{{viewingCourse.name}} 任课教师：${teacher} 授课班级：${className}</p>
        <p>开课学期：{{ selectSemester }}  考核方式： 课程性质： 学分： 学时：</p>
        <h2>2．课程目标达成度</h2>
        <img :src="'data:image/jpeg;base64' + this.chartUrl" alt="">

        <h2>3．学生自评目标达成情况</h2>

        <h2>4．课程目标达成情况分析</h2>
        <p>...</p>
        <h2>5．持续改进与成效</h2>
        <p>{{this.formData.text}}</p>
      </div>
    </el-dialog>

    <el-pagination
      :current-page="currentPage"
      :page-sizes="[10, 15, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

  </div>

</template>

<script>
import {requestByClient, supplierConsumer} from "@/utils/HttpUtils";
import axios from "axios";
import {getToken} from "@/utils/auth";
import {Loading} from "element-ui";
import XLSX from 'xlsx';
import {resumecss} from '@/styles/download.css.js';
import htmlToPdf from '@/utils/htmlToPdf'
import * as echarts from 'echarts'
//const echarts = require('echarts');

export default {
  name: "CheckLinkScore",
  components: {},
  props: {},
  data() {
    return {
      formData: {
        text: "",
      },
      editing: false,
      dialogVisible: false,
      dialogVisible_report_edit: false,
      dialogVisible_report: false,
      dialogVisible_editScore: false,
      pageSize: 20,
      total: 0,
      currentPage: 1,
      viewCourseTasks: [],
      viewingCourseTask: {},
      viewingCourse: {},
      viewingCheckLink: {},
      viewingCheckLinks: [],
      viewingStuScore: [],
      tableData: [],
      courses: [],
      courseObjectiveInfo:null,
      checkLinksList: [],
      searchForm: {
        word: '',
        year: ''
      },
      ckeckLinkEditForm: {
        course: {
          semesterId:'',
        },
        checkLink: {},
        stuScores: []
      },
      stuScoreSerchForm: {
        stuNo: ''
      },
      fileList: [],
      selectSemester:'',
      semesterCultivationId:'',
      semesterDescribe:'',
      semesterList:[{
        semesterId:'',
        semesterDescribe:'',
        cultivationId:'',
      }],
      courseScore:[],
      objectiveCalculateParameter:[],
      studentScore:[{
        courseId:null,
        studentId:null,
        studentName:null,
        objectiveEvaluate:[{
          objectiveId:null,
          subAttributeEvaluate:null,
          eval:null,
        }],
        score: [{
          assessmentName:null,
          score:null,
        }],
      }],
      subAttributeEvaluate:[{}],
      upload_file: "",
      lists: [],
      chartUrl:null,
      objectiveEvaluateChart:null,
      calculateButtonSituation: false,
      reportButtonSituation: false,
      htmlTitle:'页面导出PDF文件名',
      url:null,
      rules:[{}]
    }
  },
  watch: {
    '$store.state.settings.editYear': 'getCourseList'
  },
  mounted() {
    this.getCourseList()
  },
  methods: {
    //生成PDF预览
    pdfBtn(){
      htmlToPdf.getPdf(this.htmlTitle);
    },

    //报告生成
    reportGen(){
      this.htmlTitle='《'+this.viewingCourse.name+'》 “'+this.semesterDescribe+'” 课程目标达成情况报告'
      this.dialogVisible_report_edit=false
      this.dialogVisible_report=true
    },

    //录入多行文本
    submitReport() {
      this.$refs['vForm'].validate(valid => {
        if (!valid) return
        // eslint-disable-next-line no-console
        console.log( this.formData.text);
        //TODO 增加获取并显示measure的部分
        requestByClient(supplierConsumer, 'POST', 'courseObjective/updateMeasure', {
          courseId: this.viewingCourse.id,
          semesterId: this.selectSemester,
          measure: this.formData.text.toString(),
        }, res => {
          if(res.data.succ===true){
            this.$message({
              message: '提交成功',
              type: 'success'
            })
          }
        })
      })
    },
    resetForm() {
      this.$refs['vForm'].resetFields()
    },

    edit() {
      this.editing = true
      this.$nextTick(function () {
        this.$els.input.focus()
      })
    },
    save() {
      this.editing = false
      const textArr = document.getElementById('TextArea1').value.split(/\r{0,}\n/)
      let textHtml = ''
      textArr.forEach(item => { if (item) textHtml += '<p>' + item + '</p>' })
      //let placeholder = document.createElement('div');
      //placeholder.innerHTML = textHtml;
      //let nodes = placeholder.childNodes;
      this.formData.text=textHtml
      // eslint-disable-next-line no-console
      //console.log(placeholder);
      // eslint-disable-next-line no-console
      console.log(this.formData.text);

    },

    //获取学期列表
    getSemesterList() {
      if (this.selectSemester===''){
        requestByClient(supplierConsumer, 'POST', 'course/listSemester', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        },res => {
          if (res.data.succ) {
            this.semesterList = res.data.data
          }
        })
      }
      else {
        this.semesterList.forEach(i=>{
          if (i.semesterId===this.selectSemester){
            this.semesterCultivationId=i.cultivationId
            this.semesterDescribe=i.semesterDescribe
          }
        })
        requestByClient(supplierConsumer, 'POST', 'course/list', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          semesterId: this.selectSemester,
        },res => {
          if (res.data.succ) {
            this.tableData = res.data.data
          }
        })
      }
    },

    //导入EXCEL
    clickUploadButton() {
      this.$refs.files.click();
    },
    readExcel() {
      const loading = this.$loading({
        lock: true,
        text: "文件上传中...",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      })
      // js 获取文件对象
      let fileObj = document.getElementById("uploadScore").files
      let that = this;
      // 读取表格文件
      const files = fileObj
      if (files.length <= 0) {
        return false;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        this.$message({
          message: "上传格式不正确，请上传xls或者xlsx格式",
          type: "warning"
        });
        return false;
      } else {
        // 更新获取文件名
        that.upload_file = files[0].name;
      }
      // eslint-disable-next-line no-console
      console.log(that.upload_file);
      const fileReader = new FileReader();
      fileReader.onload = ev => {
        try {
          const data = ev.target.result;
          const workbook = XLSX.read(data, {
            type: "binary"
          });
          const sheetName = workbook.SheetNames[0]; //取第一张表
          const workSheet = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]); //生成json表格内容

          that.lists = [];
          // 从解析出来的数据中提取相应的数据
          workSheet.forEach(item => {
            // eslint-disable-next-line no-console
            //console.log(item)
            if (item["__EMPTY_1"]!=="学号") {
              if (item["__EMPTY_2"]!=="登分日期：") {
                if (typeof (item["__EMPTY_1"])!=="undefined"){
                  if (typeof (item["__EMPTY_3"])!=="undefined"){
                    that.lists.push({
                      studentId: item["__EMPTY_1"],
                      studentName: item["__EMPTY_2"],
                      assessmentName: "期中考试",
                      score: item["__EMPTY_3"],
                      courseId: this.viewingCourse.id,
                      semesterId: this.selectSemester,
                    })
                  }
                  if (typeof (item["__EMPTY_4"])!=="undefined"){
                    that.lists.push({
                      studentId: item["__EMPTY_1"],
                      studentName: item["__EMPTY_2"],
                      assessmentName: "课程报告",
                      score: item["__EMPTY_4"],
                      courseId: this.viewingCourse.id,
                      semesterId: this.selectSemester,
                    })
                  }
                  if (typeof (item["__EMPTY_5"])!=="undefined") {
                    that.lists.push({
                      studentId: item["__EMPTY_1"],
                      studentName: item["__EMPTY_2"],
                      assessmentName: "实验成绩",
                      score: item["__EMPTY_5"],
                      courseId: this.viewingCourse.id,
                      semesterId: this.selectSemester,
                    })
                  }
                  if (typeof (item["__EMPTY_6"])!=="undefined") {
                    that.lists.push({
                      studentId: item["__EMPTY_1"],
                      studentName: item["__EMPTY_2"],
                      assessmentName: "期末考试",
                      score: item["__EMPTY_6"],
                      courseId: this.viewingCourse.id,
                      semesterId: this.selectSemester,
                    })
                  }
                }
                } else {
                this.$message({
                    message: "上传文件出错，无法读取成绩数据",
                    type: "error"
                })
              }
            }
          })
          loading.close()
          // 给后端发请求
          this.submitImport();
        } catch (e) {
          return false;
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    submitImport() {
      // 给后端发送请求，更新数据
      // eslint-disable-next-line no-console
      console.log("假装给后端发了个请求...");
      // eslint-disable-next-line no-console
      console.log(this.lists);

      requestByClient(supplierConsumer, 'POST', 'stuScore/insertBatchScore', this.lists,res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          this.listScore()
          this.calculateButtonSituation = false
        }
      })
        this.loading = false
    },

    //打开成绩管理窗口
    //TODO 培养方案基本信息中增加“定义/编辑考核环节”的功能
    openScoreEditor(course){
      if (this.semesterCultivationId === '') {
        this.$message({
          message: '请选择学期',
          type: 'error'
        })
      } else {
        this.dialogVisible_editScore = true
        this.viewingCourse = course
        this.listScore()
      }
    },

    //加载成绩数据
    listScore() {
        let score=[]
        //获取成绩列表
        requestByClient(supplierConsumer, 'POST', 'checkLink/list', {//判断是否定义考核环节
          courseId: this.viewingCourse.id,
          cultivationId: this.semesterCultivationId,
        }, res => {
          if (res.data.succ) {
            this.checkLinksList = res.data.data
            // eslint-disable-next-line no-console
            console.log('0. checkLink/list res')
            // eslint-disable-next-line no-console
            console.log(this.checkLinksList)
            //若已定义考核环节，将考核环节信息查询后储存在score中
            if (this.checkLinksList.length > 0) {
              res.data.data.forEach(i=>{
                score.push({
                  assessmentName: i.assessmentName,
                  score: '',
                  totalScore: i.totalScore,
                })
              })
              // eslint-disable-next-line no-console
              console.log('0. score')
              // eslint-disable-next-line no-console
              console.log(score)
              //以下顺序执行
              new Promise((resolve)=> {
                //初始化学生成绩列表JSON
                let temp = []
                requestByClient(supplierConsumer, 'POST', 'stuScore/list', {
                  //按score[0].assessmentName的考核环节查询学生信息
                  assessmentName: score[0].assessmentName,
                  courseId: this.viewingCourse.id,
                  cultivationId: this.semesterCultivationId,
                  semesterId: this.selectSemester,
                }, res => {
                  if (res.data.succ) {
                    if (typeof (this.studentScore.studentId) === 'undefined') {
                      res.data.data.forEach(p => {
                        //JSON列表中根据查询结果插入学生信息，但插入成绩为空
                        temp.push({
                          studentId: p.studentId,
                          studentName: p.studentName,
                          courseId: p.courseId,
                          score:[],
                          objectiveEvaluate:[],
                        })
                      })
                    }
                  }
                })
                this.studentScore = temp
                // eslint-disable-next-line no-console
                console.log('1.1 this.studentScore')
                // eslint-disable-next-line no-console
                console.log(temp)
                resolve()
              }).then(()=> {
                // eslint-disable-next-line no-console
                console.log('1.2 this.studentScore')
                //再次查询并获取全部成绩，根据score中存储的考核环节轮询
                score.forEach(i => {
                    requestByClient(supplierConsumer, 'POST', 'stuScore/list', {
                      assessmentName: i.assessmentName,
                      courseId: this.viewingCourse.id,
                      cultivationId: this.semesterCultivationId,
                      semesterId: this.selectSemester,
                    }, res => {
                      if (res.data.succ) {
                        res.data.data.forEach(p => {
                          this.studentScore.forEach(q => {
                            if (p.studentId === q.studentId) {
                                q.score.push({
                                  assessmentName: i.assessmentName,
                                  score: p.score,
                                })
                            }
                          })
                        })
                      }
                    })
                })
                // eslint-disable-next-line no-console
                console.log(this.studentScore)
              }).then(()=>{
                //3. 前端显示
                // eslint-disable-next-line no-console
                console.log('3 this.studentScore')
                // eslint-disable-next-line no-console
                console.log(this.studentScore)
              }).then(()=>{
                //获取达成度列表
                requestByClient(supplierConsumer, 'POST', 'student/listObjectiveEvaluate', {
                  courseId: this.viewingCourse.id,
                  semesterId: this.selectSemester,
                }, res => {
                  if (res.data.data.length===0) this.calculateButtonSituation=false
                  else {
                    this.calculateButtonSituation=true
                    res.data.data.forEach(i=>{
                      this.studentScore.forEach(j=>{
                        if (i.studentId===j.studentId) {
                          j.objectiveEvaluate.push({
                            objectiveId: i.objectiveId,
                            eval: i.eval,
                            subAttributeEvaluate: i.subAttributeEvaluate,
                          })
                        }
                      })
                    })
                  }
                  // eslint-disable-next-line no-console
                  console.log('3.1 listObjectiveEvaluate')
                  // eslint-disable-next-line no-console
                  console.log(this.studentScore)
                })
              })
            } else {
              this.$message({
                message: '该课课程在培养方案' + this.semesterCultivationId + '中未录入考核环节',
                type: 'warning'
              });
            }
          }
        })
    },

    //计算达成度
    calculate(course){
      let evaluate=[] //储存课程目标达成度
      let supportSubRatio=[]//储存课程目标的支撑二级指标系数
      let courseSupportRatio=[]//储存课程的支撑二级指标系数
      let data=[]
      let courseTaskCheckLinkList=null
      let courseObjectiveList=null

      new Promise(resolve => {
        //1 获取课程对二级指标点的支撑权重
        requestByClient(supplierConsumer, 'POST', 'courseObjective/CourseTargetvoList', {
          courseId: course.id,
          cultivationId: this.semesterCultivationId,
        }, res => {
          //将权重分配进一个数组
          if (res.data.succ) {
            this.courseObjectiveInfo=res.data.data

            //2 获取考核环节对课程目标的支撑权重
            requestByClient(supplierConsumer, 'POST', 'courseTaskCheckLink/list', {
              courseId: course.id,
              cultivationId: this.semesterCultivationId,
            }, res => {
              if (res.data.succ) {
                courseTaskCheckLinkList = res.data.data

                //3 获取课程目标支撑二级指标的权重
                requestByClient(supplierConsumer, 'POST', 'courseObjective/list', {
                  courseId: course.id,
                  cultivationId: this.semesterCultivationId,
                }, res => {
                  //将权重分配进一个数组
                  if (res.data.succ) {
                    courseObjectiveList = res.data.data

                    // eslint-disable-next-line no-console
                    console.log('0.1 获取课程对二级指标点的支撑权重objectiveSupportRatio')
                    // eslint-disable-next-line no-console
                    console.log(this.courseObjectiveInfo)
                    // eslint-disable-next-line no-console
                    console.log('0.2 获取考核环节对课程目标的支撑权重ratio')
                    // eslint-disable-next-line no-console
                    console.log(courseTaskCheckLinkList)
                      // eslint-disable-next-line no-console
                      console.log('0.3 获取课程目标支撑二级指标的权重objectiveSupportRatio')
                      // eslint-disable-next-line no-console
                      console.log(courseObjectiveList)
                    resolve()
                    }
                  })
                }
              })
          }
        })
      }).then(()=>{
        // eslint-disable-next-line no-console
        console.log('1.0 THEN ')
        //转储存课程支撑二级指标系数
        this.courseObjectiveInfo.forEach(i=>{
          let n=i.objectiveId-1
          //supportAttributeRatio存权重，数组坐标含义‘指标点-1’
          courseSupportRatio[n]=i.courseTarget.supportRatio
        })
        // eslint-disable-next-line no-console
        console.log('1.1 转储存课程支撑二级指标系数')
        // eslint-disable-next-line no-console
        console.log(courseSupportRatio)

        //转存课程目标支撑二级指标的权重
        courseObjectiveList.forEach(i=>{
          let n=(i.objectiveId-1)
          evaluate[n]=0.00
          //evaluateSubAttribute存权重，数组坐标含义‘指标点-1’
          supportSubRatio[n]=i.objectiveSupportRatio
        })
        // eslint-disable-next-line no-console
        console.log('1.2 转存课程目标支撑二级指标的权重')
        // eslint-disable-next-line no-console
        console.log(supportSubRatio)
      }).then(()=>{
        // eslint-disable-next-line no-console
        console.log('2.0 THEN ')
        this.studentScore.forEach(p=> {
          p.score.forEach(q=>{
            courseTaskCheckLinkList.forEach(i=>{
              if (i.assessmentName === q.assessmentName) {
                let totalScore=100
                let num=i.objectiveId-1
                evaluate[num]=evaluate[num]+i.ratio*q.score/totalScore
              }
            })
          })
          for (let n = 0; n < evaluate.length; n++) {
            p.objectiveEvaluate.push({
              objectiveId: n+1,
              eval: evaluate[n],
              subAttributeEvaluate: evaluate[n]*supportSubRatio[n]*courseSupportRatio[n]
            })
            evaluate[n]=0.00
          }
        })
      }).then(()=>{

        this.studentScore.forEach(i=>{
          i.objectiveEvaluate.forEach(j=>{
            let attributeId
            let subAttributeId
            this.courseObjectiveInfo.forEach(k=>{
              if (k.courseId===course.id) {
                if (k.objectiveId===j.objectiveId) {
                  attributeId=k.attributeId
                  subAttributeId=k.subAttributeId
                }
              }
            })
            data.push({
              studentId: i.studentId,
              courseId: course.id,
              objectiveId: j.objectiveId,
              attributeId: attributeId,
              subAttributeId: subAttributeId,
              semesterId: this.selectSemester,
              cultivationId: this.semesterCultivationId,
              eval: j.eval,
              subAttributeEvaluate: j.subAttributeEvaluate
            })
          })
        })
        // eslint-disable-next-line no-console
        console.log('this.studentScore')
        // eslint-disable-next-line no-console
        console.log(this.studentScore)
        // eslint-disable-next-line no-console
        console.log('data')
        // eslint-disable-next-line no-console
        console.log(data)

      }).then(()=>{

        requestByClient(supplierConsumer, 'POST', 'student/updateObjectiveEvaluate', data, res => {
          if (res.data.succ) {
            this.$message({
              message: '更新成功',
              type: '200'
            })
            this.calculateButtonSituation=true
          }
          else {
            this.$message({
              message: '更新出错',
              type: 'error'
            })
          }
        })
      })

    },

    //撰写报告
    //TODO debug 第一次打开查看报告无法显示分布图
    async editReport(course){
      if (this.semesterCultivationId === '') {
        this.$message({
          message: '请选择学期',
          type: 'error'
        })
      } else {
        if(this.viewingCourse!==course){
          this.viewingCourse=course
          await this.listScore()
        }

        await requestByClient(supplierConsumer, 'POST', 'courseObjective/listMeasure', {
            courseId: this.viewingCourse.id,
            semesterId: this.selectSemester,
          }, res => {
            if (res.data.succ === true) {
              // eslint-disable-next-line no-console
              console.log('editReport(Course)');
              // eslint-disable-next-line no-console
              console.log(res.data.data);
              this.formData.text = res.data.data.measure
            }
          })

        this.dialogVisible_report_edit = true
      }
    },

    //图表打开
    figurePlot() {
      this.$nextTick(() => {
        this.setEvaluateFigurePlot()
        this.chartUrl = this.objectiveEvaluateChart.getDataURL({
          pixelRatio: 1,
          backgroundColor: 'white'
        });
      })
    },
    //图表参数设置
    setEvaluateFigurePlot() {
      const chartDom = document.getElementById('objectiveEvaluateChartDOM')
      if (chartDom) {
        this.objectiveEvaluateChart = echarts.init(chartDom);
        let option,
          name = [],
          data = [];

        for (let n = 0; n < this.studentScore[0].objectiveEvaluate.length; n++) {
          data[n] = []
          name[n] = this.studentScore[0].objectiveEvaluate[n].objectiveId
          for (let i = 0; i < this.studentScore.length; i++) {
            let step = [this.studentScore[i].studentId]
            step.push(this.studentScore[i].objectiveEvaluate[n].eval)
            data[n].push(step)
          }
        }
        option = {
          animation: false,
          color: ['red', 'green'],
          title: {x: 33, text: '《' + this.viewingCourse.name + '》课程目标达成度分布', subtext: this.semesterDescribe},
          tooltip: {},
          xAxis: {
            type: 'category',
            scale: true,
            axisLabel: {
              rotate: 45
            }
          },
          yAxis: {},
          series: [],
          legend: {
            orient: 'vertical',
            top: 'center',
            right: -5
          },
        };
        data.forEach((item, index) => {
          option.series.push({
            name: '课程目标' + name[index],
            symbolSize: 10,
            data: item,
            type: 'scatter',
            markLine: {
              data: [
                {type: 'average', name: '平均值'}
              ]
            },
            markPoint: {data: [{type: 'max', name: '最大值'}, {type: 'min', name: '最小值'}],}
          })
        })
        // eslint-disable-next-line no-console
        console.log('echarts data')
        // eslint-disable-next-line no-console
        console.log(option.series)
        this.objectiveEvaluateChart.setOption(option)
      }
    },

    exportTemplate(course) {
      if (this.semesterCultivationId===''){
        this.$message({
          message: '请选择学期',
          type: 'error'
        })
      }
      else{
        let baseURL = supplierConsumer.defaults.baseURL
        axios.post(baseURL + '/stuScore/exportTemplate', {
          cultivationId: this.semesterCultivationId,
          courseId: course.id
        }, {
          responseType: 'blob',
          headers: {
            '_token': getToken()
          }
        }).then(res => {
          const blob = new Blob([res.data], {
            type: 'application/vnd.ms-excel'
          })
          const objectUrl = URL.createObjectURL(blob)
          const a = document.createElement('a')
          a.href = objectUrl
          a.download = course.name + this.$store.state.settings.editYear + '年成绩导入模板.xlsx'
          // a.click();
          // 下面这个写法兼容火狐
          a.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}))
          window.URL.revokeObjectURL(objectUrl)
        })
      }
    },

    getCourseList() {
      this.dialogVisible = false
      this.dialogVisible_report_edit = false
      this.dialogVisible_editScore = false
      requestByClient(supplierConsumer, 'POST', 'course/list', {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        word: this.searchForm.word
      }, res => {
        if (res.data.succ) {
          this.courses = res.data.data
          this.total = res.data.data.total
          this.pageSize = res.data.data.pageSize
          this.currentPage = res.data.data.pageNum
        }
        this.getSemesterList()
      })
    },

    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.getCourseList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getCourseList()
    },
  }
}
</script>

<style scoped>

.historyLabel {
  width: 97%;
  height: 550px;
}

.historyCanvas {
  width: 1150px;
  height: 530px;
}

.elinkChart {
  display: inline-block;
  width: 60%;
}

.stuElinkScoreBar {
  width: 100%;
  height: 240px;
}

.sysElinkMixPie {
  width: 100%;
  height: 370px;
}

.sysElinkScoreBar {
  display: inline-block;
  width: 40%;
  height: 600px;
}

.objectiveEvaluateFigure {
  height: 400px;
}

.elinkScatterPlot {
  width: 100%;
  height: 100%;
}

.container {
  border: none;
  border-radius: 4px;
  background-color: #409eff;
  height: 40px;
  margin-top: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 15px;
  min-width: 80px;
  *zoom: 1;
}

.upload_file {
  font-size: 20px;
  opacity: 0;
  position: absolute;
  filter: alpha(opacity=0);
  width: 60px;
}

</style>
