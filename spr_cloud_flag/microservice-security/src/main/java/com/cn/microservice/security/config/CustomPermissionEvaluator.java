package com.cn.microservice.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private static Logger logger = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

    /**
     *
     * @param authentication   获取当前用户信息
     * @param o                请求的Url
     * @param o1               需要的权限
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        //获取当前登录的用户对象
        User user = (User)authentication.getPrincipal();
        logger.info("User Info:[{}]",user.toString());
        //获取用户对应的角色
        Collection<GrantedAuthority> collection =  user.getAuthorities();
        StringBuffer stringBuffer = new StringBuffer();
        for (GrantedAuthority g:collection) {
            stringBuffer.append(g + ",");
        }
        logger.info("获取到的权限集合为:[{}]",String.valueOf(stringBuffer));
        String permission = String.valueOf(stringBuffer);
        logger.info("请求的地址:[{}],需要的权限:[{}]",o,o1);
        if(permission.contains((CharSequence) o1)){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
