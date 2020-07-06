
<template>
  <div>
    <v-card-title>
      <v-btn color="primary" @click="addBrand">新增品牌</v-btn>
      <!--搜索框，与search属性关联-->
      <v-spacer />
      <v-flex xs3>
        <v-text-field label="输入关键字搜索" v-model.lazy="search" append-icon="search" hide-details />
      </v-flex>
    </v-card-title>

    <el-table
      :data="tableData"
      @sort-change='sortChange'
      style="width: 100%"
      :default-sort="{prop: 'date', order: 'descending'}"
    >
      <el-table-column prop="id" label="id"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="" label="LOGO" >
         <template slot-scope="scope">
              <img v-if="scope.row.image" :src="scope.row.image" alt="" width="130" height="40">
              <span v-else>无</span>
     </template>
      </el-table-column>
      <el-table-column prop="letter" label="品牌"  sortable='custom'></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="warning" @click="editBrand(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteOpen(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>


    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      background="blue"
      :current-page="currentPage"
      :page-sizes="[5, 15, 20, 30]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="currentTotal"
    ></el-pagination>

    <!--弹出的对话框-->
    <v-dialog max-width="500" v-model="show" persistent scrollable>
      <v-card>
        <!--对话框的标题-->
        <v-toolbar dense dark color="primary">
          <v-toolbar-title>{{isEdit ? '修改' : '新增'}}品牌</v-toolbar-title>
          <v-spacer />
          <!--关闭窗口的按钮-->
          <v-btn icon @click="closeWindow">
            <v-icon>close</v-icon>
          </v-btn>
        </v-toolbar>
        <!--对话框的内容，表单-->
        <v-card-text class="px-5" style="height:400px">
          <brand-form @close="closeWindow" :oldBrand="oldBrand" :isEdit="isEdit" />
        </v-card-text>
        
      </v-card>
    </v-dialog>
   
       
  </div>
</template>

<script>
// 导入自定义的表单组件
import BrandForm from "./BrandForm";
import message from "../../components/messages/index";
export default {

  name: "MyBrands",
  data() {
    
    return {
   
      search: "", // 搜索过滤字段
      currentPage: 1,
      pageSize: 5,
      currentTotal: 0,
      tableData: [],
      sortBy:'',//排序字段
      desc:null,//排序规则
      show: false, // 控制对话框的显示
      oldGoods: {}, // 即将被编辑的商品信息
      isEdit: false, // 是否是编辑
      
    };
  },
  watch: {
    search: {
      // 监视搜索字段
      handler() {
        this.getDataFromServer();
      },
      deep: true
    },
    pageSize: {
      //监控当前页大小
      handler() {
        this.getDataFromServer();
      },
      deep: true
    },
    currentPage: {
      //监控当前页码

      handler() {
        this.getDataFromServer();
      },
      deep: true
    }
  },
  mounted() {
    // 渲染后执行
    // 查询数据
    this.getDataFromServer();
  },
  methods: {
    sortChange(column, prop, order){
      this.sortBy= column.prop;
    //  console.log("column=="+column + 'prop++' + column.prop + 'order--' + column.order)
     if(column.order=='ascending'){
       this.desc= true;
      
     }else if(column.order=='descending'){
       this.desc= false;
     }
      this.getDataFromServer();
     
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      console.log(`当前页: ${val}`);
    },

    getDataFromServer() {
      // 从服务的加载数的方法。
      // 发起请求
      this.$http
        .get("/item/brands/page", {
          params: {
            key: this.search, // 搜索条件
            page: this.currentPage, // 当前页
            rows: this.pageSize ,// 每页大小
            sortBy: this.sortBy, // 排序字段
            desc: this.desc // 排序规则

           
          }
        })
        .then(resp => {
          // 这里使用箭头函数
          console.log(resp);
          this.tableData = resp.data.items;
          this.currentTotal = resp.data.total;
          // 完成赋值后，把加载状态赋值为false
        })
        .catch(error=> {
          this.tableData = [];
          this.currentTotal = 0;
          this.$message({
            type: 'error',
            message: '品牌数据不存在！'
          });
          
        });
    },
    addBrand() {
      // 修改标记
      this.isEdit = false;
      // 控制弹窗可见：
      this.show = true;
      // 把oldBrand变为null
      this.oldBrand = null;
    },
      deleteOpen(val) {
     
        this.$confirm('此操作将永久删除该品牌, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then( () => {
  
            this.handleDelete(val);
            
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },


    handleDelete(val) {

      this.$http
        .delete("/item/brands/delete/" + val.id)
        .then(() =>{

          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.closeWindow();
        })
        .catch(()=> {
          this.$message({
            type: 'error',
            message: '删除失败!'
          })
        });
    },
 
    editBrand(oldBrand) {
      // 根据品牌信息查询商品分类
      this.$http.get("/item/category/bid/" + oldBrand.id).then(({ data }) => {
        // 修改标记
        this.isEdit = true;
        // 控制弹窗可见：
        this.show = true;
        // 获取要编辑的brand
        this.oldBrand = oldBrand;
        // 回显商品分类
        this.oldBrand.categories = data;
      });
    },
   closeWindow() {
      // 重新加载数据
      this.getDataFromServer();
      // 关闭窗口
      this.show = false;
    }
  },
    
    
  components: {
    BrandForm
  }
};
</script>

