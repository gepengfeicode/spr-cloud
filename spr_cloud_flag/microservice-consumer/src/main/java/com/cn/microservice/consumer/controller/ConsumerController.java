package com.cn.microservice.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.microservice.My_RibbonConfig;


@RestController
//调用的服务名称  使用的轮训策略
@RibbonClient(value="business",configuration=My_RibbonConfig.class)
public class ConsumerController {
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping(value = "getBusinessInfo")
	public String getBusinessInfo(){
		ResponseEntity<String> resultObj = restTemplate.getForEntity("http://business/resultSuccess", String.class);
		return resultObj.getBody();
	}
	
}
