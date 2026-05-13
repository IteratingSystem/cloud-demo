package org.example.client;

import org.example.client.fallback.DemoClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "controller-demo", fallback = DemoClientFallback.class)
public interface DemoClient {

    @GetMapping("/demo/controller/get")
    String get();

    @GetMapping("/demo/controller/post")
    String post();


    // ==================== 熔断测试接口 ====================

    /**
     * 模拟慢接口 - 延迟响应
     * 用于触发 Sentinel 熔断
     *
     * 请求示例：GET /demo/controller/slow
     */
    @GetMapping("/demo/controller/slow")
    String slow();

    /**
     * 模拟随机失败接口
     * 有 50% 概率返回错误，用于测试熔断
     *
     * 请求示例：GET /demo/controller/random-fail
     */
    @GetMapping("/demo/controller/random-fail")
    Map<String, Object> randomFail();

    /**
     * 模拟高延迟接口（可配置延迟时间）
     *
     * 请求示例：GET /demo/controller/delay?ms=5000
     */
    @GetMapping("/demo/controller/delay")
    String delay(@RequestParam(value = "ms", defaultValue = "2000") long ms);
}