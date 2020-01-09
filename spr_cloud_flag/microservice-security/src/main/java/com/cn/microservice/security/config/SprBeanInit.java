package com.cn.microservice.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistryImpl;

@Configuration
public class SprBeanInit {
    @Bean
    public SessionRegistryImpl sessionRegistry(){
        return new SessionRegistryImpl();
    }

}
