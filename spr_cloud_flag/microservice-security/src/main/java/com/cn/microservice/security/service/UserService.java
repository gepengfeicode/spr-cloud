package com.cn.microservice.security.service;

import com.cn.microservice.security.domain.Permission;
import com.cn.microservice.security.domain.Role;
import com.cn.microservice.security.repository.PermissionReponstory;
import com.cn.microservice.security.repository.RoleReponstory;
import com.cn.microservice.security.repository.UserReponstory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 替换security默认的用户名与权限操作
 */
@Service
public class UserService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserReponstory userReponstory;
    @Autowired
    private RoleReponstory roleReponstory;
    @Autowired
    private PermissionReponstory permissionReponstory;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("输入的用户名为:[{}],线程编号:[{}]",username,Thread.currentThread().getId());
        //从数据库中排查
        com.cn.microservice.security.domain.User userInfo = userReponstory.findByName(username);
        if(null == userInfo){
            throw new NullPointerException("用户名:[{"+username+"}],未从数据库中排查到数据!");
        }
        logger.info("数据库中获取到的用户信息为:[{}]",userInfo.toString());
        //根据用户Id获取角色名称(默认一个用户拥有多个角色)
        List<Role> role = roleReponstory.findRoleByUserName(userInfo.getId());
        if(null == role || role.size () <= 0){
            throw new RuntimeException("用户名:[{"+username+"}],未从数据库中排查到数据!");
        }
        //权限集合
        List<String> permission = new ArrayList<String>();
        for (Role r: role) {
            //根据角色Id获取所有权限Id
            permission.addAll(permissionReponstory.findPermissionNameByRoleId(r.getId()));
        }
        //权限集合对象
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        //循环放入所有权限
        for (String s:permission) {
            logger.info("放入的权限为,[{}]",s);
            auths.add(new SimpleGrantedAuthority(s));
        }
        User user = new User(username,userInfo.getPassword(),true,true,true,true,auths);
        return user;
    }
}
