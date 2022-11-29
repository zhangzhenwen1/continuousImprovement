<template>
  <div style="padding: 20px">
    <el-table
      ref="multipleTable"
      :data="tableData"
      style="width: 100%"
      height="750"
      tooltip-effect="dark">
      <el-table-column
        prop="courseId"
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
          <el-button type="primary" :disabled="scope.row.editStatus === 0" @click="handleEditForm(scope.row)">编辑课程考核环节</el-button>
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
        <el-form :model="editingForm" status-icon ref="ruleForm" class="demo-ruleForm">
          <el-form-item label="课程名称" prop="pass">
            <div style="font-size: 18px;color: #1a1a1a">{{ editingForm.courseName }}</div>
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
            <span v-for="(item,index) in editingForm.assessmentInfo" type="text" autocomplete="off">
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
      year: localStorage.getItem('editYear'),
      dialogVisible: false,
      visible: false,
      tableData: [],
      pageSize: 20,
      total: 0,
      currentPage: 1,
      ableTarget: [],
      searchForm: {
        word: ''
      },
      editingForm: {
        courseName: '',
        courseId:'',
        assessmentInfo:[],
      },
      selectValue:'',
      options: [],
    }
  },
  watch: {
    '$store.state.settings.editYear': 'getList'
  },
  methods: {
    //获取下拉菜单数据
    getCultivationIdList(val) {
      if (val==null){
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
        this.editingForm.cultivationId=val
        // eslint-disable-next-line no-console
        console.log(this.editingForm.courseId)
        requestByClient(supplierConsumer, 'POST', 'checkLink/list', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          cultivationId: val,
          courseId: this.editingForm.courseId,
        },res => {
          if (res.data.succ) {
            this.editingForm.assessmentInfo = res.data.data
          }
        })
        // eslint-disable-next-line no-console
        console.log(this.editingForm)
      }
    },
    handleEditForm(row) {
      this.getCultivationIdList()
      this.editingForm.courseName=row.name
      this.editingForm.courseId=row.id
      // eslint-disable-next-line no-console
      console.log(this.editingForm)
      this.dialogVisible = true
      /*
      requestByClient(supplierConsumer, 'POST', 'checkLink/list', {
        courseId: this.editingForm.coursdId,
        cultivationId: row.cultivationId
      }, res => {
        if (res.data.succ) {
          this.editingForm.assessmentInfo = res.data.data
          this.dialogVisible = true
          // eslint-disable-next-line no-console
          console.log(this.editingForm)
        }
        this.loading = false
      })*/
    },
    submitCheckLinks(){
      // eslint-disable-next-line no-console
      console.log(this.editingForm.assessmentInfo)
      requestByClient(supplierConsumer, 'POST', 'checkLink/saveOrUpdate', this.editingForm.assessmentInfo, res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.dialogVisible = false
        }
      })
    },
    handleAddChecklink() {
      this.editingForm.assessmentInfo.push({
        cultivationId: this.editingForm.cultivationId,
        courseId: this.editingForm.courseId,
        assessmentName:'',
        totalScore:'',
        scoreRatio:'',
        })

    },
    handleDeleteChecklink(index) {
      let assessmentInfo = this.editingForm.assessmentInfo[index]
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
          this.editingForm.assessmentInfo.splice(index, 1)
        }).catch(() => {
        });
      } else {
        this.editingForm.assessmentInfo.splice(index, 1)
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
          // eslint-disable-next-line no-console
          console.log(this.tableData)
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
  }
}
</script>

<style scoped>

</style>
