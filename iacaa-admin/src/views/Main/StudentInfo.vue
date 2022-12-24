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
      <el-form-item label="">
        <el-button
          icon="el-icon-upload"
          type="primary"
          @click="handleImport()"
        >批量导入<input
          type="file"
          style="display: none;"
          id="upload"
          ref="files"
          @change="readExcel"
        /></el-button>
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
import XLSX from 'xlsx';

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
      upload_file: "",
      lists: [],
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

    handleImport() {
      if (this.selectCultivation==='') {
        this.$message({
          message: "请选择导入学生信息所属培养方案版本",
          type: "warning"
        });
      } else {
        this.$refs.files.click();
      }
    },
    readExcel() {
      const loading = this.$loading({
        lock: true,
        text: "文件上传中...",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      })
      // js 获取文件对象
      let fileObj = document.getElementById("upload").files
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
        // eslint-disable-next-line no-console
        console.log(that.upload_file);
      }
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
            that.lists.push({
              studentId: item["学号"],
              studentName: item["姓名"],
              grade: item["现在年级"],
              cultivationId: this.selectCultivation
            })
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
      requestByClient(supplierConsumer, 'POST', 'student/insertBatchInfo', this.lists,res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
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
