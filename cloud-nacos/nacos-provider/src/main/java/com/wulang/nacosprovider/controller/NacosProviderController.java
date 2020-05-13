package com.wulang.nacosprovider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosProviderController {

    @RequestMapping("/test")
    public String test(String message){
        System.out.println(message);
        return "hi," + message;
    }
}
