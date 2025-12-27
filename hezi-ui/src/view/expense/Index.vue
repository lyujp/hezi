<script setup>

</script>

<template>
  <div class="expense-container">
    <div class="title">记账系统</div>
    <header>
      <nav>
        <router-link to="/expense">首页</router-link>
        <router-link to="/expense/record">记账</router-link>
        <router-link to="#">支出分析</router-link>
        <router-link to="#">收入分析</router-link>
        <router-link to="#">消费分析</router-link>
        <router-link to="#">金融账户</router-link>
        <router-link to="#">设置信息</router-link>
      </nav>
    </header>
    <section>
      <router-view />
    </section>
  </div>

</template>

<style scoped>
.expense-container{
  min-height: 100vh;
  background: #f5f5f5;
}
.title{
  font-size: 2.8rem;
  font-weight: bold;
  color: #2c3e50;
  text-align: center;
  border-bottom: 1px solid rgba(202, 202, 202, 0.42);
  padding: 1rem 0;
}
nav{
  width: 1000px;
  margin: 2rem auto;
  display: flex;
  box-shadow: 0 0 10px rgba(181, 180, 180, 0.37);
  a{
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1.1rem;
    background: #FFFFFF;
    height: 3.5rem;
    width: 10rem;
    .active{
      background: #eaeaea;
    }
  }
  a:hover{
    background: #eaeaea;
  }
}
</style>