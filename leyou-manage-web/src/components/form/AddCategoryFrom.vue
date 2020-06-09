<template>
  <div id="app">
    <v-app id="inspire">
      <v-form
        ref="form"
        v-model="valid"
        lazy-validation
      >
        <v-text-field
          v-model="name"
          :counter="10"
          :rules="nameRules"
          label="商品分类名称"
          required
        ></v-text-field>

        <v-text-field
          v-model="email"
          :rules="emailRules"
          label="父类目编号"
          required
        ></v-text-field>

        <v-select
          v-model="select"
          :items="items"
          :rules="[v => !!v || 'Item is required']"
          label="父节点"
          required
        ></v-select>
        <v-btn
          color="warning"
          @click="cancel"
        >
          取消
        </v-btn>

        <v-btn
          :disabled="!valid"
          color="success"
          class="mr-4"
          @click="sure"
        >
          确认
        </v-btn>
      </v-form>
    </v-app>
  </div>
</template>



<script>
    export default {
        name: "AddCategoryFrom",

    }
</script>

<style scoped>

</style>
