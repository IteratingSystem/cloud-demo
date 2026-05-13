package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    // 内部微服务调用（带负载均衡和 Sentinel）
    @Autowired
    @Qualifier("internalRestTemplate")
    private RestTemplate internalRestTemplate;

    // 外部 API 调用
    @Autowired
    @Qualifier("externalRestTemplate")
    private RestTemplate externalRestTemplate;

    /**
     * 调用内部微服务 - controller-demo 的 GET 接口
     */
    public String callInternalGet() {
        String url = "http://controller-demo/demo/controller/get";
        return internalRestTemplate.getForObject(url, String.class);
    }

    /**
     * 调用内部微服务 - 慢接口（测试熔断）
     */
    public String callInternalSlow() {
        String url = "http://controller-demo/demo/controller/slow";
        return internalRestTemplate.getForObject(url, String.class);
    }

    /**
     * 调用外部 API（示例：httpbin.org）
     */
    public String callExternalApi() {
        String url = "https://httpbin.org/get";
        return externalRestTemplate.getForObject(url, String.class);
    }

    /**
     * 调用外部 API - 带参数
     */
    public String callExternalApiWithParam(String param) {
        String url = "https://httpbin.org/get?param=" + param;
        return externalRestTemplate.getForObject(url, String.class);
    }
}