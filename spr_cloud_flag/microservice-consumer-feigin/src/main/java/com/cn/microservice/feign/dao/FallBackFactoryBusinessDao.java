package com.cn.microservice.feign.dao;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FallBackFactoryBusinessDao implements FallbackFactory<BusinessDao> {
    static Logger logger = LoggerFactory.getLogger(FallBackFactoryBusinessDao.class);
    @Override
    public BusinessDao create( Throwable throwable) {
        logger.info("进入Create Method方法　ｔｈｒｏｗａｂｌｅ :[{}]",throwable.getMessage());
        return new BusinessDao() {
            @Override
            public String testHttp() {
                return "返回信息为：" ;
            }

            @Override
            public String getParam(String userName) {
                return null;
            }
        };
    }
}
