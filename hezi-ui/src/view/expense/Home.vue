<script setup>
import apiHttp from '../../util/hurl.js'
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
const summary = ref([])

onMounted(async ()=>{
  summary.value = (await apiHttp.get('/admin/expense/get_summary')).data;
})
</script>

<template>
<div class="home-container">
  <div class="title">财务总览</div>
  <div class="subtitle">总收入</div>
  <div class="content" v-text="summary.allIncome"></div>
  <div class="subtitle">总支出</div>
  <div class="content" v-text="summary.allSpending"></div>
  <div class="subtitle">结余</div>
  <div class="content" v-text="summary.balance"></div>
  <div class="subtitle">本月消费</div>
  <div class="content" v-text="summary.spendingMonthly"></div>
  <div class="subtitle">最近记录</div>
</div>
</template>

<style scoped>
.home-container{
  width: 1000px;
  margin: 0 auto;
  min-height: 40vh;
  background: #FFFFFF;
  box-shadow: 0 0 10px rgba(181, 180, 180, 0.37);
  padding: 1rem;
  div{
    margin-top: 1rem;
  }
}
.title{
  font-size: 1.8rem;
  font-weight: bold;
}
.subtitle{
  font-size: 1.2rem;
  font-weight: bold;
}
.content::before{
  display: inline-block;
  content: '￥';
}
</style>