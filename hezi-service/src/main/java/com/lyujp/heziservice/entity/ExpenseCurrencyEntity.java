package com.lyujp.heziservice.entity;

import lombok.Data;

@Data
public class ExpenseCurrencyEntity {
    private Long id;
    private String currencyCode;
    private String currencyName;
    private Long status;

}
