package org.example.controller;

import org.example.client.DemoClient;
import org.example.exception.BusinessException;
import org.example.result.R;
import org.example.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo/sentinel")
public class SentinelController {

    @Autowired
    private DemoClient demoClient;
    @Autowired
    private SentinelService sentinelService;

    //全局异常处理与统一返回
    @GetMapping("/test")
    public R<String> test() {
        return R.success("hello");
    }
    @GetMapping("/error")
    public R<Void> error() {
        throw new BusinessException("自定义业务异常");
    }
    // ==================== 熔断测试接口 ====================

    /**
     * 模拟慢接口 - 延迟响应
     * 用于触发 Sentinel 熔断
     *
     * 请求示例：GET /demo/controller/slow
     */
    @GetMapping("/slow")
    public String slow() throws InterruptedException {
        return demoClient.slow();
    }

    /**
     * 模拟随机失败接口
     * 有 50% 概率返回错误，用于测试熔断
     *
     * 请求示例：GET /demo/controller/random-fail
     */
    @GetMapping("/random-fail")
    public Map<String, Object> randomFail() {
        return demoClient.randomFail();
    }

    /**
     * 模拟高延迟接口（可配置延迟时间）
     *
     * 请求示例：GET /demo/controller/delay?ms=5000
     */
    @GetMapping("/delay")
    public String delay(@RequestParam(value = "ms", defaultValue = "2000") long ms){
        return demoClient.delay(ms);
    }



    // ==================== Service 层本地方法熔断测试 ====================

    /**
     * 测试 Service 层业务方法（随机失败）
     * 请求示例：GET /demo/sentinel/business?id=123
     */
    @GetMapping("/business")
    public Map<String, Object> business(@RequestParam(value = "id", defaultValue = "1") Long id) {
        return sentinelService.businessMethod(id);
    }
}
