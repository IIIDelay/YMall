<!--
  - Copyright (c) 2023. 版权归III_Delay所有
  -->

<template>
  <!--查询表单-->
  <el-form :inline="true" class="demo-form-inline">

    <!-- 一级分类 -->
    <el-form-item label="一级分类">
      <el-select
        v-model="category1Id"
        placeholder="请选择"
        @change="category1Changed">
        <el-option
          v-for="category in categoryList1"
          :key="category.id"
          :label="category.name"
          :value="category.id"/>
      </el-select>
    </el-form-item>

    <!-- 二级分类 -->
    <el-form-item label="二级分类">
      <el-select
        v-model="category2Id"
        placeholder="请选择"
        @change="category2Changed">
        <el-option
          v-for="category in categoryList2"
          :key="category.id"
          :label="category.name"
          :value="category.id"/>
      </el-select>
    </el-form-item>

    <!-- 三级分类 -->
    <el-form-item label="三级分类">
      <el-select
        v-model="category3Id"
        placeholder="请选择"
        @change="category3Changed">
        <el-option
          v-for="category in categoryList3"
          :key="category.id"
          :label="category.name"
          :value="category.id"/>
      </el-select>
    </el-form-item>

  </el-form>

</template>

<script>
import prop from '@/api/components/CategorySelector'

export default {

  data() {
    return {
      // 查询表单数据
      category1Id: null,
      category2Id: null,
      category3Id: null,
      categoryList1: [],
      categoryList2: [],
      categoryList3: []
    }
  },

  // 初始化一级类别
  created() {
    prop.getCategory1().then(response => {
      this.categoryList1 = response.data
    })
  },

  methods: {
    // 切换二级类别
    category1Changed() {
      prop.getCategory2(this.category1Id).then(response => {
        this.category2Id = null
        this.category3Id = null
        this.categoryList2 = response.data

        this.$emit('listenOnSelect', this.category1Id, 1)
      })

      // 清空属性列表
      this.attrInfoList = null
    },

    // 切换三级类别
    category2Changed() {
      prop.getCategory3(this.category2Id).then(response => {
        this.category3Id = null
        this.categoryList3 = response.data

        this.$emit('listenOnSelect', this.category2Id, 2)
      })
    },

    // 显示属性列表
    category3Changed() {
      const category = this.categoryList3.find(item => {
        return this.category3Id === item.id
      })
      console.log(category)

      // 子组件向父组件传值
      this.$emit('listenOnSelect', this.category3Id, 3)
      this.$emit('listenOnCateSelect', this.category3Id, category.name)
    }

  }

}
</script>

