package com.lyujp.heziservice.mapper;

import com.lyujp.heziservice.dto.expense.ExpenseRecordOptionsDto;
import com.lyujp.heziservice.dto.expense.ExpenseSummaryDto;
import com.lyujp.heziservice.entity.ExpenseAccountEntity;
import com.lyujp.heziservice.entity.ExpenseAccountFlowEntity;
import com.lyujp.heziservice.entity.ExpenseCategoryEntity;
import com.lyujp.heziservice.entity.ExpenseDailyTradeEntity;
import com.lyujp.heziservice.vo.KvVo;

import java.util.List;

public interface ExpenseMapper {
    ExpenseSummaryDto getSummary(Long userId);
    List<ExpenseCategoryEntity> getCategory();
    List<ExpenseAccountEntity> getAccount(Long userId);
    void recordDailyTrade(ExpenseDailyTradeEntity expenseDailyTradeEntity);
    void recordAccountFlow(ExpenseAccountFlowEntity expenseAccountFlowEntity);

    void updateAccountBalance(Long userId,Long accountId, Double balance);
}
