package com.cn.microservice.securitysms.smsconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录异常时调用onAuthenticationFailure 方法
 */
public class SmsAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(SmsAuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败！");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.print("短信验证登录失败!");
        printWriter.flush();
        httpServletResponse.setCharacterEncoding("UTF-8");
    }
}
