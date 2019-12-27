package com.cn.microservice.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {
    @RequestMapping(value = {"/login","/"})
    public String showHomePage(Model model) {

        return "/views/login.html";
    }

}
