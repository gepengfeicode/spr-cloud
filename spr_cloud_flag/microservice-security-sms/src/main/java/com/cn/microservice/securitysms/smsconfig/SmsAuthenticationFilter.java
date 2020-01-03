package com.cn.microservice.securitysms.smsconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 短信登录的鉴权过滤器，模仿 UsernamePasswordAuthenticationFilter 实现
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static Logger logger = LoggerFactory.getLogger(SmsAuthenticationFilter.class);
//    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    //前段表单提交的参数名称 (name指定的名称)
      public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
    //    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
//    private String usernameParameter = "username";
//    private String passwordParameter = "password";
    //前段表单提交的参数名称 (name指定的名称)
    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    //是否仅Post方式进行表单提交
    private boolean postOnly = true;
    //这里复制的默认from表单格式进行处理的,从此可以看出默认from请求地址login请求方法POST
    public SmsAuthenticationFilter() {
//        super(new AntPathRequestMatcher("/login", "POST"));
        //短信登录提交的 from表单路径与请求方式
        super(new AntPathRequestMatcher("/sms/login", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobilenum = this.obtainMobileNumber(request);
//            String mobilenum = "13111111111111111";
            logger.info("[{}],获取到的验证码为:[{}]",this.getClass().getName(),mobilenum);
            mobilenum = mobilenum.trim();
            //传入短信验证码 创建对象
            SmsAuthenticationToken smsAuthenticationToken = new SmsAuthenticationToken(mobilenum);
            this.setDetails(request, smsAuthenticationToken);
            return this.getAuthenticationManager().authenticate(smsAuthenticationToken);
        }
    }
    //原意为获取前段传递的用户名与密码
    /*protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }*/

    /**
     * 获取前段传递的短信验证码
     * @param request
     * @return
     */
    protected String obtainMobileNumber(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }

//    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
//        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
//    }
      protected void setDetails(HttpServletRequest request, SmsAuthenticationToken smsAuthenticationToken) {
          smsAuthenticationToken.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

//    public void setUsernameParameter(String usernameParameter) {
//        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
//        this.usernameParameter = usernameParameter;
//    }
//
//    public void setPasswordParameter(String passwordParameter) {
//        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
//        this.passwordParameter = passwordParameter;
//    }
    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Password parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
//
//    public final String getUsernameParameter() {
//        return this.usernameParameter;
//    }
//
//    public final String getPasswordParameter() {
//        return this.passwordParameter;
//    }
    public final String getMobileParameter() {
        return this.mobileParameter;
    }
}
