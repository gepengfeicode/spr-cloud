package com.cn.microservice.securitysms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SmsUserService implements UserDetailsService {
    private static Logger logger = LoggerFactory.getLogger(SmsUserService.class);

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("传入的验证码为:[{}]==================================",s);
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("all");
        auths.add(simpleGrantedAuthority);
        User user = new User(s,"root",true,true,true,true,auths);
        return user;
    }
}
