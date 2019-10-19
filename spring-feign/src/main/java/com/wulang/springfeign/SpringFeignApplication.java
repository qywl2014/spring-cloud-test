package com.wulang.springfeign;

import com.wulang.springfeign.service.ServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RestController
public class SpringFeignApplication {
    @Autowired
    private ServiceHi serviceHi;

    public static void main(String[] args) {
        SpringApplication.run(SpringFeignApplication.class, args);
    }

    @RequestMapping("/hi")
    public String hi(){
        return "feign:"+serviceHi.hi();
    }
}
