package com.cn.microservice.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    /*@RequestMapping(value = {"/login","/"})
    public String showHomePage(Model model) {
        logger.info("初始化登录页面");
        return "/views/login.html";
    }*/
    @RequestMapping(value = "login_session_timer")
    @ResponseBody
    public String login_session_timer(){
        return "登录超时,请从新登录";
    }
    @RequestMapping(value = "expireUrl")
    @ResponseBody
    public String expire(){
        return "用户被挤下！";
    }
}
