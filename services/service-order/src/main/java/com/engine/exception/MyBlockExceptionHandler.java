package com.engine.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.engine.common.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

/**
 * @Auther WenLong
 * @Date 2025/6/11 17:04
 * @Description 自定义Sentinel不满足规则异常处理
 **/
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse response,
                       String s, BlockException e) throws Exception {
        response.setContentType("Application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        R error = R.error(500, "被Sentinel限制了:"+e.getClass().getName());
        String json = mapper.writeValueAsString(error);
        out.write(json);
        out.flush();
        out.close();
    }
}
