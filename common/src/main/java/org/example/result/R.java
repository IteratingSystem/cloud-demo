package org.example.result;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data
public class R<T> {
    private Integer code;
    private String message;
    private T data;
    private String timestamp;

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // ==================== 成功 ====================
    public static <T> R<T> success() {
        return new R<>(200, "success", null);
    }

    public static <T> R<T> success(T data) {
        return new R<>(200, "success", data);
    }

    public static <T> R<T> success(String message, T data) {
        return new R<>(200, message, data);
    }

    // ==================== 失败 ====================
    public static <T> R<T> error() {
        return new R<>(500, "系统异常", null);
    }

    public static <T> R<T> error(String message) {
        return new R<>(500, message, null);
    }

    public static <T> R<T> error(Integer code, String message) {
        return new R<>(code, message, null);
    }

    // ==================== 业务异常 ====================
    public static <T> R<T> businessError(String message) {
        return new R<>(1001, message, null);
    }

    // ==================== Sentinel 熔断异常 ====================
    public static <T> R<T> blockError(String message) {
        return new R<>(429, message, null);
    }
}