package com.app.bs.booking_system.utils;

public record APIResponse<T>(boolean success, String message, T data) {
    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<>(true, "Success", data);
    }

    public static <T> APIResponse<T> success(T data, String message) {
        return new APIResponse<>(true, message, data);
    }

    public static <T> APIResponse<T> error(String message) {
        return new APIResponse<>(false, message, null);
    }
}