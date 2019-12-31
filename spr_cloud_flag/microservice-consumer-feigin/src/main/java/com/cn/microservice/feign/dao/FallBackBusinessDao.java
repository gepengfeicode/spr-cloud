package com.cn.microservice.feign.dao;

import org.springframework.stereotype.Component;

@Component
public class FallBackBusinessDao implements BusinessDao {
    @Override
    public String testHttp() {
        return "fall back testHttpMethod()";
    }

    @Override
    public String getParam(String userName) {
        return null;
    }
}
