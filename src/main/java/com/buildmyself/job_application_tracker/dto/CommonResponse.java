package com.buildmyself.job_application_tracker.dto;

import com.buildmyself.job_application_tracker.exception.ErrorCode;

public record CommonResponse<T>(
        String errorCode,
        String message,
        T data
) {
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(
                ErrorCode.SUCCESS.getCode(),
                ErrorCode.SUCCESS.getDefaultMessage(),
                data);
    }

    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<>(
                ErrorCode.SUCCESS.getCode(),
                message,
                data);
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>(
                ErrorCode.SUCCESS.getCode(),
                ErrorCode.SUCCESS.getDefaultMessage(),
                null);
    }

    public static <T> CommonResponse<T> error(ErrorCode errorCode) {
        return new CommonResponse<>(
                errorCode.getCode(),
                errorCode.getDefaultMessage(),
                null);
    }

    public static <T> CommonResponse<T> error(ErrorCode errorCode, String message, T data) {
        return new CommonResponse<>(
                errorCode.getCode(),
                message,
                data);
    }
}
