package com.cn.microservice.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cn.microservice.feign.dao.BusinessDao;

@RestController
public class FeignController {
	@Autowired
	private BusinessDao businessDao;
//	@GetMapping(value = "feign-getBusinessInfo")
	@RequestMapping(value = "feign-getBusinessInfo",method = RequestMethod.GET)
	public String feignGetBusinessInfo(){
		return businessDao.testHttp();
	}
	
	@RequestMapping(value = "feign-getParam",method=RequestMethod.POST)
	public String feignPostGetParam(@RequestParam String userName){
		return businessDao.getParam(userName);
	}
}
