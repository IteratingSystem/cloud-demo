package com.engine;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

/**
 * @Auther WenLong
 * @Date 2025/6/4 9:32
 * @Description 服务发现测试
 **/
@SpringBootTest
public class DiscoveryTest {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    NacosServiceDiscovery nacosServiceDiscovery;

    @Test
    void discoveryClientTest(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println("ip:"+instance.getHost()+"port:"+instance.getPort());
            }
        }
    }

    @Test
    void nacosServiceDiscoveryTest() throws NacosException {
        List<String> services = nacosServiceDiscovery.getServices();
        for (String service : services) {
            System.out.println(service);
            List<ServiceInstance> instances = nacosServiceDiscovery.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println("ip:"+instance.getHost()+"port:"+instance.getPort());
            }
        }
    }
}
