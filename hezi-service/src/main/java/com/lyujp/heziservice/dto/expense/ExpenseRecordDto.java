package com.lyujp.heziservice.dto.expense;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ExpenseRecordDto {
    private Long id;
    private Long tradeType;
    private Long category;
    private Double amount;
    private OffsetDateTime tradeTime;
    private String remark;
    private Long account;
    private Long currency;
}
