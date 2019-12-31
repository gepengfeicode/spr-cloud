package com.cn.microservice.feign.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cn.microservice.feign.My_FeignConfig;
/**
 * FeignClient 主要操作
 * @author Administrator
 *
 */
//name 执行的是服务名称 configuration执行的配置类
@FeignClient(name="business",configuration=My_FeignConfig.class/*,fallback = FallBackBusinessDao.class*/,fallbackFactory =FallBackFactoryBusinessDao.class )
public interface BusinessDao {
//	@GetMapping(value = "resultSuccess")
	@RequestMapping(value = "feign-getBusinessInfo",method = RequestMethod.GET)
	public String testHttp();
	@RequestMapping(value="getParam",method=RequestMethod.POST)
//	@PostMapping(value="getParam")
	public String getParam(@RequestBody String userName);
}
