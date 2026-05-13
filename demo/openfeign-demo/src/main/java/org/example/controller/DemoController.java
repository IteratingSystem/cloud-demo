package org.example.controller;

import org.example.client.DemoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/feign")
public class DemoController {
    @Autowired
    private DemoClient client;

    @GetMapping("/get")
    public String get() {
        return client.get();
    }
}
