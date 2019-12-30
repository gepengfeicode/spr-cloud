package com.cn.microservice.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Durid数据源配置
 */
@Configuration
@ConfigurationProperties(prefix = "druid.datasource")
public class DataSourceConfig {
    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    private String url;
    private String password;
    private String username;
    @Bean
    public DruidDataSource druidDataSource(){
        logger.info("初始化 Druid DataSource,属性值为:[{}]",this.toString());
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(this.getUrl());
        druidDataSource.setPassword(this.getPassword());
        druidDataSource.setUsername(this.getUsername());
        return druidDataSource;
    }

    @Override
    public String toString() {
        return "DataSourceConfig{" +
                "url='" + url + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        DataSourceConfig.logger = logger;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
