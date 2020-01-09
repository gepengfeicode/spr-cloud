package com.cn.microservice.security.smsconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsBeanInit {
    private static Logger logger = LoggerFactory.getLogger(SmsBeanInit.class);
    @Bean
    public SmsAuthenticationSuccessHandler smsAuthenticationSuccessHandler(){
        logger.info("初始化 SmsAuthenticationSuccessHandler Bean對象");
        return new SmsAuthenticationSuccessHandler();
    }

    @Bean
    public SmsAuthenticationFailureHandler smsAuthenticationFailureHandler(){
        logger.info("初始化 SmsAuthenticationFailureHandler Bean對象");
        return new SmsAuthenticationFailureHandler();
    }

//    @Bean
//    public SmsAuthenticationFilter smsAuthenticationFilter(){
//        logger.info("初始化 SmsAuthenticationFilter Bean對象");
//        return new SmsAuthenticationFilter();
//    }

}
