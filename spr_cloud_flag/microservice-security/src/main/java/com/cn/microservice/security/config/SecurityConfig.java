package com.cn.microservice.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.cn.microservice.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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
    @Autowired/*退出的时候执行的Handlter*/
    private SecurityLoginOutHandler securityLoginOutHandler;
    @Autowired/*为实现自动登录而新增*/
    private SecurityPersistentTokenRepository securityPersistentTokenRepository;
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
                .formLogin()
                //自定义from表单提交地址 提交那个地址才可进入Security内部的登录接口
                .loginProcessingUrl("/security/login")
                //指定表单获取的用户名
                .usernameParameter("username")
                //指定表单获取的密码
                .passwordParameter("password")
                //登录链接
                .loginPage("/views/login.html")
                //登录成功进入的Handler
                .successHandler(securitySuccessHandler)
                //登录失败地址
//                .failureForwardUrl("/views/login_error.html")
                .failureUrl("/views/login_error.html")
                //登录失败进入的Handler 设置此值后 failureForwardUrl 将失效
//                .failureHandler(securityFailHandler)
                //登录成功跳转页面
                .defaultSuccessUrl("/home").permitAll()
                /*.defaultSuccessUrl("/views/home.html").permitAll()*/
                //退出登录所有权限都可以
                .and()
                //退出操作
                .logout()
                //点击退出调用哪个链接可执行Security内置的退出接口
                .logoutUrl("/logout_")
                //退出成功后跳转的页面
                .logoutSuccessUrl("/views/login.html")
                //退出后的处理  重写Handler后 logoutUrl 与 logoutSuccessUrl 将失效
//                .logoutSuccessHandler(securityLoginOutHandler)
                .permitAll()
                .and()
//                开启自动登录
                .rememberMe()
                //实现自动登录 根据token查询数据库的方式
                .tokenRepository(securityPersistentTokenRepository.persistentTokenRepository())
                //有效期 单位秒
                .tokenValiditySeconds(3 * 60)
                //登录时的规则类
                .userDetailsService(userService);
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
        //自定义 密码不加密的情况
//        auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                logger.info("charSequence Info：[{}]",charSequence.toString());
//                return charSequence.toString();
//            }
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                logger.info("用户输入密码:[{}]，数据库存储的密码:[{}]",charSequence.toString(),s);
//                return s.equals(charSequence.toString());
//            }
//        });
        //密码加密的情况
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
