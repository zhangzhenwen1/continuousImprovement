<template>
  <div style="padding: 20px">
    <el-table
      ref="multipleTable"
      :data="tableData"
      style="width: 100%"
      height="750"
      tooltip-effect="dark">
      <el-table-column
        prop="id"
        label="课程编号"
        width="60">
      </el-table-column>
      <el-table-column
        prop="name"
        label="课程名称"
        width="100">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            :disabled="scope.row.editStatus === 0"
            @click="handleEditForm(scope.row)"
          >
            编辑课程考核环节
          </el-button>
          <el-button
            type="primary"
            :disabled="scope.row.editStatus === 0"
            @click="handleEditObjective(scope.row)"
          >
            编辑课程目标
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="考核环节编辑"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible"
      width="90%"
      center>
      <div>
        <el-form :model="assessmentList" status-icon ref="ruleForm" class="demo-ruleForm">
          <el-form-item label="课程名称" prop="pass">
            <div style="font-size: 18px;color: #1a1a1a">{{
                courseName
              }}</div>
          </el-form-item>
          <!--下拉组件-->
          <el-form-item label="选择培养方案版本" prop="pass">
            <!--选项数据来源：
                  cultivationList: [{
                    value: '选项1',
                    label: '黄金糕'
                  },{...}
                  默认值所在数组：
                  v-model="selectValue"
              -->
            <el-select
              v-model="cultivationId"
              placeholder="选择培养方案版本"
              @change="getCultivationIdList(cultivationId)"
            >
              <el-option
                v-for="item in cultivationList"
                :key="item.id"
                :label="item.cultivationName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="考核环节：" prop="pass">
            <el-button type="primary" round style="" @click="handleAddAssessment">添加</el-button>
            <br>
            <el-table
              ref="multipleTable"
              style="width: 100%"
              height="50"
              tooltip-effect="dark">
              <el-table-column
                prop=""
                label="考核环节"
                width="100">
              </el-table-column>
              <el-table-column
                prop=""
                label="目标分数">
              </el-table-column>
              <el-table-column
                prop=""
                label="总评占比">
              </el-table-column>
            </el-table>
            <span v-for="(item,index) in assessmentList" type="text" autocomplete="off">
              <el-select v-model="item.assessmentName" placeholder="课程目标" clearable filterable style="width: 40%;margin-top: 10px">
                <el-option label="期末考试" value="期末考试" />
                <el-option label="期中考试" value="期中考试" />
                <el-option label="日常作业" value="日常作业" />
                <el-option label="课堂表现" value="课堂表现" />
                <el-option label="日常考勤" value="日常考勤" />
              </el-select>
              <el-input type="text" autocomplete="off" v-model="item.totalScore" style="width: 25%;margin-top: 10px" />
              <el-input type="text" autocomplete="off" v-model="item.scoreRatio" style="width: 25%;margin-top: 10px" />
              <el-button type="danger" icon="el-icon-delete" circle @click="handleDeleteAssessment(index)" />
            </span>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitAssessment">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="课程目标编辑"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible_objective"
      width="90%"
      center
    >
      <div>
        <el-form
          ref="ruleForm"
          :model="objectiveInfo"
          status-icon
          class="demo-ruleForm"
        >
          <!--课程名称-->
          <el-form-item
            label="课程名称"
            prop="pass"
          >
            <div style="font-size: 18px;color: #1a1a1a">
              {{ courseName }}
            </div>
          </el-form-item>
          <!--下拉组件-->
          <el-form-item label="选择培养方案版本" prop="pass">
            <!--选项数据来源：
                  cultivationList: [{
                    value: '选项1',
                    label: '黄金糕'
                  },{...}
                  默认值所在数组：
                  v-model="selectValue"
              -->
            <el-select
              v-model="cultivationId"
              placeholder="选择培养方案版本"
              @change="getCultivationIdList(cultivationId)"
            >
              <el-option
                v-for="item in cultivationList"
                :key="item.id"
                :label="item.cultivationName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="课程目标："
            prop="pass"
          >
            <el-button
              type="primary"
              round
              style=""
              @click="handleAddObjective"
            >
              添加
            </el-button>
            <br>
            <el-table
              ref="multipleTable"
              style="width: 100%"
              height="50"
              tooltip-effect="dark"
            >
              <el-table-column
                prop=""
                label="课程目标编号"
                width="50"
              />
              <el-table-column
                prop=""
                label="课程目标描述"
                width="300"
              />
              <el-table-column
                prop=""
                label="支撑二级指标点"
                width="300"
              />
              <el-table-column
                prop=""
                label="权重系数"
                width="80"
              />
            </el-table>
            <span
              v-for="(item,index) in objectiveInfo"
              type="text"
              autocomplete="off"
            >
              <el-input
                v-model="item.objectiveId"
                type="text"
                autocomplete="off"
                style="width: 5%;margin-right: 5px; margin-top: 10px"
              />
              <el-input
                v-model="item.objectiveDescribe"
                type="text"
                autocomplete="off"
                style="width: 20%;margin-right: 10px"
              />
              <el-select
                v-model="item.target.idx_target"
                placeholder="支撑二级指标点"
                @change="getSubAttributeList()"
                clearable
                style="width: 27%;margin-right: 10px"
              >
                <el-option
                  v-for="item1 in subAttributeList"
                  :key="item1.idx_target"
                  :label="`${item1.attributeId}.${item1.id} ${item1.subAttributeDescribe}`"
                  :value="item1.idx_target"
                />
              </el-select>
              <el-input-number
                v-model="item.objectiveSupportRatio"
                :min="0.1"
                :max="1"
                step="0.1"
                label="权重系数"
                style="width: 10%;margin-right: 10px"
              />
              <el-button
                type="danger"
                icon="el-icon-delete"
                circle
                @click="handleDeleteObjective(index)"
              />
              <el-button
                type="primary"
                @click="handleRelationEditForm(index)"
              >
                关联考核环节
              </el-button>
              <br />
            </span>
          </el-form-item>
        </el-form>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible_objective = false">
          取 消
        </el-button>
        <el-button
          type="primary"
          @click="submitEditObjective"
        >
          确 定
        </el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="关联课程目标与考核环节"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible_relation"
      width="90%"
      center>
      <div>
        <el-form ref="ruleForm" :model="editRelation" status-icon class="demo-ruleForm">
          <el-form-item label="课程" prop="name">
            <div style="font-size: 18px;color: #1a1a1a">
              {{courseName}}
            </div>
          </el-form-item>
          <el-form-item label="培养方案版本" prop="pass">
            <div style="font-size: 18px;color: #1a1a1a">
              {{cultivationId}}
            </div>
          </el-form-item>
          <el-form-item label="课程目标" prop="pass">
            <div style="font-size: 18px;color: #1a1a1a">
              {{objectiveId}}. {{objectiveDescribe}}
            </div>
          </el-form-item>
          <el-form-item label="考核环节：" prop="pass">
            <el-button type="primary" round style="" @click="handleAddRelation">添加</el-button>
            <br>
            <el-table
              ref="multipleTable"
              style="width: 100%"
              height="50"
              tooltip-effect="dark">
              <el-table-column
                prop=""
                label="考核环节"
                width="230">
              </el-table-column>
              <el-table-column
                prop=""
                label="权重系数">
              </el-table-column>
            </el-table>
            <span v-for="(item1,index1) in editRelation" type="text" autocomplete="off">
              <el-select v-model="item1.assessmentName" placeholder="考核环节" clearable filterable
                         style="width: 50%;margin-top: 10px">
                <el-option v-for="i in assessmentList"
                           :key="i.assessmentName"
                           :label="i.assessmentName"
                           :value="i.assessmentName"/>
              </el-select>
              <el-input-number v-model="item1.ratio" :min="0.1" :max="1" step="0.1" label="权重系数"
                               style="width: 35%;margin-top: 10px"/>
              <el-button type="danger" icon="el-icon-delete" circle @click="deleteRelation(index1)"
                         style="margin-left: 10px"/>
            </span>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible_relation = false">取 消</el-button>
        <el-button type="primary" @click="submitRelationForm">确 定</el-button>
      </div>
    </el-dialog>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 15, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import {requestByClient, supplierConsumer} from "@/utils/HttpUtils";

export default {
  name: "Assessment",
  mounted() {
    this.getList()
  },
  data() {
    return {
      dialogVisible: false,
      dialogVisible_objective: false,
      dialogVisible_relation: false,
      dialogVisible_relationList:false,
      visible: false,
      tableData: [],
      pageSize: 20,
      total: 0,
      currentPage: 1,
      ableTarget: [],
      searchForm: {
        word: ''
      },
      courseName: '',
      courseId:'',
      cultivationId:'',
      type:'',
      assessmentList:[],
      objectiveInfo:[{
        objectiveSupportRatio:1.00,
        target:{},
      }],

      objectiveId:'',
      objectiveDescribe:'',
      editRelation:[{
          objectiveId:'',
          courseId:'',
          cultivationId:'',
          assessmentName:'',
          ratio: 1.00
        }],
      selectAssessmentSession:'',

      cultivationList: [],
      subAttributeList:[{
        idx_target:'',
        attributeId:'',
      }],
    }
  },
  watch: {

  },
  methods: {
    //获取课程的考核环节
    getAssessmentList(courseId, cultivationId){
      requestByClient(supplierConsumer, 'POST', 'checkLink/list', {
        courseId: courseId,
        cultivationId: cultivationId,
      }, res => {
        if (res.data.succ) {
          this.assessmentList = res.data.data
          // eslint-disable-next-line no-console
          console.log('this.assessmentList')
          // eslint-disable-next-line no-console
          console.log(this.assessmentList)
        }
      })
    },
    //获取目标考核关系
    getRelationList(){
      requestByClient(supplierConsumer, 'POST', 'courseTaskCheckLink/list', {
        courseId: this.courseId,
        cultivationId: this.cultivationId,
        objectiveId: this.objectiveId,
      }, res => {
        if (res.data.succ) {
          this.editRelation = res.data.data
          // eslint-disable-next-line no-console
          console.log('getRelationList')
          // eslint-disable-next-line no-console
          console.log(this.editRelation)
        }
      })
    },
    getSubAttributeList() {
        requestByClient(supplierConsumer, 'POST', 'target/list', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          cultivationId: this.cultivationId
        }, res => {
          if (res.data.succ) {
            this.subAttributeList = res.data.data
          }
        })
        //this.objectiveInfo[index].idx_target=idx_target
        this.objectiveInfo.forEach(p=> {
          p.idx_target = p.target.idx_target
          this.subAttributeList.forEach(i => {
            if (p.idx_target === i.idx_target) {
              p.attributeId = i.attributeId
              p.subAttributeId = i.id
            }
          })
        })
        // eslint-disable-next-line no-console
        console.log('objectiveInfo')
        // eslint-disable-next-line no-console
        console.log(this.objectiveInfo)
    },
    //获取下拉菜单数据及getAssessmentList,getRelationList,getSubAttributeList
    getCultivationIdList(val) {
      if (this.cultivationId===''){
        requestByClient(supplierConsumer, 'POST', 'cultivation/list', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        },res => {
          if (res.data.succ) {
            this.cultivationList = res.data.data
          }
        })
      }
      else {
        if(this.type==='editForm'){
          this.cultivationId=val
          this.getAssessmentList(this.courseId,val)
        }
        else if(this.type==='editObjective'){
          this.cultivationId=val
          this.getSubAttributeList()
          this.getAssessmentList(this.courseId, this.cultivationId)
          requestByClient(supplierConsumer, 'POST', 'courseObjective/voList', {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            cultivationId: val,
            courseId: this.courseId,
          },res => {
            if (res.data.succ) {
              this.objectiveInfo = res.data.data
              this.objectiveInfo.forEach(p=> {
                p.idx_target = p.target.idx_target
                this.subAttributeList.forEach(i => {
                  if (p.idx_target === i.idx_target) {
                    p.attributeId = i.attributeId
                    p.subAttributeId = i.id
                  }
                })
              })
              // eslint-disable-next-line no-console
              console.log('objectiveInfo')
              // eslint-disable-next-line no-console
              console.log(this.objectiveInfo)
              // eslint-disable-next-line no-console
              console.log('END 选择方案版本')
            }
          })
        }
      }
    },

    //编辑目标环节关系
    handleRelationEditForm(index) {
      this.objectiveId = this.objectiveInfo[index].objectiveId
      this.objectiveDescribe=this.objectiveInfo[index].objectiveDescribe
      this.getRelationList()
      this.dialogVisible_relation=true
    },
    handleAddRelation() {
      this.editRelation.push({
        objectiveId: this.objectiveId,
        courseId: this.courseId,
        cultivationId: this.cultivationId,
        assessmentName: '',
        ratio: 1.00,
      })
    },
    submitRelationForm() {
      // eslint-disable-next-line no-console
      console.log('submitRelationForm')
      // eslint-disable-next-line no-console
      console.log(this.editRelation)
      for (let item of this.editRelation) {
        if (item.assessmentName === '') {
          this.$message({
            message: '考核环节不能为空',
            type: 'error'
          });
          return false
        }
        if (item.ratio === '' || item.ratio < 0) {
          this.$message({
            message: '权重不能为空',
            type: 'error'
          });
          return false
        }
      }
      requestByClient(supplierConsumer, 'POST', 'courseTaskCheckLink/saveOrUpdate', this.editRelation, res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          this.dialogVisible_relation = false
          ;
        } else {
          this.$message.error(res.data.msg);
        }
        this.loading = false
      })
    },
    deleteRelation(index) {
      let info = this.editRelation[index]
      if (info.assessmentName) {
        this.$confirm('此操作将删除其支撑数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          requestByClient(supplierConsumer, 'POST', 'courseTaskCheckLink/delete', info, res => {
            if (res.data.succ) {
              this.$message({
                message: '已删除',
                type: 'success'
              });
              this.dialogVisible_relation=false
            } else {
              this.$message.error(res.data.msg);
            }
          })
          this.editRelation.splice(index, 1)
        })}
      else{
        this.editRelation.splice(index, 1)
      }
    },

    //编辑课程目标
    handleEditObjective(row) {
      this.type='editObjective'
      this.courseId=row.id
      this.courseName=row.name
      this.getCultivationIdList()
      this.dialogVisible_objective = true
      // eslint-disable-next-line no-console
      console.log('handleEditObjective')
      // eslint-disable-next-line no-console
      console.log(this.editObjective)
      // eslint-disable-next-line no-console
      console.log('this.subAttributeList')
      // eslint-disable-next-line no-console
      console.log(this.subAttributeList[0].attributeId)
      // eslint-disable-next-line no-console
      console.log('END handleEditObjective')
    },
    handleAddObjective() {
      this.objectiveInfo.push({
        objectiveId:'',
        objectiveDescribe:'',
        cultivationId: this.cultivationId,
        courseId: this.courseId,
        attributeId:'',
        subAttributeId:'',
        objectiveSupportRatio: 1.00,
        target:{},
      })
    },
    submitEditObjective(){
      requestByClient(supplierConsumer, 'POST', 'courseObjective/saveOrUpdate', this.objectiveInfo, res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.dialogVisible_objective = false
        }
      })
    },
    handleDeleteObjective(index) {
      let objectiveInfo = this.objectiveInfo[index]
      if (objectiveInfo.attributeId) {
        this.$confirm('此操作将删除其支撑数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          requestByClient(supplierConsumer, 'POST', 'courseObjective/delete', {
            courseId: objectiveInfo.courseId,
            cultivationId:objectiveInfo.cultivationId,
            objectiveId: objectiveInfo.objectiveId,
          }, res => {
            if (res.data.succ) {
              this.$message({
                  message: '已删除',
                  type: 'success'
                },
                this.dialogVisible_objective = false
              );
            } else {
              this.$message.error(res.data.msg);
            }
            this.loading = false
          })
          this.objectiveInfo.splice(index, 1)
        }).catch(() => {});
      } else {
        this.objectiveInfo.splice(index, 1)
      }
    },

    //编辑考核环节
    handleEditForm(row) {
      this.type='editForm'
      this.courseId=row.id
      this.courseName=row.name
      // eslint-disable-next-line no-console
      console.log('courseId')
      // eslint-disable-next-line no-console
      console.log(this.courseId)
      this.getCultivationIdList()
      this.dialogVisible = true
    },
    submitAssessment(){
      requestByClient(supplierConsumer, 'POST', 'checkLink/saveOrUpdate', this.assessmentList, res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.dialogVisible = false
        }
      })
    },
    handleAddAssessment() {
      this.assessmentList.push({
        courseId: this.courseId,
        cultivationId: this.cultivationId,
        assessmentName:'',
        totalScore:'',
        scoreRatio:'',
      })
    },
    handleDeleteAssessment(index) {
      let assessmentInfo = this.assessmentList[index]
      if (assessmentInfo.assessmentName) {
        this.$confirm('此操作将删除其支撑数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          requestByClient(supplierConsumer, 'POST', 'checkLink/delete', {
            courseId: this.courseId,
            cultivationId:this.cultivationId,
            assessmentName: assessmentInfo.assessmentName,
          }, res => {
            if (res.data.succ) {
              this.$message({
                  message: '已删除',
                  type: 'success'
                },
                this.dialogVisible = false
              );
            } else {
              this.$message.error(res.data.msg);
            }
            this.loading = false
          })
          this.assessmentList.splice(index, 1)
        }).catch(() => {
        });
      } else {
        this.assessmentList.splice(index, 1)
      }
    },

    getList() {
      this.dialogVisible = false
      this.loading = true
      requestByClient(supplierConsumer, 'POST', 'course/authList', {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        word: this.searchForm.word
      }, res => {
        if (res.data.succ) {
          this.tableData = res.data.data.list
          this.total = res.data.data.total
          this.pageSize = res.data.data.pageSize
          this.currentPage = res.data.data.pageNum
        }
        this.loading = false
      })
      this.getCultivationIdList()
    },

    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.getList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
    }
  }
}
</script>

<style scoped>

</style>
