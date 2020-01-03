package com.cn.microservice.securitysms.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * 为实现Security的自动登录儿进行创建的
 */
@Configuration
public class SecurityPersistentTokenRepository {

    @Autowired
    private static Logger logger = LoggerFactory.getLogger(SecurityPersistentTokenRepository.class);
    @Autowired/*为实现自动登录新加*/
    private DruidDataSource druidDataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        logger.info("初始化[ PersistentTokenRepository ],TokenRepository!");
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(druidDataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
