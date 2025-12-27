package com.lyujp.heziservice.dto.expense;

import com.lyujp.heziservice.entity.ExpenseAccountEntity;
import com.lyujp.heziservice.entity.ExpenseCategoryEntity;
import com.lyujp.heziservice.vo.KvVo;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ExpenseRecordOptionsDto {
    private List<KvVo> tradeType;
    private List<ExpenseCategoryEntity> category;
    private List<ExpenseAccountEntity> account;
    private List<KvVo> currency;
}
