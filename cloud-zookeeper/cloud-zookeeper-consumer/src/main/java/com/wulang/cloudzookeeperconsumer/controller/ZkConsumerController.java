package com.wulang.cloudzookeeperconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@EnableFeignClients
public class ZkConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private AppClient appClient;

    @FeignClient("testZookeeperProvider")
    interface AppClient {

        @RequestMapping(path = "/test", method = RequestMethod.GET)
        String test();

    }

    @RequestMapping("/test")
    public String test(String message) {
        return "hi," + message;
    }

    @PostConstruct
    public void init() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println(instance.getHost());
                System.out.println(instance.getPort());
                System.out.println(instance.getUri());
                System.out.println(restTemplate.getForObject(instance.getUri() + "/test?message=335", String.class));
            }
        }
        System.out.println(appClient.test());
        System.out.println(restTemplate.getForObject("http://testZookeeperProvider/test?message=335", String.class));
        System.out.println(services);
    }
}
