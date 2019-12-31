package com.cn.microservice.security;

import com.cn.microservice.security.filters.ValidateCodeFilter;
import com.cn.microservice.security.servlet.ValidateCodeServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.cn.microservice.security.domain")
@EnableJpaRepositories(basePackages = "com.cn.microservice.security.repository")
@ServletComponentScan(basePackages = "com.cn.microservice.security")
public class SecurityApp {
    private static Logger logger = LoggerFactory.getLogger(SecurityApp.class);
    public static void main(String[] args) {
       new SpringApplicationBuilder(SecurityApp.class).web(true).run(args);
//        SpringApplication.run(SecurityApp.class,args);
    }
    //注册Servlet
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        logger.info("初始化Servlet:[{}]",ValidateCodeServlet.class.getName());
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new ValidateCodeServlet());
        servletRegistrationBean.addUrlMappings("/getVerifyCode");
        return servletRegistrationBean;
    }

    //注册Filter
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(){
//        logger.info("初始化Filter:[{}]",ValidateCodeFilter.class.getName());
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new ValidateCodeFilter());
//        filterRegistrationBean.addUrlPatterns("/login");
//        return filterRegistrationBean;
//    }
}
