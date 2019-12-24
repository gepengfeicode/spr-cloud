package com.cn.microservice.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.cn.microservice.security.domain")
@EnableJpaRepositories(basePackages = "com.cn.microservice.security.repository")
public class SecurityApp {
    public static void main(String[] args) {
       new SpringApplicationBuilder(SecurityApp.class).web(true).run(args);
//        SpringApplication.run(SecurityApp.class,args);
    }
}
