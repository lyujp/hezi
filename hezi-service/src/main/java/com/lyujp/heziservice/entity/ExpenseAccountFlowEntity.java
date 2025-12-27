package com.lyujp.heziservice.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ExpenseAccountFlowEntity {
    private Long id;
    private Long accountId;
    private Long tradeId;
    private Long recurringExecId;
    private Double beforeBalance;
    private Double afterBalance;
    private Double amount;
    private Long flowType;
    private String remark;
    private OffsetDateTime createAt;
    private OffsetDateTime tradeAt;
}
