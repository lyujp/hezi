<script setup>

</script>

<template>
  <div class="home-container">
    <div class="content">

      <div class="logo"></div>

      <div class="desc">
        探索无限可能&nbsp;&lt;/.
      </div>

      <div class="icp">
        <a href="https://beian.miit.gov.cn/" target="_blank">新ICP备2024011966号</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  background: radial-gradient(circle at 20% 50%, rgba(100, 181, 246, 0.1) 0%, transparent 50%),
  radial-gradient(circle at 40% 80%, rgba(76, 175, 80, 0.1) 0%, transparent 50%),
  radial-gradient(circle at 80% 20%, rgba(147, 112, 219, 0.1) 0%, transparent 50%);
}
.content{
  width: 400px;
  height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;

}

.logo{
  background: url("/logo.jpg") no-repeat center center;
  background-size: cover;
  height: 200px;
  width: 200px;
  border-radius: 50%;
  animation: spin 40s linear infinite,
  breath 2s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100%{
    transform: rotate(360deg);
  }
}
@keyframes breath {
  0%,100%{
    box-shadow: #8f8f8f 0 0 10px;
  }
  50%{
    box-shadow: #373737 0 0 20px;
  }
}


.desc{
  font-size: 25px;
  text-align: center;
  margin-top: 100px;
  text-shadow: #8f8f8f 0 0 1px;
  color: #858585;
  display: inline-block;

  width: 0;
  border-right: 3px solid white;

  white-space: nowrap;
  overflow: hidden;
  animation: typing 1s steps(40, end) forwards,
  blink-cursor 1.5s step-end infinite;

}
.desc:hover{
  text-shadow: #4e4e4e 0 0 2px;
}

@keyframes typing {
  0%{
    width: 0;
  }
  100%{
    width: 200px;
  }
}

@keyframes blink-cursor {
  0%,100%{
    border-color: transparent;
  }
  50% {
    border-color: #bababa;
  }
}
.icp{
  width: 100vw;
  position: absolute;
  bottom: 30px;
  text-align: center;
  font-size: 12px;
  color: #b1b1b1;
}
</style>