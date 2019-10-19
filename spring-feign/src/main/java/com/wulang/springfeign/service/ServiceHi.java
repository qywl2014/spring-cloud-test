package com.wulang.springfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "service-hi")
public interface ServiceHi {
    @RequestMapping("/hi")
    String hi();
}
