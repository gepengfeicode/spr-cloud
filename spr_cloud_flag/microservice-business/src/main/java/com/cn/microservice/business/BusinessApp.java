package com.cn.microservice.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.netflix.discovery.DiscoveryClient;

@SpringBootApplication
@EnableEurekaClient
public class BusinessApp {
	public static void main(String[] args) {
		SpringApplication.run(BusinessApp.class, args);
	}
}
