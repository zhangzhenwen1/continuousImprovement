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
      style="width: 98%; margin: 30px"
      height="750"
      tooltip-effect="dark"
    >
      <!--表头-->
      <el-table-column
        prop="cultivationId"
        label="培养方案版本"
        width="150"
      />
      <el-table-column
        prop="name"
        label="毕业要求"
        width="200"
      />
      <el-table-column
        prop="discrible"
        label="描述"
        width="600"
      />
      <!--/表头-->
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-edit"
            @click="handleEditForm(scope.row)"
          >分指标点</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="毕业要求分指标点编辑"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="90%"
      center
    >
      <div>
        {{editForm.cultivationId}}
        <el-form
          ref="ruleForm"
          :model="editForm"
          status-icon
          class="demo-ruleForm"
        >
          <el-form-item
            label="毕业要求"
            prop="name"
          >
            <el-input
              v-model="editForm.name"
              type="text"
              autocomplete="off"
              readonly="true"
            />
          </el-form-item>
          <el-form-item
            label="毕业要求描述"
            prop="pass"
          >
            <el-input
              v-model="editForm.attributeDescribe"
              type="text"
              autocomplete="off"
              readonly="true"
            />
          </el-form-item>
          <el-form-item
            label="分指标点："
            prop="pass"
          >
            <el-button
              type="primary"
              style=""
              @click="handleAddTarget"
            >添加</el-button>
            <br>
            <!--分指标点编辑表格-->
            <span
              v-for="(item,index) in editForm.targets"
              :key="index"
              type="text"
              autocomplete="off"
            >
              <el-form-item
                label="分指标点编号"
                prop="pass"
              >
                <el-input
                  v-model="item.id"
                  type="text"
                  autocomplete="off"
                  style="width: 91%;margin-top: 10px"
                />
              </el-form-item>
              <el-form-item
                label="分指标点描述"
                prop="pass"
              >
                <el-input
                  v-model="item.subAttributeDescribe"
                  type="text"
                  autocomplete="off"
                  style="width: 91%;margin-top: 10px"
                />
              </el-form-item>
              <el-button
                type="danger"
                icon="el-icon-delete"
                circle
                @click="deleteSubAttribute(index)"
              />
            </span>
            <!--/分指标点编辑表格-->
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
  name: 'GradRequirementEdit',
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
        id: '',
        attributeDescribe: '',
        name: '',
        cultivationId:'',
        attributeId:'',
        targets: []
      },
      addForm: {
        attributeDescribe: '',
        name: ''
      },
      selectValue: '',
      options: [],
      ids: [],
    }
  },
  watch: {
    '$store.state.settings.editYear': 'getList'
  },
  mounted() {
    this.getList();
    this.getCultivationIdList();
  },
  methods: {
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
        requestByClient(supplierConsumer, 'POST', 'gradRequirement/list', {
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
    onSubmit() {
    },
    getList() {
      this.dialogVisible = false
      this.dialogVisible1 = false
      this.loading = true
      requestByClient(supplierConsumer, 'POST', 'gradRequirement/list', {
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
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.getList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
    },
    handleClose() {
    },
    submitEditForm() {
      this.dialogVisible = false
      this.loading = true
      // eslint-disable-next-line no-console
      console.log(this.editForm.targets)
      requestByClient(supplierConsumer, 'POST', 'target/update2', this.editForm.targets,res => {
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
    handleEditForm(record) {
      this.dialogVisible = true
      this.editForm.attributeId = record.id
      this.editForm.attributeDescribe = record.attributeDescribe
      this.editForm.name = record.name
      this.editForm.cultivationId=record.cultivationId
      requestByClient(supplierConsumer, 'POST','target/list', {
          attributeId: record.id,
          cultivationId: record.cultivationId,
        }
        , res => {
          if (res.data.succ) {
            this.editForm.targets = res.data.data
          }
          this.loading = false
        })
    },
    handleAddForm() {
      this.dialogVisible1 = true
      this.addForm.attributeDescribe = ''
      this.addForm.name = ''
    },
    handleDelete() {
      this.$confirm('此操作将删除其支撑数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        requestByClient(supplierConsumer, 'POST','gradRequirement/deleteList', {
            ids: this.ids }
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
    handleAddTarget() {
      this.editForm.targets.push({ id: '', subAttributeDescribe:'', attributeId:this.editForm.attributeId, cultivationId:this.editForm.cultivationId })
    },
    deleteSubAttribute(index) {
      let subAttribute = this.editForm.targets[index]
      if(subAttribute.id){
        this.$confirm('此操作将删除其支撑数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          requestByClient(supplierConsumer, 'POST','target/deleteOne', {
            id: subAttribute.id,
            attributeId: subAttribute.attributeId,
            cultivationId: subAttribute.cultivationId,
          },res => {
            if (res.data.succ) {
              this.$message({
                message: '删除成功',
                type: 'success'
              })
            }
          })
          this.editForm.targets.splice(index, 1)
        }).catch(() => {
        });
      }else {
        this.editForm.targets.splice(index, 1)
      }
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
