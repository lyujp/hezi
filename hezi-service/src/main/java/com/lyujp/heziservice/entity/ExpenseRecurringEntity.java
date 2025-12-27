package com.lyujp.heziservice.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ExpenseRecurringEntity {
    private Long id;
    private Long userId;
    private String expenseName;
    private Long categoryId;
    private Long accountId;
    private Long currencyId;
    private Double amount;
    private Long recurringType;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Long status;
    private String remark;
    private OffsetDateTime createAt;
}
