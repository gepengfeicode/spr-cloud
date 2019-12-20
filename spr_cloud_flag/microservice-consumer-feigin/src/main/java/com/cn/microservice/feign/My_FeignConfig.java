package com.cn.microservice.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class My_FeignConfig {
	/**
	 * 指定feign日志级别
	 * NONE，无记录（DEFAULT）。
		
		BASIC，只记录请求方法和URL以及响应状态代码和执行时间。
		
		HEADERS，记录基本信息以及请求和响应标头。
		
		FULL，记录请求和响应的头文件，正文和元数据。
	 * @return
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.BASIC;
	}
	/**
	 * 调用eureka接口时如果需要设置帐号密码的话在下面进行赋值
	 * @return
	 */
//	@Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("user", "password");
//    }
	
}
