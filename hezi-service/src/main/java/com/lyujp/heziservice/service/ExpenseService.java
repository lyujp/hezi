package com.lyujp.heziservice.service;

import cn.dev33.satoken.stp.StpUtil;
import com.lyujp.heziservice.dto.common.ResDto;
import com.lyujp.heziservice.dto.expense.ExpenseRecordDto;
import com.lyujp.heziservice.dto.expense.ExpenseRecordOptionsDto;
import com.lyujp.heziservice.entity.ExpenseAccountEntity;
import com.lyujp.heziservice.entity.ExpenseAccountFlowEntity;
import com.lyujp.heziservice.entity.ExpenseDailyTradeEntity;
import com.lyujp.heziservice.entity.TxtdmEntity;
import com.lyujp.heziservice.exception.BusinessException;
import com.lyujp.heziservice.mapper.ExpenseMapper;
import com.lyujp.heziservice.mapper.TxtDmMapper;
import com.lyujp.heziservice.util.TxtdmHelper;
import com.lyujp.heziservice.vo.KvVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseMapper expenseMapper;
    private final TxtDmMapper txtDmMapper;
    private final TransactionTemplate transactionTemplate;

    //获取支出记录选项信息
    public ExpenseRecordOptionsDto getRecordOptions(){
        ExpenseRecordOptionsDto expenseSummaryDto = new ExpenseRecordOptionsDto();

        //交易类型
        List<TxtdmEntity> txtdmEntityList = txtDmMapper.getDict();
        List<KvVo> tradeTypeKvVo = txtdmEntityList.stream()
                .filter(txtdmEntity -> txtdmEntity.getName().equals("trade_type"))
                .map(txtdmEntity ->{
                    KvVo kvvo = new KvVo();
                    kvvo.setId(txtdmEntity.getId());
                    kvvo.setName(txtdmEntity.getNote());
                    kvvo.setVal(txtdmEntity.getCode().toString());
                    return kvvo;
                })
                .toList();
        expenseSummaryDto.setTradeType(tradeTypeKvVo);

        //交易分类
        expenseSummaryDto.setCategory(expenseMapper.getCategory());
        expenseSummaryDto.setAccount(expenseMapper.getAccount(Long.parseLong(StpUtil.getLoginId().toString())));

        //货币类型
        List<KvVo> currencyTypeKvVo = txtdmEntityList.stream()
                .filter(txtdmEntity -> txtdmEntity.getName().equals("currency"))
                .map(txtdmEntity ->{
                    KvVo kvvo = new KvVo();
                    kvvo.setId(txtdmEntity.getId());
                    kvvo.setName(txtdmEntity.getNote());
                    kvvo.setVal(txtdmEntity.getCode().toString());
                    return kvvo;
                })
                .toList();
        expenseSummaryDto.setCurrency(currencyTypeKvVo);
        return expenseSummaryDto;
    }

    public ResDto<Void> record(ExpenseRecordDto expenseRecordDto){
        List<TxtdmEntity> txtdmEntityList = txtDmMapper.getDict();
        if(expenseRecordDto == null){
            return ResDto.fail(500,"参数错误");
        }
        if(expenseRecordDto.getTradeType() == null){
            return ResDto.fail("类型不得为空");
        }
        if(expenseRecordDto.getCategory() == null){
            return ResDto.fail("分类不得为空");
        }
        if(expenseRecordDto.getAmount() == null || expenseRecordDto.getAmount() == 0){
            return ResDto.fail("金额不得为空");
        }
        if(expenseRecordDto.getTradeTime() == null){
            expenseRecordDto.setTradeTime(OffsetDateTime.now());
        }
        if(expenseRecordDto.getAccount() == null){
            return ResDto.fail("账户不得为空");
        }
        if(expenseRecordDto.getCurrency() == null){
            return ResDto.fail("币种不得为空");
        }
        if(!TxtdmHelper.isExist(txtdmEntityList,"currency",expenseRecordDto.getCurrency())){
            return ResDto.fail("币种不存在");
        }
        expenseRecordDto.setId(null);
        Object isSuccess = transactionTemplate.execute(status -> {
            try{
                ExpenseDailyTradeEntity expenseDailyTradeEntity = new ExpenseDailyTradeEntity();
                expenseDailyTradeEntity.setAccountId(expenseRecordDto.getAccount());
                expenseDailyTradeEntity.setCategoryId(expenseRecordDto.getCategory());
                expenseDailyTradeEntity.setTradeType(expenseRecordDto.getTradeType());
                expenseDailyTradeEntity.setAmount(expenseRecordDto.getAmount());
                expenseDailyTradeEntity.setCurrencyId(expenseRecordDto.getCurrency());
                expenseDailyTradeEntity.setRemark(expenseRecordDto.getRemark());
                expenseDailyTradeEntity.setTradeTime(expenseRecordDto.getTradeTime());
                expenseDailyTradeEntity.setUserId(StpUtil.getLoginIdAsLong());
                expenseMapper.recordDailyTrade(expenseDailyTradeEntity);
                if(expenseDailyTradeEntity.getId() == null || expenseDailyTradeEntity.getId() < 0){
                    throw new BusinessException("添加失败");
                }
                List<ExpenseAccountEntity> expenseAccountEntitys = expenseMapper.getAccount(StpUtil.getLoginIdAsLong());
                Optional<ExpenseAccountEntity> account = expenseAccountEntitys.stream()
                        .filter(expenseAccountEntity -> Objects.equals(expenseAccountEntity.getId(), expenseRecordDto.getAccount()))
                        .findFirst();
                if(account.isEmpty()){
                    throw new BusinessException("账户不存在");
                }
                double afterBalance = 0.0;
                if(expenseRecordDto.getTradeType() ==1){
                    afterBalance = account.get().getBalance() - expenseRecordDto.getAmount();
                }else{
                    afterBalance = account.get().getBalance() + expenseRecordDto.getAmount();
                }
                ExpenseAccountFlowEntity expenseAccountFlowEntity = new ExpenseAccountFlowEntity();
                expenseAccountFlowEntity.setAccountId(expenseRecordDto.getAccount());
                expenseAccountFlowEntity.setTradeId(expenseDailyTradeEntity.getId());
                expenseAccountFlowEntity.setRecurringExecId(null);
                expenseAccountFlowEntity.setBeforeBalance(account.get().getBalance());
                expenseAccountFlowEntity.setAfterBalance(afterBalance);
                expenseAccountFlowEntity.setAmount(expenseRecordDto.getAmount());
                expenseAccountFlowEntity.setFlowType(1L);
                expenseAccountFlowEntity.setRemark(expenseRecordDto.getRemark());
                expenseAccountFlowEntity.setTradeAt(expenseRecordDto.getTradeTime());

                expenseMapper.recordAccountFlow(expenseAccountFlowEntity);
                if(expenseAccountFlowEntity.getId() == null || expenseAccountFlowEntity.getId() <=0){
                    status.setRollbackOnly();
                    throw new BusinessException("添加失败");
                }
                expenseMapper.updateAccountBalance(StpUtil.getLoginIdAsLong(),expenseRecordDto.getAccount(),afterBalance);
                return "success";
            } catch (Exception e) {
                status.setRollbackOnly();
                return e.getMessage();
            }
        });
        if (isSuccess != null && isSuccess.toString().equals("success")) {
            return ResDto.success();
        }else{
            if (isSuccess != null) {
                return ResDto.fail(isSuccess.toString());
            }else{
                return ResDto.fail(500);
            }
        }
    }
}
