package com.cn.microservice.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 简单实用Hystrix
 */
@RestController
public class HystrixController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/h")
    /*执行hystrix时调用的方法名称 调用方法的入参与返回值必须一致*/
    @HystrixCommand(fallbackMethod="getParamFallBackMethod")
    public String getParam(@RequestParam String name){
        return restTemplate.postForEntity("http://business/resultSuccess",null,String.class/*,name*/).getBody();
    }

    public String getParamFallBackMethod(@RequestParam String name){
        return "Default Message -Name!";
    }

    @GetMapping(value = "getServerIP")
    public String getServerIp(HttpServletRequest request){
        String name = request.getServerName() + request.getServerPort();
        return name;
    }
}
