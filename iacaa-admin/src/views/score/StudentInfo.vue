<template>
  <span>
    <el-form
      :inline="true"
      :model="searchForm"
      class="demo-form-inline"
      style="height: 40px;padding: 10px"
    >
      <!--搜索组件-->
      <el-form-item label="">
        <el-input
          v-model="searchForm.word"
          placeholder="标题/描述"
          clearable
        />
      </el-form-item>
      <el-form-item label="">
        <el-button
          type="primary"
          @click="getList()"
        >查询/全部显示</el-button>
      </el-form-item>
      <!--下拉组件-->
      <span style="float: right;margin-right: 400px">
        <el-form-item>
          选择培养方案版本
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="selectCultivation"
            placeholder="选择培养方案版本"
            @change="getCultivationIdList()"
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
      style="width: 98%; margin: 30px"
      height="750"
      tooltip-effect="dark"
    >
      <!--表头-->
      <el-table-column
        prop="studentId"
        label="学号"
        width="150"
      />
      <el-table-column
        prop="studentName"
        label="姓名"
        width="250"
      />
      <el-table-column
        prop="cultivationId"
        label="培养方案版本"
        width="150"
      />
      <!--/表头-->
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            @click="handleEditForm(scope.row)"
          >修改信息</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     style="margin-left: 10px">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="学生信息编辑"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="90%"
      center
    >
      <div>
        {{editForm.studentName}}
        <el-form
          ref="ruleForm"
          :model="editForm"
          status-icon
          class="demo-ruleForm"
        >
          <el-form-item
            label="学号"
            prop="studentName"
          >
            {{ editForm.studentId }}
          </el-form-item>
          <el-form-item
            label="姓名"
            prop="pass"
          >
            <el-input
              v-model="editForm.studentName"
              type="text"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="适用培养方案版本"
            prop="pass"
          >
            <el-select
              v-model="selectCultivation"
              placeholder="选择培养方案版本"
              @change="getCultivationIdList()"
            >
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.cultivationName"
              :value="item.id"
            />
          </el-select>
          </el-form-item>
        </el-form>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="submitEditForm('editForm')"
        >确 定</el-button>
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

//下拉组件引入
import Vue from 'vue'
import { Select,Option } from 'element-ui'
Vue.use(Select)
Vue.use(Option)

export default {
  name: 'StudentInfo',
  data() {
    return {
      dialogVisible: false,
      dialogVisible_edit:false,
      tableData: [],
      pageSize: 20,
      total: 0,
      currentPage: 1,
      searchForm: {
        word: '',
      },
      editForm: {
        studentId: '',
        studentName: '',
        cultivationId:'',
      },
      selectCultivation: '',
      options: [],
    }
  },
  watch: {
  },
  mounted() {
    this.getList();
    this.getCultivationIdList();
  },
  methods: {
    getList() {
      this.dialogVisible = false
      this.dialogVisible_edit = false
      this.loading = true
      requestByClient(supplierConsumer, 'POST', 'student/listInfo', {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        word: this.searchForm.word,
      },res => {
        if (res.data.succ) {
          this.tableData = res.data.data
          // this.total = res.data.data.total
          // this.pageSize = res.data.data.pageSize
          // this.currentPage = res.data.data.pageNum
        }
        this.loading = false
      })
    },
    getCultivationIdList() {
      if (this.selectCultivation===''){
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
        requestByClient(supplierConsumer, 'POST', 'student/listInfo', {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          cultivationId: this.selectCultivation,
        },res => {
          if (res.data.succ) {
            this.tableData = res.data.data
          }
        })
      }
    },

    handleDelete(row) {
      // eslint-disable-next-line no-console
      console.log('handleDelete')
      // eslint-disable-next-line no-console
      console.log(row)
      this.$confirm('此操作将删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        requestByClient(supplierConsumer, 'POST','student/deleteOne', row
          , res => {
            if (res.data.succ) {
              this.$message({
                message: '删除成功',
                type: 'success'
              })
              this.getList()
            }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEditForm(record) {
      this.dialogVisible = true
      this.editForm.studentName = record.studentName
      this.editForm.studentId = record.studentId
      this.editForm.cultivationId=record.cultivationId
      this.selectCultivation=record.cultivationId
    },
    submitEditForm() {
      this.loading = true
      this.editForm.cultivationId=this.selectCultivation
      requestByClient(supplierConsumer, 'POST', 'student/updateInfo', this.editForm,res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          this.dialogVisible = false
          this.getList()
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
.el-main .el-divider--horizontal{
  margin: 0px 0;
}
</style>
