package com.lyujp.heziservice.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ExpenseDailyTradeEntity {
    private Long id;
    private Long userId;
    private Long accountId;
    private Long categoryId;
    private Long tradeType;
    private Double amount;
    private OffsetDateTime createAt;
    private Long currencyId;
    private String remark;
    private OffsetDateTime tradeTime;
}
