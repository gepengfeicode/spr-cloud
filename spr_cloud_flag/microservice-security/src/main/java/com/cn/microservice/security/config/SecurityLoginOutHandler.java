package com.cn.microservice.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问 logout 地址
 * 用户进行退出的时候进入此方法
 */
@Component
public class SecurityLoginOutHandler implements LogoutSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(SecurityLoginOutHandler.class);
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(null == authentication){
            return;
        }
        logger.info("进入用户退出方法,当前推出的用户名为:[{}]",authentication.getName());
    }
}
