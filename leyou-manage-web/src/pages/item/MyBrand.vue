<template>
  <div>
    <v-layout class="px-2 pb-2">
      <v-flex>
        <v-btn color="blue" @click="addBrand">新增品牌</v-btn>
      </v-flex>
      <v-flex class="xs4">
        <v-text-field label="搜索" v-model="search" append-icon="search" hide-details/>
      </v-flex>
    </v-layout>

      <v-data-table
        :headers="headers"
        :items="brands"
        :pagination.sync="pagination"
        :total-items="totalBrands"
        :loading="loading"
        class="elevation-1"
      >
        <template slot="items" slot-scope="props">
          <tr>
            <td class="text-xs-center">{{ props.item.id }}</td>
            <td class="text-xs-center">{{ props.item.name }}</td>
            <td class="text-xs-center">{{ props.item.letter }}</td>
            <td class="text-xs-center"><img :src="props.item.image" style="align-content: center ; width: 100px;height: 50px" ></td>
            <td class="text-xs-center">
              <v-btn icon >
                <v-icon color="grey lighten-1">edit</v-icon>
              </v-btn>
              <v-btn icon >
                <v-icon color="grey lighten-1">delete</v-icon>
              </v-btn>
            </td>
          </tr>
        </template>

      </v-data-table>

  </div>

</template>

<script>
    export default {
      name: "MyBrand",
      data() {
        return {
          totalBrands: 0,
          brands: [],
          loading: true,
          search: '',
          pagination: {},
          headers: [
            {text: 'id', align: 'center', sortable: true, value: 'id',},
            {text: '名称', align: 'center', value: 'name'},
            {text: '首字母', align: 'center', sortable: true, value: 'letter'},
            {text: 'LOGO', align: 'center', value: 'image'},
            {text: '操作', align: 'center'},
          ],
        }
      },
      created(){
        this.getBrands();
      },
      watch:{
        pagination: { // 监视pagination属性的变化
          deep: true, // deep为true，会监视pagination的属性及属性中的对象属性变化
          handler() {
            // 变化后的回调函数，这里我们再次调用getDataFromServer即可
            this.getBrands();
          }
        },
        search: { // 监视搜索字段
          handler() {
            this.getBrands();
          }
        }
      },
      methods:{
        addBrand(){

        },
        getBrands() {
          this.loading= true;
          this.$http.get("/item/brands/page", {
            params: {
              key: this.search, // 搜索条件
              page: this.pagination.page,// 当前页
              rows: this.pagination.rowsPerPage,// 每页大小
              sortBy: this.pagination.sortBy,// 排序字段
              desc: this.pagination.descending// 是否降序
            }
            }
          )
            .then(
              resp => {
                this.brands = resp.data.items;
                this.totalBrands = resp.data.total;
                this.loading = false;
              }
            )
            .catch(function (error) {
              console.log(error);
            });


          // setTimeout(()=>{
          //     // this.brands = [
          //     //   {id: 1,name: '华为p30',letter: 'H',image: 'http://img5.imgtn.bdimg.com/it/u=1588337193,1548388694&fm=26&gp=0.jpg'},
          //     //   {id: 2,name: '小米2',letter: 'X',image: 'http://img1.imgtn.bdimg.com/it/u=1623580565,3290430678&fm=15&gp=0.jpg'},
          //     //   {id: 3,name: '三星note20',letter: 'S',image: 'http://img5.imgtn.bdimg.com/it/u=1588337193,1548388694&fm=26&gp=0.jpg'},
          //     //   {id: 4,name: '锤子R3',letter: 'C',image: 'http://img5.imgtn.bdimg.com/it/u=1588337193,1548388694&fm=26&gp=0.jpg'},
          //     //   { id:5,name: '苹果10',letter: 'P',image: 'http://img5.imgtn.bdimg.com/it/u=1588337193,1548388694&fm=26&gp=0.jpg'},
          //     // ]
          //
          //   }
          //   ,1000)




        }
      }



    }




</script>

<style scoped>

</style>
