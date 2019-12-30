package com.cn.microservice.security.controller;

import com.cn.microservice.security.domain.User;
import com.cn.microservice.security.repository.UserReponstory;
import com.cn.microservice.security.utils.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//@RestController
@Controller
@RequestMapping
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserReponstory userReponstory;
    /*查询用户角色*/
    @GetMapping(value = "queryUsers")
    @ResponseBody
    @PreAuthorize(value = "hasRole('ROLE_permission_query')")
    public List<User> getUsers(){
        logger.info("用户拥有[{permission_query}]权限!");
        return userReponstory.findAll();
    }
    @GetMapping(value = "addUsers")
    @ResponseBody
    @PreAuthorize(value = "hasRole('ROLE_permission_add')")
    public String addUsers(){
        logger.info("用户拥有[{permission_add}]权限!");
        return "用户新增权限";
    }
    @GetMapping(value = "delUsers")
    @ResponseBody
    @PreAuthorize(value = "hasRole('ROLE_permission_del')")
    public String delUsers(){
        logger.info("用户拥有[{permission_del}]权限!BCryptPasswordEncoder");
        return "用户删除权限";
    }
    @GetMapping(value = "editUsers")
    @ResponseBody
    @PreAuthorize(value = "hasRole('ROLE_permission_edit')")
    public String editUsers(){
        logger.info("用户拥有[{permission_edit}]权限!");
        return "用户更新权限";
    }

    @GetMapping(value = "getUserIp")
    @ResponseBody
    @PreAuthorize(value = "hasRole('ROLE_permission_getIp')")
    public String getUserIp(HttpServletRequest request){
        logger.info("用户拥有[{permission_getIp}]权限!");
        return "用户拥有获取IP权限,Ip地址为:" + IPUtils.getAccessRealIp(request) + "================" + IPUtils.getIpAddr(request);
    }
}
