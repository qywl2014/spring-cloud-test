package com.wulang.springfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-hi",fallback = ServiceHiHystrix.class)
public interface ServiceHi {
    @RequestMapping("/hi")
    String hi(@RequestParam(value = "name") String name);
}
