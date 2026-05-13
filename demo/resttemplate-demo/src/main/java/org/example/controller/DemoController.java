package org.example.controller;

import org.example.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo/rest")
public class DemoController {

    @Autowired
    private ExternalApiService externalApiService;

    /**
     * 调用内部微服务 - GET 接口
     * GET /rest/internal/get
     */
    @GetMapping("/internal/get")
    public String callInternalGet() {
        return externalApiService.callInternalGet();
    }

    /**
     * 调用内部微服务 - 慢接口（测试熔断）
     * GET /rest/internal/slow
     */
    @GetMapping("/internal/slow")
    public String callInternalSlow() {
        return externalApiService.callInternalSlow();
    }

    /**
     * 调用外部 API
     * GET /rest/external
     */
    @GetMapping("/external")
    public String callExternal() {
        return externalApiService.callExternalApi();
    }

    /**
     * 调用外部 API（带参数）
     * GET /rest/external/param?value=hello
     */
    @GetMapping("/external/param")
    public String callExternalWithParam(@RequestParam(defaultValue = "test") String value) {
        return externalApiService.callExternalApiWithParam(value);
    }
}