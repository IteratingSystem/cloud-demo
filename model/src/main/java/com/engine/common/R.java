package com.engine.common;

import lombok.Data;

/**
 * @Auther WenLong
 * @Date 2025/6/11 16:57
 * @Description 返回对象
 **/
@Data
public class R {
    private Integer code;
    private String message;
    private Object data;

    public static R ok(String message, Object data) {
        R r = new R();
        r.message = message;
        r.data = data;
        r.code = 200;
        return r;
    }
    public static R error(Integer code,String message) {
        R r = new R();
        r.message = message;
        r.code = code;
        return r;
    }
    public static R error(String message, Object data) {
        R r = new R();
        r.message = message;
        r.data = data;
        r.code = 500;
        return r;
    }
}
