package com.cn.microservice.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.web.bind.annotation.*;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class BusinessController {
	/*@GetMapping(value = "resultSuccess")*/
	@RequestMapping(value = "resultSuccess")
	public String testHttp(HttpServletRequest request, HttpServletResponse response){
//		List<InstanceInfo> infos =  client.getInstancesById("business");
//		for (InstanceInfo instanceInfo : infos) {
//			System.out.println("端口为:" + instanceInfo.getPort());
//		}
		Cookie[] cookie = request.getCookies();
		for (Cookie cookie1:cookie){
			System.out.println(cookie1.getName() + ":" + cookie1.getValue());
		}
		System.out.println("============================================");
		return "SUCCESS  -01";
	}

	@RequestMapping(value="getParam"/*,method=RequestMethod.POST*/)
//	@PostMapping(value="getParam")
	public String getParam(/*@RequestParam(value = "name")*/@RequestBody String name){
		System.out.println("传入的用户名为:" + name);
		return "Hello " + name;
	}
}
