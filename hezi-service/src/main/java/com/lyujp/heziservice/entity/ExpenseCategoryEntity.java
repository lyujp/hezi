package com.lyujp.heziservice.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ExpenseCategoryEntity {
    private Long id;
    private String categoryName;
    private Long categoryType;
    private Long parentId;
    private OffsetDateTime createAt;
    private Long status;
}
