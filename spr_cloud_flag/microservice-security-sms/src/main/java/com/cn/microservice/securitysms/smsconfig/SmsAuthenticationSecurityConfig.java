package com.cn.microservice.security.smsconfig;

import com.cn.microservice.security.service.SmsUserService;
import com.cn.microservice.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsAuthenticationSecurityConfig  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired/*改变默认的用户查询方法*/
    private SmsUserService smsUserService;
    @Autowired
    private SmsAuthenticationSuccessHandler smsAuthenticationSuccessHandler;
    @Autowired
    private SmsAuthenticationFailureHandler smsAuthenticationFailureHandler;
//    @Autowired
//    private SmsAuthenticationFilter smsAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //创建过滤器
        SmsAuthenticationFilter smsCodeAuthenticationFilter = new SmsAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(smsAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(smsAuthenticationFailureHandler);

        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(smsUserService);
        http.authenticationProvider(smsAuthenticationProvider).addFilterBefore(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
