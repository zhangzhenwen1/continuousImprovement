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
        <el-button type="primary" icon="el-icon-upload" @click="handleAdd()"
                 style="margin-left: 10px">增加</el-button>
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
        prop="teacherName"
        label="姓名"
        width="250"
      />
      <el-table-column
        prop="teacherGender"
        label="性别"
        width="150"
      />
      <el-table-column
        prop="teacherBirthDate"
        label="出生日期"
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
        </template>
      </el-table-column>
    </el-table>
    <!-- 增加教师 -->
    <el-dialog
      title="教师信息编辑"
      :visible.sync="dialogVisible_add"
      :close-on-click-modal="false"
      width="90%"
      center
    >
      <div>
        <el-form
          ref="ruleForm"
          :model="editForm"
          status-icon
          class="demo-ruleForm"
        >
          <el-form-item
            label="姓名"
            prop="teacherName"
            class="required"
          >
            <el-input v-model="editForm.teacherName" type="text" clearable />
          </el-form-item>

          <el-form-item
            label="性别"
            prop="pass"
          >
            <el-select
              v-model="editForm.teacherGender"
              placeholder="选择性别"
            >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          </el-form-item>

          <el-form-item label="出生日期" prop="pass">
            <el-date-picker
              v-model="editForm.teacherBirthDate"
              type="date"
              class="full-width-input"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              clearable
              :editable="true"
            ></el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible_add = false">取 消</el-button>
        <el-button
          type="primary"
          @click="submitEditForm()"
        >确 定</el-button>
      </div>
    </el-dialog>
  // 编辑页面
    <el-dialog
      title="教师信息编辑"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="90%"
      center
    >
        {{editForm.teacherName}}
      <el-button type="danger" icon="el-icon-delete" @click="handleDelete(editForm)"
                 style="margin-left: 10px">删除</el-button>
      <div>
        <el-form
          ref="ruleForm"
          :model="editForm"
          status-icon
          class="demo-ruleForm"
        >
          <el-form-item
            label="编号"
            prop="pass"
          >
            {{ editForm.teacherId }}
          </el-form-item>
          <el-form-item
            label="性别"
            prop="pass"
          >
            <el-select
              v-model="editForm.teacherGender"
              placeholder="选择性别"
            >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          </el-form-item>
          <el-form-item label="出生日期" prop="pass">
            <el-date-picker
              v-model="editForm.teacherBirthDate"
              type="date"
              class="full-width-input"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              clearable
              :editable="true"
            ></el-date-picker>
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
import XLSX from 'xlsx';

export default {
  name: 'TeacherInfo',
  data() {
    return {
      dialogVisible: false,
      dialogVisible_add: false,
      dialogVisible_edit: false,
      tableData: [],
      pageSize: 20,
      total: 0,
      currentPage: 1,
      searchForm: {
        word: '',
      },
      editForm: {
        teacherId: '',
        teacherName: '',
        teacherGender: '',
        teacherBirthDate: '',
      },
      selectGender: '',
      options: [{
        value: '男',
        label: '男',
      }, {
        value: '女',
        label: '女',
      }],
      upload_file: "",
      lists: [],
      rules: {
        teacherName: [{
          required: true,
          message: '字段值不可为空',
        }],
      },
    }
  },
  watch: {
  },
  mounted() {
    this.getList();
  },
  methods: {
    getList() {
      this.dialogVisible = false
      this.dialogVisible_edit = false
      this.dialogVisible_add=false
      this.loading = true
      requestByClient(supplierConsumer, 'POST', 'teacher/listInfo', {
        word: this.searchForm.word,
      },res => {
        if (res.data.succ) {
          this.tableData = res.data.data
        }
        this.loading = false
      })
    },
    handleAdd() {
      this.dialogVisible_add=true
      this.editForm.teacherName = ''
      this.editForm.teacherId = ''
      this.editForm.teacherGender=''
      this.editForm.teacherBirthDate=''
    },
    handleEditForm(record) {
      this.dialogVisible = true
      this.editForm.teacherName = record.teacherName
      this.editForm.teacherId = record.teacherId
      this.editForm.teacherGender=record.teacherGender
      this.editForm.teacherBirthDate=record.teacherBirthDate
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
        requestByClient(supplierConsumer, 'POST','teacher/deleteOne', {teacherId: row.teacherId}
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
    submitEditForm() {
      this.loading = true

      requestByClient(supplierConsumer, 'POST', 'teacher/updateInfo', this.editForm,res => {
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
               teacherId: '',
               teacherName: item["姓名"],
               teacherGender: item["性别"],
               teacherBirthDate: item["出生日期"]
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
      requestByClient(supplierConsumer, 'POST', 'teacher/insertBatch', this.lists,res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          this.dialogVisible_add = false
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
