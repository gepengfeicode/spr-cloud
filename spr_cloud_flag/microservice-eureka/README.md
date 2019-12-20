## 介绍

本模块为springcloud注册中心



1.在配置*bootstrap*和application文件后发现application如果同已配置在两个文件内都存在那么他会使用application文件也就是说优先加载的*bootstrap*文件 

示例文件名

*bootstrap*.properties

*bootstrap*.yml

application.properties

application.yml

## 1.创建Eureka注册中

1.Pom增加eureka依赖

```xml
  <!-- 注册中心配置 -->
  	<dependency>
  		<groupId>org.springframework.cloud</groupId>
  		<artifactId>spring-cloud-starter-eureka-server</artifactId>
  	</dependency>
<!-- 安全认证 注册中心设置账号密码 -->
  	<dependency> 
   		 	<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
  </dependencies>
```

2.增加application.properties配置文件

```properties
#端口名称
server.port=8092
#服务名称 
spring.application.name=eureka
#设置为false代表不注册自己服务
eureka.client.register-with-eureka=false
#由于注册中心的只测就是维护服务示例,他并不需要去检索服务,所以设置为false
eureka.client.fetch-registry=false
#地址 本地ip
eureka.instance.hostname=127.0.0.1

#spring-boot-starter-security  的作用
#在application.properties登陆注册中心账号密码
security.user.name=root
security.user.password=root
#访问地址 默认为自己的服务就是注册中心  spring-boot-starter-security 
eureka.client.serviceUrl.defaultZone=http://root:root@127.0.0.1:8092/eureka/

## 心跳间隔	服务续约任务的调度时间 多少秒调度一次 心跳
eureka.instance.lease-renewal-interval-in-seconds= 7000
## 服务失效时间： 如果多久没有收到请求，则可以删除服务
eureka.instance.lease-expiration-duration-in-seconds = 7000
#使用IP注册  默认是本机 电脑名
eureka.instance.perferIpAddress=true

#測試botstrap和application的優先級
#name=application	
```

3.声明本服务为注册中心

```java
//SpringBoot项目必加依赖
@SpringBootApplication
//声明当前服务是注册中心
@EnableEurekaServer
```

## 2.eureka信息

客户端每30秒发送一次心跳给Eureka注册中心,如果90秒没有一次心跳的那们这个服务将在Eureka注册中心中下线

## 3.Eureka高可用

需在host里面配置

127.0.0.01 peer1 peer2

```properties
---
spring:
  profiles: peer1
eureka:
  instance:
    hostname: peer1
  client:
    serviceUrl:
      defaultZone: http://peer2/eureka/

---
spring:
  profiles: peer2
eureka:
  instance:
    hostname: peer2
  client:
    serviceUrl:
      defaultZone: http://peer1/eureka/	
```

