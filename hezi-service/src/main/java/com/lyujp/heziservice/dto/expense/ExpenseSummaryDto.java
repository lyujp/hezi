package com.lyujp.heziservice.dto.expense;

import lombok.Data;

@Data
public class ExpenseSummaryDto {
    private Double allIncome;
    private Double allSpending;
    private Double balance;
    private Double spendingMonthly;

}
