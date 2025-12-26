<script setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import apiHttp from '../../util/hurl.js'
const router = useRouter();

const username = ref('');
const password = ref('');
const successMsg = ref('');
const errMsg = ref('');
const showMsg = (type,msg) =>{
  errMsg.value = '';
  successMsg.value = '';
  if(type === 'success'){
    successMsg.value = msg;
  }else{
    errMsg.value = msg;
  }
}

const submit = () => {
  if(!username.value || !password.value
      || username.value.length === 0 || username.value.length === 0) {
    showMsg('error','用户名或密码不得为空')
  }
  apiHttp.post('/login',{
    username: username.value,
    password: password.value
  }).then(res => {
    if(res.code === 200){
      showMsg('success','登录成功')
      setTimeout(()=>{
        router.push('/workbench')
      },1000)
    }else{
      showMsg('error',res.message)
    }
  })
}
</script>

<template>
  <div class="login-container">
    <div class="left">
      欢迎回来
    </div>
    <div class="login-content">
      <div class="login-card">
        <div class="title">用户登录</div>
        <div class="success" v-if="successMsg">
          {{ successMsg }}
        </div>
        <div class="error" v-if="errMsg">
          {{  errMsg }}
        </div>
        <div class="login-label">用户名</div>
        <div class="username">
          <input type="text" v-model="username" placeholder="请输入用户名">
        </div>
        <div class="login-label">密码</div>
        <div class="password">
          <input type="password" v-model="password" placeholder="请输入密码">
        </div>
        <div class="submit" @click="submit">
          登录
        </div>
        <div class="login-footer">
          <span>忘记密码？</span> | <span>注册新账号</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container{
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 30rem;
  background: radial-gradient(circle at 20% 50%, rgba(100, 181, 246, 0.1) 0%, transparent 50%),
  radial-gradient(circle at 40% 80%, rgba(76, 175, 80, 0.1) 0%, transparent 50%),
  radial-gradient(circle at 80% 20%, rgba(147, 112, 219, 0.1) 0%, transparent 50%);

  .left{
    font-size: 2rem;
    font-weight: bold;
    text-shadow: 0 0 0.1rem #4c4c4c;
  }

  .login-content {
    background: #fff;
    padding: 1.5rem 2rem;
    box-shadow: 0 0 1rem #cacaca;
    .login-card{
      .title {
        font-size: 1.5rem;
        padding: 1rem 0;
        text-align: center;
      }
      .login-label {
        font-size: 1.1rem;
        margin: 0.8rem 0;
      }
      input {
        height: 2.5rem;
        width: 20rem;
        border: 0.1rem solid #cacaca;
        padding: 0.1rem 1rem;
      }
      .submit {
        margin: 0.8rem 0;
        background: #4CAF50;
        height: 2.5rem;
        text-align: center;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #FFFFFF;
        border-radius: 0.2rem;
        cursor: pointer;
      }
      .success {
        border: 1px solid #4CAF50;
        background: #d8edda;
        color: #4CAF50;
        font-size: 0.9rem;
        text-align: center;
        padding: 0.5rem 0;
      }
      .error {
        border: 1px solid #af4c4c;
        background: #edd8d8;
        color: #af4c4c;
        font-size: 0.9rem;
        text-align: center;
        padding: 0.5rem 0;
      }
    }
  }

}
</style>