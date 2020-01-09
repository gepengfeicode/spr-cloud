package com.cn.microservice.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 实现Session超时(会话结束)
 */
public class SecuritySessionManageInvalidSessionStrategy implements InvalidSessionStrategy {
    private static Logger logger = LoggerFactory.getLogger(SecuritySessionManageInvalidSessionStrategy.class);
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("Session超时用户自动退出登录!");
        request.getRequestDispatcher("/login_session_timer").forward(request,response);
    }
}
