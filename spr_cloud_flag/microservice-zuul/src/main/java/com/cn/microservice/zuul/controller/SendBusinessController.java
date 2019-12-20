package com.cn.microservice.zuul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cn.microservice.zuul.utils.PublicParam;

@RestController
public class SendBusinessController {
	
	@Autowired
	private RestTemplate restTemplate;
	@GetMapping(value = "businessSend")
	public String getBusinessResult(){
		System.err.println("发起请求准备通过网关调用Business服务,请求地址【"+PublicParam.BUSINESSURL+"】" );
		return restTemplate.getForObject(PublicParam.BUSINESSURL, String.class);
	}
}
