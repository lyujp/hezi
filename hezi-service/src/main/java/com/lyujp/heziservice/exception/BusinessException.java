package com.lyujp.heziservice.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private int code;

    // 构造方法
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        this(500, message);
    }
}
