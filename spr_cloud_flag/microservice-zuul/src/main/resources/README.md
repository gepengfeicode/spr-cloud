# Zuul概念



# Zuul使用方法

1.增加Pom.xml依赖

```xml
<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zuul</artifactId>
		</dependency>
```

2.开启Zuul代理

要启用它，使用`@EnableZuulProxy`注释Spring Boot主类，并将本地调用转发到相应的服务。

```java
@EnableZuulProxy
```

