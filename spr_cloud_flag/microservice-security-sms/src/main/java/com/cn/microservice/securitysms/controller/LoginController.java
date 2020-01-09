package com.cn.microservice.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    /*@RequestMapping(value = {"/login","/"})
    public String showHomePage(Model model) {
        logger.info("初始化登录页面");
        return "/views/login.html";
    }*/
    @Autowired
    private SessionRegistryImpl sessionRegistryImpl;
    @RequestMapping(value = "login_session_timer")
    @ResponseBody
    public String login_session_timer(){
        return "Sessiong过期,请从新登录";
    }
    @RequestMapping(value = "expireUrl")
    @ResponseBody
    public String expire(){
        return "用户被挤下！";
    }

    @RequestMapping(value = "getUserInfo")
    @ResponseBody
    public String getUserInfo(){
        return SecurityContextHolder.getContext().getAuthentication().getName().toString();
    }




    @RequestMapping("/sms/code")
    @ResponseBody
    public String sms(String mobile, HttpSession session) {
        int code = (int) Math.ceil(Math.random() * 9000 + 1000);

        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("mobile", mobile);
        map.put("code", code);

        session.setAttribute("smsCode", map);

        logger.info("{}：为 {} 设置短信验证码：{}", session.getId(), mobile, code);
        return session.getId() + " 为" + mobile + "设置短信验证码为:" + code;
    }
    /**
     * 清楚Session 指定用户退出
     * sessionRegistry.getAllPrincipals(); 获取所有 principal 信息
     * 通过 principal.getUsername 是否等于输入值，获取到指定用户的 principal
     * sessionRegistry.getAllSessions(principal, false)获取该 principal 上的所有 session
     * 通过 sessionInformation.expireNow() 使得 session 过期
     * @param request
     * @param response
     * @param userName
     * @return
     */
    @RequestMapping("/kick")
    @ResponseBody
    public String removeUserSessionByUsername(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "userName")String userName){
        logger.info("传入的用户名称为:[{}]",userName);
        int count = 0;
        //1.获取Session中所有用户信息
        List<Object> users = sessionRegistryImpl.getAllPrincipals();
        //2.循环集合
        for (Object principal:users) {
            //3.判断是否继承User
            if (principal instanceof User){
                User user = (User) principal;
                //4.判断用户名称是否与传入的用户名称相等
                logger.info("Session内存储的用户信息为:[{}]",user.toString());
                String sessionUserName = user.getUsername();
                if (sessionUserName.equals(userName)){
                    //5获取所有Sessiong信息       includeExpiredSessions 是否包含已经过期的Session,此时不需要包含,只处理未过期的会话即可
                    List<SessionInformation> sessionInformations = sessionRegistryImpl.getAllSessions(principal,false);
                    if(null != sessionInformations && sessionInformations.size()  > 0){
                        for (SessionInformation sessionInformation:sessionInformations) {
                            //6.手动注销
                            sessionInformation.expireNow();
                            count ++ ;
                        }
                        continue;
                    }else {
                        logger.info("用户名:[{}],注销Session失败！",userName);
                    }
                }
            }else {
                logger.info("用户信息异常！");
            }
        }
        return "调用此方法强制过期清除的Sessiong " + count + "个！";
    }
}
