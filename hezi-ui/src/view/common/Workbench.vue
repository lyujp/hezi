<script setup>
import apiHttp from '../../util/hurl.js'
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
const router = useRouter();
const category = ref([])
const workbench = ref([])

onMounted(async ()=>{
  category.value = (await apiHttp.get('/admin/workbench/category/getlist')).data;
  workbench.value = (await apiHttp.get('/admin/workbench/getlist')).data;
})

const handleClickCard = (url) => {
  router.push(url)
}

</script>

<template>
<div class="workbench-container">
  <header>
    <div class="logo">
      <div class="logo"></div>
      <div class="title">工作台</div>
    </div>
    <div class="user">
      <div class="username">闪电</div>
      <div class="avatar"></div>
    </div>
  </header>

<!--  欢迎信息-->
  <section>
    <div class="welcome">欢迎回来</div>
    <div class="subtitle">请选择需要进入的项目模块</div>
  </section>

<!--  项目列表-->
  <section>
    <div class="category" v-for="categoryItem in category">
      <div class="category-name" v-text="categoryItem.name"></div>
      <div class="item-card-container">
        <div class="item-card" v-for="item in workbench.filter(item => item.categoryId === categoryItem.id)" @click="handleClickCard(item.url)">
          <div class="item-img"><img :src="item.img" alt=""></div>
          <div class="item-title" v-text="item.title"></div>
          <div class="item-subtitle" v-text="item.subtitle"></div>
          <div class="item-footer">
            <div class="item-status" v-text="item.status"></div>
            <div class="item-arrow">&gt;</div>
          </div>
        </div>
      </div>
    </div>
  </section>


</div>
</template>

<style scoped>
.workbench-container{
  background: #f3f5f8;
  min-height: 100vh;
}
header {
  display: flex;
  justify-content: space-between;
  height: 4rem;
  background: #fff;
  box-shadow: 0 0 5px #aaaaaa;
  .logo{
    display: flex;
    align-items: center;
    margin-left: 1rem;
    gap: 1rem;
    .logo{
      background: url("/logo.jpg") no-repeat center center ;
      background-size: cover;
      background-origin: content-box;
      height: 2.7rem;
      width: 2.7rem;
    }
    .title{
      font-size: 1.3rem;
      font-weight: bold;
    }
  }
  .user{
    display: flex;
    align-items: center;
    gap: 1rem;
    .username{
      font-size: 0.9rem;
    }
    .avatar{
      background: url("/logo.jpg") no-repeat center center ;
      background-size: cover;
      background-origin: content-box;
      border-radius: 50%;
      height: 2.7rem;
      width: 2.7rem;
      margin-right: 2rem;
    }
  }
}

section {
  width: 1000px;
  margin: 2rem auto;
}

.welcome{
  font-size: 2rem;
  font-weight: bold;
}
.subtitle{
  font-size: 1rem;
  color: #737373;
}

.category{
  .category-name{
    font-weight: bold;
    border-left: 5px solid #5d78bc;
    padding-left: 1rem;
    margin: 1rem 0;
  }
  .item-card-container{
    display: flex;
    gap: 1rem;
    .item-card{
      height: 280px;
      width: 300px;
      background: #FFFFFF;
      border-radius: 5%;
      padding: 1.5rem;
      box-shadow: 0 0 5px rgba(170, 170, 170, 0.26);
      display: flex;
      flex-direction: column;
      .item-img {
        width: 3rem;
        height: 3rem;
      }
      .item-title {
        font-size: 1.1rem;
        font-weight: bold;
        padding: 1rem 0;
        flex-grow: 0;
      }
      .item-subtitle{
        font-size: 0.9rem;
        color: #737373;
        flex-grow: 1;
      }
      .item-footer{
        border-top: 1px solid rgba(205, 205, 205, 0.35);
        padding: 1.5rem 0;
        margin: 1rem 0 ;
        flex-grow: 0;
        height: 3rem;
        display: flex;
        justify-content: space-between;
        .item-status {
          font-size: 0.9rem;
          color: #5d78bc;
          background: #5d78bc;
        }
        .item-arrow {
          background: rgba(220, 220, 220, 0.34);
          border-radius: 50%;
          height: 2rem;
          width: 2rem;
          display: flex;
          justify-content: center;
          align-items: center;
        }
      }
    }
    .item-card:hover{
      cursor: pointer;
      box-shadow: 0 0 5px rgba(170, 170, 170, 0.5);
      transform: translateY(-5px);

    }
  }


}
</style>