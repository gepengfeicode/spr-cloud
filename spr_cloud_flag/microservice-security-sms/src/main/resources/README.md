**本模块为Spring Boot 集成Spring Security模块实现权限管理**

### 特殊点

1.sql

在存储权限表的时候必须是以以下规则进行存储。

ROLE_*****

示例

```sql
insert into sys_permission(permissionName) values ('ROLE_permission_add');
```

2.注解

value值必须以hashRole('ROLE_开头的权限值')

示例:

```java
@PreAuthorize(value = "hasRole('ROLE_permission_add')")
```

### 基于Redis完成Session共享

1.实现步骤

​	引入pom依赖,启动类增加注解,开启端口1 端口2。

​	实现方式 端口1登录,端口2也可以登录 

2.Pom依赖

```xml
 <!--Session共享 基于Redis-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
        </dependency>
```

3.配置文件配置Redis地址以及缓存的实现方式

```properties
  redis:
    host: 127.0.0.1
    password:
      #Session共享的获取方式
  session:
    store-type: redis
```

4.启动类增加注解

```java
@EnableRedisHttpSession//开启RedisSession共享
```



### 核心配置类的方法介绍

```java
/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //authorizeRequests:声明配置是权限配置
        //antMatchers：路径
        //permitAll：任何权限都可以访问，不需要身份认证
        //anyRequest：任何请求
        //authenticated：认证后才能访问
        //and().csrf().disable()：固定写法，表示csrf拦截失效

        http
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable();
    }
}


access(String) 如果给定的SpEL表达式计算结果为true，就允许访问

anonymous() 允许匿名用户访问

authenticated() 允许认证的用户进行访问

denyAll() 无条件拒绝所有访问

fullyAuthenticated() 如果用户是完整认证的话（不是通过Remember-me功能认证的），就允许访问

hasAuthority(String) 如果用户具备给定权限的话就允许访问

hasAnyAuthority(String…) 如果用户具备给定权限中的某一个的话，就允许访问

hasRole(String) 如果用户具备给定角色(用户组)的话,就允许访问

hasAnyRole(String…) 如果用户具有给定角色(用户组)中的一个的话,允许访问

hasIpAddress(String) 如果请求来自给定ip地址的话,就允许访问

not() 对其他访问结果求反

permitAll() 所有权限无条件允许访问

rememberMe() 如果用户是通过Remember-me功能认证的，就允许访问
```

### 教程地址

<https://blog.csdn.net/yuanlaijike/article/details/80249235>

教程1链接
https://blog.csdn.net/yuanlaijike/article/details/80249235
教程2链接
https://blog.csdn.net/yuanlaijike/article/details/80249869
教程3链接
https://blog.csdn.net/yuanlaijike/article/details/80250389
教程4链接
https://blog.csdn.net/yuanlaijike/article/details/80253922
教程5链接
https://blog.csdn.net/yuanlaijike/article/details/80327880
教程6链接
https://blog.csdn.net/yuanlaijike/article/details/84638745
教程7链接
https://blog.csdn.net/yuanlaijike/article/details/84703690
教程8链接
https://blog.csdn.net/yuanlaijike/article/details/86164160
教程9链接
https://blog.csdn.net/yuanlaijike/article/details/95104553
教程10链接
https://blog.csdn.net/yuanlaijike/article/details/101565313