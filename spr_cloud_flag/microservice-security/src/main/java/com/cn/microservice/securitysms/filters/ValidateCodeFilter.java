package com.cn.microservice.securitysms.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class ValidateCodeFilter extends OncePerRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(ValidateCodeFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("进入过滤器 ValidateCodeFilter!");
        if(urlValidate(request,response)){
            if(validateCode(request,response)){
                logger.info("校验成功进行下一步操作"); //重定向
                Cookie cookie = new Cookie("loginError","SUCCESS!");
                response.addCookie(cookie);
                filterChain.doFilter(request,response);
            }else {
                logger.info("验证码校验校验失败,重新进入登录页面");
                //重定向
                Cookie cookie = new Cookie("loginError","LoginError!");
                response.addCookie(cookie);
//            response.sendRedirect("/");
                request.getRequestDispatcher("/").forward(request,response);
            }
        }else {
            logger.info("此链接无需进行验证码校验:[{}],继续下一步！",request.getServletPath());
            filterChain.doFilter(request,response);
        }
    }

    /**
     * 校验前台验证码
     * @return
     */
    private boolean validateCode(HttpServletRequest request, HttpServletResponse response){
        //系统内部生成的校验码
        Object obj = request.getSession().getAttribute("validateCode");
        //前段获取的校验码
        Object obj_1 = request.getParameter("verifyCode");
        if(null == obj || null == obj_1){
            logger.info("验证码校验失败,未获取验证码！");
            return false;
        }
        //系统生成的验证码 使用toLowerCase为实现不区分大小写英文验证码
        String sessionValiDataCode = obj.toString().toLowerCase();
        //前段获取的验证码  使用toLowerCase为实现不区分大小写英文验证码
        String pageValiDataCode = obj_1.toString().toLowerCase();
        logger.info("前台输入的验证码为:[{}],系统生成的验证码为:[{}]",pageValiDataCode,sessionValiDataCode);
        if(!sessionValiDataCode.equals(pageValiDataCode)){
            logger.info("验证码校验不通过！");
            return false;
        }
        return true;
    }

    /**
     * 获取前置请求地址,防止拦截错误地址
     */
    public boolean urlValidate(HttpServletRequest request,HttpServletResponse response){
        //针对请求 login接口进行拦截
        logger.info(" Key :[{}] Value :[{}]","request.getServletPath()",request.getServletPath());
        logger.info(" Key :[{}] Value :[{}]","request.getServerName()",request.getServerName());
        logger.info(" Key :[{}] Value :[{}]","request.getPathInfo()",request.getPathInfo());
        logger.info(" Key :[{}] Value :[{}]","request.getContextPath()",request.getContextPath());
        logger.info(" Key :[{}] Value :[{}]","request.getMethod()",request.getMethod());
        boolean pathResult =  request.getServletPath().trim().equals("/login".trim());
        boolean methodResult = request.getMethod().toUpperCase().equals("POST");
        logger.info("地址校验结果为:[{}],请求方法校验结果为:[{}]",pathResult,methodResult);
        if( pathResult&&methodResult){
            return true;
        }
        return false;
    }
}
