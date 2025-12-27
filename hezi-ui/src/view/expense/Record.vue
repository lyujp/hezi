<script setup>
import {onMounted, ref} from "vue";
import apiHttp from "../../util/hurl.js";
import { ElMessage } from 'element-plus'

const selectedRecords = ref({
  tradeType: "1",
  category: null,
  amount: null,
  tradeTime: new Date(),
  remark: '',
  account: null,
  currency: "",
  balance: null,
})

const records = ref({
  tradeType: [],
  category: [],
  account: [],
  currency: [],
})

const handleSubmit = async () => {
  const parm = {
    ...selectedRecords.value
  }
  const res = await apiHttp.post('/admin/expense/record',parm)
  if(res.code === 200){
    ElMessage({
      message: '成功',
      type: 'success',
    })
    selectedRecords.value.amount = null;
    selectedRecords.value.remark = null;
    await loadDate();
    handleAccountChange();
  }else{
    ElMessage({
      message: '失败：' + res.code + "," + res.message,
      type: 'error',
    })
  }
}

const handleReset = () => {
  console.log(selectedRecords.value)
  selectedRecords.value = {
    tradeType: "1",
    category: null,
    amount: null,
    tradeTime: new Date(),
    remark: '',
    account: null,
    currency: "",
    balance: null,
  }
}

const handleAccountChange = () => {
  selectedRecords.value.currency = records.value.account.filter(item => item.id === selectedRecords.value.account)[0].currencyId + '';
  selectedRecords.value.balance = records.value.account.filter(item => item.id === selectedRecords.value.account)[0].balance;
}

onMounted(()=>{
  loadDate();
})

const loadDate = async () => {
  records.value = (await apiHttp.get('/admin/expense/get_record_options')).data;
  console.log(records.value)
}

</script>

<template>
  <div class="record-container">
    <div class="title">添加记录</div>
    <div class="content">
      <div class="label">类型</div>
      <div class="input">
        <el-select size="large" v-model="selectedRecords.tradeType" placeholder="请选择">
          <el-option
              v-for="item in records.tradeType"
              :key="item.id"
              :label="item.name"
              :value="item.val">
          </el-option>
        </el-select>
      </div>
      <div class="label">分类</div>
      <div class="input">
        <el-select size="large" v-model="selectedRecords.category" :disabled="selectedRecords.tradeType == null"  placeholder="请选择">
          <el-option
              v-for="item in records.category.filter(item => item.categoryType == selectedRecords.tradeType)"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id">
          </el-option>
        </el-select>
      </div>
      <div class="label">金额</div>
      <div class="input"><el-input type="number" size="large"  v-model="selectedRecords.amount" placeholder="金额"/></div>
      <div class="label">账户</div>
      <div class="input">
        <el-select size="large" v-model="selectedRecords.account" @change="handleAccountChange"  placeholder="请选择">
          <el-option
              v-for="item in records.account"
              :key="item.id"
              :label="item.accountName"
              :value="item.id">
          </el-option>
        </el-select>
      </div>
      <div class="label">余额</div>
      <div class="input"><el-input type="number" disabled size="large" v-model="selectedRecords.balance" placeholder=""/></div>
      <div class="label">币种</div>
      <div class="input">
        <el-select size="large" disabled v-model="selectedRecords.currency" placeholder="">
          <el-option
              v-for="item in records.currency"
              :key="item.id"
              :label="item.name"
              :value="item.val">
          </el-option>
        </el-select>
      </div>
      <div class="label">日期</div>
      <div class="input"><el-date-picker v-model="selectedRecords.tradeTime"
                                         style="width: 100%" size="large" placeholder="交易时间"/></div>
      <div class="label">备注</div>
      <div class="input"><el-input type="textarea" size="large" v-model="selectedRecords.remark" placeholder="备注"/></div>
      <button class="submit" @click="handleSubmit">提交</button>
      <button class="reset" @click="handleReset">重置</button>
    </div>
  </div>
</template>

<style scoped>
.record-container{
  width: 1000px;
  margin: 0 auto;
  min-height: 40vh;
  background: #FFFFFF;
  box-shadow: 0 0 10px rgba(181, 180, 180, 0.37);
  padding: 1rem;
  .content{
    width: 700px;
    margin: 2rem auto;
    .label {
      margin-top: 1rem;
      margin-bottom: 0.5rem;
    }
  }
  button {
    margin-top: 2rem;
    height: 3rem;
    width: 8rem;
    margin-right: 2rem;
  }
  .submit{
    background: #34446a;
    color: #FFFFFF;
  }

  .reset{
    background: #FFFFFF;
    color: #34446a;
    border: 1px solid #34446a;
  }

}
.title{
  font-size: 1.8rem;
  font-weight: bold;
}
</style>