package com.engine.interceptor;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Auther WenLong
 * @Date 2025/6/11 17:25
 * @Description 全局异常处理
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Throwable.class)
    public String throwable(Throwable ex) {
        return ex.getMessage();
    }
}
