package com.lyujp.heziservice.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ExpenseAccountEntity {
    private Long id;
    private Long userId;
    private String accountName;
    private Long accountType;
    private Long currencyId;
    private Double balance;
    private String remark;
    private OffsetDateTime createAt;
    private Long status;
}
