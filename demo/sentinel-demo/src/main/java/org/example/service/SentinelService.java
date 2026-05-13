package org.example.service;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SentinelService {

    private static final Random RANDOM = new Random();

    /**
     * 普通业务方法，模拟随机失败
     * 使用 @SentinelResource 进行熔断保护
     */
    @SentinelResource(
            value = "businessMethod",
            fallback = "businessMethodFallback",
            blockHandler = "businessMethodBlockHandler"
    )
    public Map<String, Object> businessMethod(Long id) {
        // 模拟业务逻辑
        System.out.println("执行业务方法，参数 id = " + id);

        // 模拟随机失败：60% 概率抛异常
        if (RANDOM.nextInt(10) < 6) {
            throw new RuntimeException("业务异常：处理失败");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "业务处理成功");
        result.put("data", "id = " + id + " 处理完成");
        return result;
    }

    /**
     * 业务异常时的兜底方法（fallback）
     */
    public Map<String, Object> businessMethodFallback(Long id, Throwable ex) {
        System.out.println("进入 fallback，异常原因：" + ex.getMessage());
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("code", 500);
        fallback.put("message", "【Fallback】业务异常，返回兜底数据");
        fallback.put("error", ex.getMessage());
        return fallback;
    }

    /**
     * 被限流/熔断时的兜底方法（blockHandler）
     */
    public Map<String, Object> businessMethodBlockHandler(Long id, BlockException ex) {
        System.out.println("进入 blockHandler，被 Sentinel 限制：" + ex.getClass().getSimpleName());
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("code", 503);
        fallback.put("message", "【BlockHandler】服务被限流或熔断：" + ex.getClass().getSimpleName());
        return fallback;
    }

    /**
     * 慢调用方法，用于测试熔断
     */
    @SentinelResource(
            value = "slowMethod",
            fallback = "slowMethodFallback"
    )
    public String slowMethod(long ms) throws InterruptedException {
        System.out.println("执行慢方法，延迟 " + ms + " 毫秒");
        Thread.sleep(ms);
        return "慢方法执行成功，延迟 " + ms + " 毫秒";
    }

    /**
     * 慢方法的兜底方法
     */
    public String slowMethodFallback(long ms, Throwable ex) {
        return "【Fallback】慢方法调用失败，延迟 " + ms + " 毫秒，" + ex.getMessage();
    }
}
