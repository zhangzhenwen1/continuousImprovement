<template>
  <span style="width: 100%">
    <el-form :inline="true" :model="searchForm" class="demo-form-inline" style="height: 40px;padding: 10px">
      <el-form-item label="">
        <el-input v-model="searchForm.word" placeholder="标题/描述" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getAttributeList">查询</el-button>
      </el-form-item>
      <!--下拉组件-->
      <span style="float: right;padding-right: 320px">
        <el-form-item>
          选择培养方案版本
        </el-form-item>
        <el-form-item>
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
      </span>
    </el-form>
    <el-table
      ref="multipleTable"
      :data="tableData"
      style="width: 100%;padding-left: 30px"
      height="750"
      tooltip-effect="dark"
    >
      <el-table-column
        prop="cultivationId"
        label="培养方案版本"
        width="150"
      />
      <el-table-column
        prop="name"
        label="毕业要求"
        width="100"
      />
      <el-table-column prop="targets" type="expand" label="分指标点" width="1000">
        <template slot-scope="scope">
          <el-table :data="scope.row.targets" stripe>
            <el-table-column
              prop="subAttributeDescribe"
              label="分指标点描述"
              width="500"
            />
            <el-table-column label="操作">
              <template slot-scope="scope_sub">
                <el-button type="primary" icon="el-icon-edit" circle @click="handleTargetEditForm(scope_sub.row,scope.row)" />
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="指标点支撑编辑"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="50%"
      center
    >
      <div>
        <el-form ref="ruleForm" :model="targetEditForm" status-icon class="demo-ruleForm">
          <el-form-item label="毕业要求" prop="name">
            {{targetEditForm.attributeId}}
            {{targetEditForm.attributeDescribe}}
            <!--el-input v-model="targetEditForm.attributeDescribe" disabled type="text" autocomplete="off" /-->
          </el-form-item>
          <el-form-item label="指标点" prop="subAttributeDescribe">
            {{targetEditForm.attributeId}}.{{targetEditForm.subAttributeId}}
            {{targetEditForm.subAttributeDescribe}}
            <!--el-input v-model="targetEditForm.subAttributeDescribe" disabled type="text" autocomplete="off" /-->
          </el-form-item>
          <el-form-item label="支撑课程:" prop="pass">
            <el-button type="primary" round style="" @click="handleAddTarget">添加</el-button>
            <br>
            <!--eslint-disable-next-line-->
            <span v-for="(item,index) in targetEditForm.course" type="text" autocomplete="off">
              <el-select
                v-model="item.courseId"
                placeholder="选择课程"
                clearable filterable style="width: 60%;margin-top: 10px">
                <el-option
                  v-for="item1 in courses"
                  :key="item1.id"
                  :label="item1.name"
                  :value="item1.id"
                />
              </el-select>
              <el-input-number v-model="item.supportRatio" :min="0.1" :max="1" :step="0.1" label="权重系数" style="width: 30%;margin-top: 10px" />
              <el-button type="danger" icon="el-icon-delete" circle @click="deleteDescribe(index)" />
            </span>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitTargetEditForm()">确 定</el-button>
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
  </span>
</template>

<script>
import { requestByClient } from '@/utils/HttpUtils'
import { supplierConsumer } from '@/utils/HttpUtils'
export default {
  name: 'Target',
  data() {
    return {
      dialogVisible: false,
      dialogVisible1: false,
      visible: false,
      tableData: [],
      pageSize: 20,
      total: 0,
      currentPage: 1,
      searchForm: {
        word: '',
      },
      editForm: {
        //id: '',
        attributeDescribe: '',
        name: '',
        targets: []
      },
      addForm: {
        attributeDescribe: '',
        name: ''
      },
      courses: [],
      ids: [],
      targetEditForm: {
        id: '',
        attributeId: '',
        attributeDescribe:'',
        subAttributeId:'',
        subAttributeDescribe: '',
        cultivationId:'',
        course: []
      },
      selectValue: '',
      options: [],

    }
  },
  watch: {
    '$store.state.settings.editYear': 'getReqList'
  },
  mounted() {
    this.getAttributeList()
    this.getCourseList()
    this.getCultivationIdList()
  },
  methods: {
    //获取下拉菜单数据
    getCultivationIdList(val) {
      if (val==null){
        requestByClient(supplierConsumer, 'POST', 'cultivation/list', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          word: this.searchForm.word,
        },res => {
          if (res.data.succ) {
            this.options = res.data.data
          }
        })
      }
      else {
        requestByClient(supplierConsumer, 'POST', 'gradRequirement/voList', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          cultivationId: val,
        },res => {
          if (res.data.succ) {
            this.tableData = res.data.data
          }
        })
      }
    },
    getCourseList() {
      this.dialogVisible = false
      this.dialogVisible1 = false
      requestByClient(supplierConsumer, 'POST', 'course/list', {
      }, res => {
        if (res.data.succ) {
          this.courses = res.data.data
        }
      })
    },
    //获取毕业要求列表
    getAttributeList() {
      requestByClient(supplierConsumer, 'POST', 'gradRequirement/voList', {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        word: this.searchForm.word
      }, res => {
        if (res.data.succ) {
          this.tableData = res.data.data
          // this.total = res.data.data.total
          // this.pageSize = res.data.data.pageSize
          // this.currentPage = res.data.data.pageNum
        }
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
    handleClose(done) {
    },
    handleTargetEditForm(scope_sub, scope) {
      this.dialogVisible = true
      this.targetEditForm.subAttributeId = scope_sub.id
      this.targetEditForm.subAttributeDescribe = scope_sub.subAttributeDescribe
      this.targetEditForm.attributeId = scope.id
      this.targetEditForm.attributeDescribe=scope.attributeDescribe
      this.targetEditForm.cultivationId=scope.cultivationId
      requestByClient(supplierConsumer, 'POST', 'courseTarget/list', {
          cultivationId: this.targetEditForm.cultivationId,
          attributeId:this.targetEditForm.attributeId,
          subAttributeId: this.targetEditForm.subAttributeId
      }
      , res => {
        if (res.data.succ) {
          this.targetEditForm.course = res.data.data
          // eslint-disable-next-line no-console
          console.log(this.targetEditForm.course)
        }
        this.loading = false
      })
    },
    handleAddForm() {
      this.dialogVisible1 = true
      this.addForm.attributeDescribe = ''
      this.addForm.name = ''
    },
    handleAddTarget() {
      this.targetEditForm.course.push({
        courseId:'',
        cultivationId: this.targetEditForm.cultivationId,
        attributeId:this.targetEditForm.attributeId,
        subAttributeId: this.targetEditForm.subAttributeId,
        supportRatio: 1.0
      })
    },
    deleteDescribe(index) {
      let courseTarget = this.targetEditForm.course[index]
      if(courseTarget){
        this.$confirm('此操作将删除其支撑数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          requestByClient(supplierConsumer, 'POST', 'courseTarget/deleteOne', {
            cultivationId: courseTarget.cultivationId,
            attributeId:courseTarget.attributeId,
            subAttributeId: courseTarget.subAttributeId,
            courseId: courseTarget.courseId
          }, res => {
            if (res.data.succ) {
              this.$message({
                message: '删除成功',
                type: 'success'
              })
            } else {
              this.$message.warning(res.data.msg)
            }
            this.loading = false
          })
          this.targetEditForm.course.splice(index, 1)
        }).catch(() => {
        });
      }else {
        this.targetEditForm.course.splice(index, 1)
      }

    },
    submitTargetEditForm() {
      this.loading = true
      requestByClient(supplierConsumer, 'POST', 'courseTarget/saveOrUpdate', this.targetEditForm.course, res => {
        if (res.data.succ) {
          this.dialogVisible = false
          this.$message({
            message: '修改成功',
            type: 'success'
          })
        } else {
          this.$message.warning(res.data.msg)
        }
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
.el-table__header tr,
.el-table__header th {
  padding: 0;
  height: 40px;
  line-height: 50px;
}
.el-table__body tr,
.el-table__body td {
  padding: 0;
  height: 40px;
  line-height: 30px;
}
.el-pagination{
  text-align: right;
}
.el-form{
  text-align: left;
}
.dialog-footer{
  margin-top: 0px;
}
.demo-form-inline{
  margin-left: 50px;
}
</style>
