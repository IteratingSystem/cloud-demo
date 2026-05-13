package org.example.client.fallback;

import org.example.client.DemoClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DemoClientFallback implements DemoClient {

    @Override
    public String get() {
        return "【Fallback】GET 请求失败，服务已熔断，这是兜底数据";
    }

    @Override
    public String post() {
        return "【Fallback】POST 请求失败，服务已熔断，这是兜底数据";
    }

    @Override
    public String slow() {
        return "【Fallback】慢接口调用失败，服务已熔断，这是兜底数据";
    }

    @Override
    public Map<String, Object> randomFail() {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("code", 503);
        fallback.put("message", "【Fallback】随机失败接口调用失败，服务已熔断，这是兜底数据");
        return fallback;
    }

    @Override
    public String delay(long ms) {
        return "【Fallback】延迟接口调用失败，请求延迟 " + ms + " 毫秒，服务已熔断，这是兜底数据";
    }
}
