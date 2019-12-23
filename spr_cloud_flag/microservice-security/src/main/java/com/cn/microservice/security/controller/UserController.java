package com.cn.microservice.security.controller;

import com.cn.microservice.security.domain.User;
import com.cn.microservice.security.repository.UserReponstory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserReponstory userReponstory;
    @GetMapping(value = "/")
    public List<User> getUserAll(){
        return userReponstory.findAll();
    }

}
