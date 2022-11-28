<template>
  <span>
    <el-form
      :inline="true"
      :model="searchForm"
      class="demo-form-inline"
      style="height: 40px;padding: 10px"
    >
      <el-form-item label="">
        <el-input
          v-model="searchForm.word"
          placeholder="标题/描述"
          clearable
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="getList()"
        >查询</el-button>
      </el-form-item>
      <span style="float: right;margin-right: 30px">
        <el-form-item>
          <el-button
            type="warning"
            @click="handleAddForm()"
          >新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            type="danger"
            @click="handleDelete()"
          >删除</el-button>
        </el-form-item>
      </span>
    </el-form>
    <!--主页面表格显示格式-->
    <el-table
      ref="multipleTable"
      :data="tableData"
      style="width: 98%; margin: 30px"
      height="750"
      tooltip-effect="dark"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column
        prop="cultivationName"
        label="培养方案名称"
        width="200"
      />
      <el-table-column
        prop="id"
        label="编制年份"
        width="100"
      />
      <el-table-column
        prop="cultivationDescribe"
        label="描述"
        width="300"
      />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            @click="handleEditForm(scope.row)"
            >
            方案名称
          </el-button>
          <el-button
            type="primary"
            @click="handleEditAttributes(scope.row)"
          >
            编辑毕业要求
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="编辑培养方案详情"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="70%"
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
            label="培养方案名称"
            prop="cultivationName"
          >
            <el-input
              v-model="editForm.cultivationName"
              type="text"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="编制年份（不允许修改，只能删除后新增）"
            prop="pass"
          >
            <el-input
              v-model="editForm.id"
              type="Integer"
              autocomplete="off"
              readonly="true"
            />
          </el-form-item>
          <el-form-item
            label="描述"
            prop="pass"
          >
            <el-input
              v-model="editForm.cultivationDescribe"
              type="text"
              autocomplete="off"
            />
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

    <el-dialog
      title="添加培养方案"
      :visible.sync="dialogVisible1"
      :close-on-click-modal="false"
      width="70%"
      center
    >
      <div>
        <el-form
          ref="ruleForm"
          :model="addForm"
          status-icon
          class="demo-ruleForm"
        >
          <el-form-item
            label="培养方案名称"
            prop="cultivationName"
          >
            <el-input
              v-model="addForm.cultivationName"
              type="text"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="编制年份"
            prop="pass"
          >
            <el-input
              v-model="addForm.id"
              type="INTEGER"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="描述"
            prop="pass"
          >
            <el-input
              v-model="addForm.cultivationDescribe"
              type="text"
              autocomplete="off"
            />
          </el-form-item>
        </el-form>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button
          type="primary"
          @click="submitAddForm()"
        >确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="添加毕业要求"
      :visible.sync="dialogVisible2"
      :close-on-click-modal="false"
      width="70%"
      center
    >
      <div>
        {{addAttributes.cultivationId}}
        <el-form
          ref="ruleForm"
          :model="addAttributes"
          status-icon
          class="demo-ruleForm"
        >
          <el-form-item
            label="毕业要求"
            prop="Attributes"
          >
            <el-button type="primary" round style="" @click="handleAddAttributes">添加</el-button>
            <br>
            <span :key="index" v-for="(item,index) in addAttributes.attributes" type="text" autocomplete="off">

              <el-form-item label="毕业要求指标点" prop="name" >
                <el-input v-model="item.id" type="text" autocomplete="off" style="width: 5%;margin-top: 10px" />
                <el-input v-model="item.name" type="text" autocomplete="off" style="width: 100%;margin-top: 10px"/>
              </el-form-item>
              <el-form-item label="指标点描述" prop="describe">
              <el-input v-model="item.attributeDescribe" type="text" autocomplete="off" style="width: 91%;margin-top: 10px" />
                </el-form-item>
              <el-button type="danger" icon="el-icon-delete" circle @click="deleteDescribe(index)" />
            </span>
          </el-form-item>
        </el-form>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button
          type="primary"
          @click="submitEditAttributes()"
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

export default {
  name: 'Cultivation',
  mounted() {
    this.getList()
  },
  data() {
    return {
      dialogVisible: false,
      dialogVisible1: false,
      dialogVisible2: false,
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
        cultivationName: '',
        cultivationDescribe: ''
      },
      addForm: {
        id: '',
        cultivationName: '',
        cultivationDescribe: ''
      },
      addAttributes: {
        cultivationId: '',
        attributes: []
      },
      ids: []
    }
  },
  watch: {
    '$store.state.settings.editYear': 'getList'
  },

  methods: {
    handleSelectionChange(val) {
      const results = val.map(item => item.id)
      this.ids = results
    },
    onSubmit() {
    },
    getList() {
      this.dialogVisible = false
      this.dialogVisible1 = false
      this.dialogVisible2 = false
      this.loading = true
      requestByClient(supplierConsumer, 'POST', 'cultivation/list', {
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
      requestByClient(supplierConsumer, 'POST', 'cultivation/update', this.editForm,res => {
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
    submitAddForm() {
      this.dialogVisible1 = false
      this.loading = true
      requestByClient(supplierConsumer, 'POST','cultivation/save', {
        cultivationDescribe: this.addForm.cultivationDescribe,
        cultivationName: this.addForm.cultivationName,
        id: this.addForm.id
      },res => {
        if (res.data.succ) {
          this.$message({
            message: '添加成功',
            type: 'success'
          })
          this.getList()
        }
        this.loading = false
      })
    },

    submitEditAttributes() {
      this.dialogVisible = false
      this.loading = true
      this.addAttributes.attributes.forEach((item)=>{
        item.cultivationId=this.addAttributes.cultivationId
      })
// eslint-disable-next-line no-console
      console.log(this.addAttributes.attributes)
      requestByClient(supplierConsumer, 'POST', 'gradRequirement/update2', this.addAttributes.attributes,res => {
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
    handleEditAttributes(record) {
      this.dialogVisible2 = true
      this.addAttributes.cultivationId = record.id
        requestByClient(supplierConsumer, 'POST','gradRequirement/list', {
          cultivationId: record.id
          }
          , res => {
            if (res.data.succ) {
              this.addAttributes.attributes = res.data.data
            }
            this.loading = false
          })
    },
    deleteDescribe(index) {
      let attributes = this.addAttributes.attributes[index]
      if(attributes.id){
        this.$confirm('此操作将删除其支撑数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let id = attributes.id
          requestByClient(supplierConsumer, 'POST','gradRequirement/delete', {
            id: id,
            cultivationId: this.addAttributes.cultivationId
          },res => {
            if (res.data.succ) {
              this.$message({
                message: '删除成功',
                type: 'success'
              })
            }
          })
          this.addAttributes.attributes.splice(index, 1)
        }).catch(() => {
        });
      }else {
        this.addAttributes.attributes.splice(index, 1)
      }
    },

    handleAddAttributes() {
      this.addAttributes.attributes.push({ attribute: '',reqId: this.editForm.id })
    },
    handleEditForm(record) {
      this.dialogVisible = true
      this.editForm.id = record.id
      this.editForm.cultivationDescribe = record.cultivationDescribe
      this.editForm.cultivationName = record.cultivationName
      requestByClient(supplierConsumer, 'POST','cultivation/list', {
          reqId: record.id
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
      this.addForm.cultivationDescribe = ''
      this.addForm.cultivationName = ''
      this.addForm.id = ''
    },
    handleDelete() {
      this.$confirm('此操作将删除其培养方案, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        requestByClient(supplierConsumer, 'POST','cultivation/del', {
          ids: this.ids
        }, res => {
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
.el-main .el-divider--horizontal{
  margin: 0px 0;
}
</style>
