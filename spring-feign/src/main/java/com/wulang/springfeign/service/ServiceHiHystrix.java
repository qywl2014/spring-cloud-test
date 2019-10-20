package com.wulang.springfeign.service;

import org.springframework.stereotype.Component;

@Component
public class ServiceHiHystrix implements ServiceHi {
    @Override
    public String hi(String name) {
        return "hystric   sorry," + name;
    }
}
