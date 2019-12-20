package com.cn.microservice.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;

@RestController
public class BusinessController {
	@GetMapping(value = "resultSuccess")
	public String testHttp(){
//		List<InstanceInfo> infos =  client.getInstancesById("business");
//		for (InstanceInfo instanceInfo : infos) {
//			System.out.println("端口为:" + instanceInfo.getPort());
//		}
		System.out.println("============================================");
		return "SUCCESS  -01";
	}
	
	@PostMapping(value="getParam")
//	@RequestMapping(value="getParam",method=RequestMethod.POST)
	public String getParam(@RequestBody String userName){
		System.out.println("传入的用户名为:" + userName);
		return "Hello " + userName;
	}
}
