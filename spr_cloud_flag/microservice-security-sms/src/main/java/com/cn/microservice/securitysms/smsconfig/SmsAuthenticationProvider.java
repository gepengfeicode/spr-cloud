package com.cn.microservice.security.smsconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SmsAuthenticationProvider implements AuthenticationProvider {
    private static Logger logger = LoggerFactory.getLogger(SmsAuthenticationProvider.class);
    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //转换成SMS认证对象
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        logger.info("【{}】,获取到的短信验证码为:[{}]",this.getClass().getName(),smsAuthenticationToken.getPrincipal());
        String mobileNum = (String) smsAuthenticationToken.getPrincipal();
        //校验验证码
        checkSmsCode(mobileNum);
        //获取对象信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobileNum);
        SmsAuthenticationToken smsAuthenticationTokenResult = new SmsAuthenticationToken(userDetails,userDetails.getAuthorities());
        smsAuthenticationToken.setDetails(smsAuthenticationToken.getDetails());
        return smsAuthenticationToken;
    }

    private void checkSmsCode(String mobile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String inputCode = request.getParameter("smsCode");

        Map<String, Object> smsCode = (Map<String, Object>) request.getSession().getAttribute("smsCode");
        if(smsCode == null) {
            throw new BadCredentialsException("未检测到申请验证码");
        }

        String applyMobile = (String) smsCode.get("mobile");
        int code = Integer.parseInt((String) smsCode.get("code"));
        if(!applyMobile.equals(mobile)) {
            throw new BadCredentialsException("申请的手机号码与登录手机号码不一致");
        }
        if(code != Integer.parseInt(inputCode)) {
            throw new BadCredentialsException("验证码错误");
        }
    }

    /**
     * 方法决定了这个 Provider 要怎么被 AuthenticationManager 挑中，我这里通过 return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication)，处理所有 SmsCodeAuthenticationToken 及其子类或子接口。
     * ————————————————
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
