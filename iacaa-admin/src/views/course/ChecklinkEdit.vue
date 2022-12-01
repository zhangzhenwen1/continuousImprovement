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
        <el-form :model="editForm" status-icon ref="ruleForm" class="demo-ruleForm">
          <el-form-item label="课程名称" prop="pass">
            <div style="font-size: 18px;color: #1a1a1a">{{ editForm.courseName }}</div>
          </el-form-item>
          <!--下拉组件-->
          <el-form-item label="选择培养方案版本" prop="pass">
            <!--选项数据来源：
                  options: [{
                    value: '选项1',
                    label: '黄金糕'
                  },{...}
                  默认值所在数组：
                  v-model="selectValue"
              -->
            <el-select
              v-model="selectValue"
              placeholder="选择培养方案版本"
              @change="getCultivationIdList(selectValue)"
            >
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.cultivationName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="考核环节：" prop="pass">
            <el-button type="primary" round style="" @click="handleAddChecklink">添加</el-button>
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
            <span v-for="(item,index) in editForm.assessmentInfo" type="text" autocomplete="off">
              <el-select v-model="item.assessmentName" placeholder="课程目标" clearable filterable style="width: 40%;margin-top: 10px">
                <el-option label="期末考试" value="期末考试" />
                <el-option label="期中考试" value="期中考试" />
                <el-option label="日常作业" value="日常作业" />
                <el-option label="课堂表现" value="课堂表现" />
                <el-option label="日常考勤" value="日常考勤" />
              </el-select>
              <el-input type="text" autocomplete="off" v-model="item.totalScore" style="width: 25%;margin-top: 10px" />
              <el-input type="text" autocomplete="off" v-model="item.scoreRatio" style="width: 25%;margin-top: 10px" />
              <el-button type="danger" icon="el-icon-delete" circle @click="handleDeleteChecklink(index)" />
            </span>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitCheckLinks">确 定</el-button>
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
          :model="editObjective"
          status-icon
          class="demo-ruleForm"
        >
          <!--课程名称-->
          <el-form-item
            label="课程名称"
            prop="pass"
          >
            <div style="font-size: 18px;color: #1a1a1a">
              {{ editObjective.courseName }}
            </div>
          </el-form-item>
          <!--下拉组件-->
          <el-form-item label="选择培养方案版本" prop="pass">
            <!--选项数据来源：
                  options: [{
                    value: '选项1',
                    label: '黄金糕'
                  },{...}
                  默认值所在数组：
                  v-model="selectValue"
              -->
            <el-select
              v-model="selectValue"
              placeholder="选择培养方案版本"
              @change="getCultivationIdList(selectValue)"
            >
              <el-option
                v-for="item in options"
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
              v-for="(item,index) in editObjective.objectiveInfo"
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
                @change="getSubAttributeList(item.target.idx_target)"
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
  name: "ChecklinkEdit",
  mounted() {
    this.getList()
  },
  data() {
    return {
      dialogVisible: false,
      dialogVisible_objective: false,
      visible: false,
      tableData: [],
      pageSize: 20,
      total: 0,
      currentPage: 1,
      ableTarget: [],
      searchForm: {
        word: ''
      },
      editForm: {
        courseName: '',
        courseId:'',
        assessmentInfo:[],
      },
      editObjective:{
        courseName: '',
        courseId:'',
        cultivationId:'',
        objectiveInfo:[{
          objectiveSupportRatio:1,
          target:{},
        }],
      },
      selectValue:'',
      type:'',
      options: [],
      subAttributeList:[],
    }
  },
  watch: {

  },
  methods: {
    //获取下拉菜单数据
    getCultivationIdList(val) {
      if (this.selectValue==0){
        requestByClient(supplierConsumer, 'POST', 'cultivation/list', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        },res => {
          if (res.data.succ) {
            this.options = res.data.data
          }
        })
      }
      else {
        if(this.type=='editForm'){
          this.editForm.cultivationId=val
          this.getSubAttributeList()
          requestByClient(supplierConsumer, 'POST', 'checkLink/list', {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            cultivationId: val,
            courseId: this.editForm.courseId,
          },res => {
            if (res.data.succ) {
              this.editForm.assessmentInfo = res.data.data
            }
          })
        }
        else if(this.type=='editObjective'){
          this.editObjective.cultivationId=val
          this.getSubAttributeList()
          requestByClient(supplierConsumer, 'POST', 'courseObjective/voList', {
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            cultivationId: val,
            courseId: this.editObjective.courseId,
          },res => {
            if (res.data.succ) {
              this.editObjective.objectiveInfo = res.data.data
              this.editObjective.objectiveInfo.forEach(p=> {
                p.idx_target = p.target.idx_target
                this.subAttributeList.forEach(i => {
                  if (p.idx_target == i.idx_target) {
                    p.attributeId = i.attributeId
                    p.subAttributeId = i.id
                  }
                })
              })
              // eslint-disable-next-line no-console
              console.log('this.editObjective')
              // eslint-disable-next-line no-console
              console.log(this.editObjective)
              // eslint-disable-next-line no-console
              console.log(this.subAttributeList)
            }
          })

        }
      }
    },
    getSubAttributeList(val) {
      if (this.subAttributeList.length==0){
        requestByClient(supplierConsumer, 'POST', 'target/list', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        },res => {
          if (res.data.succ) {
            this.subAttributeList = res.data.data
            // eslint-disable-next-line no-console
            console.log(this.subAttributeList)
          }
        })
      }
      else{
        // eslint-disable-next-line no-console
        console.log('option passin')
        // eslint-disable-next-line no-console
        console.log(val)
        this.editObjective.objectiveInfo.forEach(p=>{
            p.idx_target=p.target.idx_target
            this.subAttributeList.forEach(i=>{
              if (p.idx_target==i.idx_target){
                p.attributeId=i.attributeId
                p.subAttributeId =i.id
              }
            })
        })

      }
    },

    handleEditObjective(row) {
      this.type='editObjective'
      this.getCultivationIdList()
      this.editObjective.courseName=row.name
      this.editObjective.courseId=row.id
      this.dialogVisible_objective = true
    },
    handleAddObjective() {
      this.editObjective.objectiveInfo.push({
        cultivationId: this.editObjective.cultivationId,
        courseId: this.editObjective.courseId,
        objectiveSupportRatio: 1.00,
        target:{},
      }),
        // eslint-disable-next-line no-console
        console.log('handleAddObjective')
      // eslint-disable-next-line no-console
      console.log(this.editObjective)
    },
    submitEditObjective(){
      requestByClient(supplierConsumer, 'POST', 'courseObjective/saveOrUpdate', this.editObjective.objectiveInfo, res => {
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
      let objectiveInfo = this.editObjective.objectiveInfo[index]
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
          this.editObjective.objectiveInfo.splice(index, 1)
        }).catch(() => {
        });
      } else {
        this.editObjective.objectiveInfo.splice(index, 1)
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
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.getList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
    },
    handleAddChecklink() {
      this.editForm.assessmentInfo.push({
        cultivationId: this.editForm.cultivationId,
        courseId: this.editForm.courseId,
        assessmentName:'',
        totalScore:'',
        scoreRatio:'',
      })
    },
    handleEditForm(row) {
      this.type='editForm'
      this.getCultivationIdList()
      this.editForm.courseName=row.name
      this.editForm.courseId=row.id
      // eslint-disable-next-line no-console
      console.log(this.editForm)
      this.dialogVisible = true
      /*
      requestByClient(supplierConsumer, 'POST', 'checkLink/list', {
        courseId: this.editForm.coursdId,
        cultivationId: row.cultivationId
      }, res => {
        if (res.data.succ) {
          this.editForm.assessmentInfo = res.data.data
          this.dialogVisible = true
          // eslint-disable-next-line no-console
          console.log(this.editForm)
        }
        this.loading = false
      })*/
    },
    submitCheckLinks(){
      requestByClient(supplierConsumer, 'POST', 'checkLink/saveOrUpdate', this.editForm.assessmentInfo, res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.dialogVisible = false
        }
      })
    },
    handleDeleteChecklink(index) {
      let assessmentInfo = this.editForm.assessmentInfo[index]
      // eslint-disable-next-line no-console
      console.log(assessmentInfo)
      if (assessmentInfo.cultivationId) {
        this.$confirm('此操作将删除其支撑数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          requestByClient(supplierConsumer, 'POST', 'checkLink/delete', {
            courseId: assessmentInfo.courseId,
            cultivationId:assessmentInfo.cultivationId,
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
          this.editForm.assessmentInfo.splice(index, 1)
        }).catch(() => {
        });
      } else {
        this.editForm.assessmentInfo.splice(index, 1)
      }
    },
  }
}
</script>

<style scoped>

</style>
