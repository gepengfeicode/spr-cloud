package com.cn.microservice.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//SpringBoot项目必加依赖
@SpringBootApplication
//声明当前服务是注册中心
@EnableEurekaServer
@RestController
public class EurekaApp {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApp.class, args);
	}
	
	@Value(value = "${name}")
	public String name;
	
	@RequestMapping(value = "getValue")
	//测试获取到的数据到底是application还是botstrap文件内的内容
	public String testBotstrapAndApplicationPrimary(){
		/**
		 * 测试结果为当application与bootstrap文件同时存在使由于优先加载bootstrap文件 导致application文件的配置内容将bootstrap文件内容覆盖
		 */
		return name;
	}
}
