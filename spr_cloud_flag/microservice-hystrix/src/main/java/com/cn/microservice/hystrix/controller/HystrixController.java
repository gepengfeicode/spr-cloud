package com.cn.microservice.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 简单实用Hystrix
 */
@RestController
public class HystrixController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/h")
//    @HystrixCommand(fallbackMethod="getParamFallBackMethod")
    public String getParam(@RequestParam String name){
        return restTemplate.postForEntity("http://business/getParam",null,String.class,name).getBody();
    }

    public String getParamFallBackMethod(@RequestParam String name){
        return "Default Message -Name!";
    }

}
