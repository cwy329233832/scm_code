<template>
    <!-- <div>{{msg}}</div> -->
    <div>
      <el-row class="btnAdd">
        <el-col :xs="2" :sm="2" :md="2" :lg="2">
          <el-button icon="el-icon-circle-plus-outline" type="primary" size="small" @click="handleAdd">新增</el-button>
        </el-col>
      </el-row>
      
      <scm-table   :highlightCurrentRow="true" :stripe="true" :border="true"
        :data="pageResult" :columns="columns" :showBatchDelete="false" 
        @findPage="findPage" @handleEdit="handleEdit"  @handleDelete="handleDelete">
      </scm-table>

      <!--新增编辑界面-->
    <el-dialog :title="operation?'新增':'编辑'" width="40%" :visible.sync="dialogVisible" :close-on-click-modal="false">
      <el-form :model="dataForm" label-width="110px" :rules="dataFormRules" ref="dataForm" :size="size">
        <el-form-item label="ID" prop="id" v-if="false">
          <el-input v-model="dataForm.id" :disabled="true" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="退货申请原因" prop="returnRequestCause">
          <el-input v-model="dataForm.returnRequestCause" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="退货类别" prop="returnType">
          <el-select v-model="dataForm.returnType" placeholder="请选择退货类别">
              <el-option value='0' label="商业退货"></el-option>
              <el-option value="1" label="当次退货"></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="编码" prop="code">
          <el-input v-model="dataForm.code" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注 " prop="memo">
          <el-input v-model="dataForm.memo" auto-complete="off" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :size="size" @click.native="dialogVisible = false">取消</el-button>
        <el-button :size="size" type="primary" @click.native="submitForm" :loading="editLoading">确定</el-button>
      </div>
    </el-dialog>
    </div>
</template>

<script>
import ScmTable from "@/components/table/Scmtable";
import storage from '@/utils/storage'
export default {
  name: "scmzdreturnrequestcause", 
  components: {
    "scm-table": ScmTable
  },
  data() {
    return {
      size: "small",
      columns: [
        { prop: "returnRequestCause", label: "退货申请原因", minWidth: 120 },
        { prop: "returnType", label: "类别", minWidth: 120 , formatter:(row, column, cellValue, index)=>{return cellValue=='1'?'当次退货':'商业退货'}},
        { prop: "code", label: "编码", minWidth: 120 },        
        { prop: "createPersion", label: "创建人", minWidth: 120 },
        { prop: "createDate", label: "创建时间", minWidth: 120, },
        { prop: "memo", label: "备注", minWidth: 120 },
      ],
      pageRequest: {
        page: 1,
        pageSize: 10,
        showTotal: true
      },
      pageResult: {},
      operation: false, // true:新增, false:编辑
      dialogVisible: false, // 新增编辑界面是否显示
      editLoading: false,
      dataFormRules: {
        returnRequestCause: [
          { required: true, message: "请输入退货申请原因", trigger: "blur" },
          { max: 20, message: '长度不能超过20', trigger: "blur"}
        ],
        returnType: [
          { required: true, message: "请选择退货类别", trigger: "change" },
        ],
        code: [
          { required: true, message: "请输入编码", trigger: "blur"},
          { max: 20, message: '长度不能超过20', trigger: "blur"}
        ],
        memo: [
          { required: false, message: "请输入备注", trigger: "blur"},
          { max: 200, message: '长度不能超过200', trigger: "blur"}
        ]
      },
      // 新增编辑界面数据
      dataForm: {
        returnRequestCause: "",
        returnType: "",
        memo: "",
        id:''
      },
      menuData: [],
      menuSelections: [],
      menuLoading: false,
      authLoading: false,
      checkAll: false,
      ownerId:'',
    };
  },
  mounted() {
      this.ownerId = storage.get('ownerId')
  },
  methods: {
    handleAdd: function() {
      this.dialogVisible = true;
      this.operation = true;
      if (this.$refs["dataForm"]!==undefined) {
          this.$refs["dataForm"].resetFields();
      }
      this.dataForm = {
        returnRequestCause: "",
        memo: "",
        ownerId: this.ownerId
      };
    },
    // 显示编辑界面
    handleEdit: function(params) {
      console.log(params)
      this.dialogVisible = true;
      this.operation = false;
      this.dataForm = Object.assign({}, {
        code: params.row.code,
        returnRequestCause: params.row.returnRequestCause,
        memo: params.row.memo,
        returnType: params.row.returnType,
        id: params.row.id,
        ownerId: this.ownerId
      });
    },
    //删除
    handleDelete (data){
      if(data.params.length == 1 && data.params[0] != ""){
        this.$api.dict.delDict({
          url: '/scm/scmZdReturnRequestCause/v1/remove/'+ data.params[0]
        }).then(data.callback)
      }else{
        if(data.params.length>1){
          let ids = ''
          for(let i= 0; i < data.params.length ; i++){
            if(i == data.params.length-1){
              ids += 'ids='+data.params[i]
            }else{
              ids += 'ids='+data.params[i]+'&'
            }
          }
          this.$api.dict.delDictPl({ 
            url: '/scm/scmZdReturnRequestCause/v1/removes?'+ids,
          }).then(data.callback)
        }else{
          return;
        }
      }
    },
    // 编辑
    submitForm: function() {
      this.$refs.dataForm.validate(valid => {
        if (valid) {
            this.editLoading = true;
            let params = Object.assign({}, this.dataForm);
            this.$api.dict.addDict({
              url: '/scm/scmZdReturnRequestCause/v1/save',
              data: params
            }).then(res => {
              this.editLoading = false;
              if (res.state) {
                this.$message({ message: "操作成功", type: "success" ,duration: 800});
                this.dialogVisible = false;
                this.$refs["dataForm"].resetFields();
               
              } else {
                this.$message({
                  message: "操作失败, " + res.shortMessage,
                  type: "error",
                  duration: 800
                });
              }
               this.findPage(null);
            });
        }
      });
    },
    findPage(data) {
      if(data != null){
        this.pageRequest = data.pageRequest;  
      }
      this.$api.dict.getDict({
          url:'/scm/scmZdReturnRequestCause/v1/list',
          data:{
            pageBean: this.pageRequest,
            params: {},
            querys: [],
            sorter: [
              {
                direction: "DESC",
                property: "createDate"
              }
            ]
          }
          
        }).then(res => {
          if (res.state == false) {
            this.$message({
              message: res.shortMessage,
              type: "error",
              duration: 800
            });
            return;
          }
          this.pageResult = res;
          
        }).then(data!=null?data.callback:'')
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.btnAdd {
  padding-bottom: 5px;
}
.paginer {
  margin-top: 5px;
}
.el-select {
  width: 100%;
}
</style>
