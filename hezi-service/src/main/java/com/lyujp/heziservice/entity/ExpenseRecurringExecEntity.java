package com.lyujp.heziservice.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ExpenseRecurringExecEntity {
    private Long id;
    private Long recurringId;
    private Long dailyTradeId;
    private OffsetDateTime executeTime;
    private OffsetDateTime createAt;
}
