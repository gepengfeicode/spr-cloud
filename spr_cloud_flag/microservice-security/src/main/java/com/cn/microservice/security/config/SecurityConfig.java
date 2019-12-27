package com.cn.microservice.security.config;

import com.cn.microservice.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//配置类
@Configuration
//开启 Security 服务
@EnableWebSecurity
//开启全局 Securtiy 注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    Logger logger  = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired/*改变默认的用户查询方法*/
    private UserService userService;
    @Autowired/*登录成功执行*/
    private SecuritySuccessHandler securitySuccessHandler;
    @Autowired/*登录失败执行*/
    private SecurityFailHandler securityFailHandler;
    @Autowired
    private SecurityLoginOutHandler securityLoginOutHandler;
    /**
     * 权限配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("初始化权限与规则");
        http.authorizeRequests()
                //设置无需权限就可以访问的
                /*.antMatchers("/getUsers").permitAll()*/
                //以下路径登录后访问
                .anyRequest().authenticated()
                .and()
                //登录页面地址
                .formLogin().loginProcessingUrl("/security/login").usernameParameter("username").passwordParameter("password")
                .loginPage("/views/login.html").failureForwardUrl("/views/login_error.html")
                .successHandler(securitySuccessHandler)
                .failureHandler(securityFailHandler)
                //登录成功跳转页面
                .defaultSuccessUrl("/home").permitAll()
                /*.defaultSuccessUrl("/views/home.html").permitAll()*/
                //退出登录所有权限都可以
                .and().logout()
                .logoutUrl("/logout_")
                .logoutSuccessUrl("/views/login_out.html")
                //退出后的处理
                .logoutSuccessHandler(securityLoginOutHandler)
                .permitAll();
        //关csrf
        http.csrf().disable();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        logger.info("初始化忽略的文件类型");
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }
    /**
     * 替换默认密码查询方法
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("替换原有的[UserService]");
        auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                logger.info("charSequence Info：[{}]",charSequence.toString());
                return charSequence.toString();
            }
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                logger.info("用户输入密码:[{}]，数据库存储的密码:[{}]",charSequence.toString(),s);
                return s.equals(charSequence.toString());
            }
        });
//        auth.userDetailsService(userService)
//                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
