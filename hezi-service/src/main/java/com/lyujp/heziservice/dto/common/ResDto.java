package com.lyujp.heziservice.dto.common;

import lombok.Data;

@Data
public class ResDto<T> {

    private Integer code;
    private String message;
    private T data;

    public ResDto() {
    }
    public ResDto(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResDto<T> success() {
        return new ResDto<>(200, "", null);
    }

    public static <T> ResDto<T> success(T data) {
        return new ResDto<>(200, "", data);
    }

    public static <T> ResDto<T> success(String message) {
        return new ResDto<>(200, message, null);
    }

    public static <T> ResDto<T> success(Integer code, String message) {
        return new ResDto<>(code, message, null);
    }

    public static <T> ResDto<T> fail(Integer code, String message) {
        return new ResDto<>(code, message, null);
    }

    public static <T> ResDto<T> fail(Integer code) {
        return new ResDto<>(code, "", null);
    }

    public static <T> ResDto<T> fail(String message) {
        return new ResDto<>(500, message, null);
    }

    public static <T> ResDto<T> of(Integer code, String message, T data) {
        return new ResDto<>(code, message, data);
    }
}