package com.cn.microservice.securitysms.smsconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录正常时调用onAuthenticationFailure 方法
 */
public class SmsAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(SmsAuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
       logger.info("登录成功！");
       PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.print("短信验证登录成功!");
        printWriter.flush();
        httpServletResponse.setCharacterEncoding("UTF-8");
    }
}
