package com.wulang.cloudzookeeperprovider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZkProviderController {

    @RequestMapping("/test")
    public String test(String message){
        System.out.println(message);
        return "hi," + message;
    }
}
