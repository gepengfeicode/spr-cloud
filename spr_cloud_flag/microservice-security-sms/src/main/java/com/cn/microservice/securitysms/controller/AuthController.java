package com.cn.microservice.securitysms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 登录类
 */
@Controller
@RequestMapping
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @RequestMapping(value = "/home")
    public String home(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Boolean loginOk = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        logger.info("进入跳转核心页面! 登录状态为:[{}],线程编号:[{}]",loginOk,Thread.currentThread().getId());
        List<GrantedAuthority> auths = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority grantedAuthority: auths) {
            logger.info("获取的用户权限为:[{}]",grantedAuthority.getAuthority());
        }
        logger.info("进入跳转核心页面! 登录的用户名儿为:[{}]",userName);
        return "/views/home.html";
    }
}
