package com.cn.microservice.security.smsconfig;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 410L;
    /**
     * 在 UsernamePasswordAuthenticationToken 中该字段代表登录的用户名，
     * 在这里就代表登录的手机号码
     */
    private final Object principal;
    /**
     * 鉴权对象  因实现的短信登录暂时无需存在的必要
     */
//    private Object credentials;
    /**
     * 构建一个没有鉴权的 SmsCodeAuthenticationToken
     */
    public SmsAuthenticationToken(Object principal/*, Object credentials*/) {
        super((Collection)null);
        this.principal = principal;
//        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public SmsAuthenticationToken(Object principal, /*Object credentials,*/ Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        /*this.credentials = credentials;*/
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return null;
    }


    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
//        this.credentials = null;
    }
}
