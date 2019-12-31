### 1.配置feign

1.pom文件引入feign模块依赖

```xml
<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-feign</artifactId>
	</dependency>
```
2.启动类中增加注解开启feign请求

@EnableFeignClients

3.声明interface增加@FeignClient(name="business") 该name的值对应spring.application.name值(调用哪个微服务写那个微服务的名称)

4.在接口内填入方法最简单的将原接口代码复制过来将方法体删除保留方法名参数注解值

### 2.请求地址

1.普通Get请求

<http://127.0.0.1:8096/feign-getBusinessInfo>

2.普通Post请求

http://127.0.0.1:8096/feign-getParam

userName:"xxxx"

### 3.重写Feign默认配置

1.创建配置类

```java
@Configuration
public class My_FeignConfig {
	/**
	 * 指定feign日志级别
	 * NONE，无记录（DEFAULT）。
		
		BASIC，只记录请求方法和URL以及响应状态代码和执行时间。
		
		HEADERS，记录基本信息以及请求和响应标头。
		
		FULL，记录请求和响应的头文件，正文和元数据。
	 * @return
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.BASIC;
	}
	/**
	 * 调用eureka接口时如果需要设置帐号密码的话在下面进行赋值
	 * @return
	 */
//	@Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("user", "password");
//    }
	
}	
```

2.在Interface中进行指定

```java
@FeignClient(name="business",configuration=My_FeignConfig.class)	
```

### 4.Feign第一次请求超时

1.因hystrix超时时间导致以下配置可设置请求超时时间

```xml
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=3000
或直接将超时时间禁用保证每次都能获取到响应
hystrix.command.default.execution.timeout.enabled=false 
```

### 5.Feign集成Hystrix

1.插入依赖

```xml
 <!--断路器依赖-->
      <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-hystrix</artifactId>
      </dependency>	
```

2.执行feign的fallback类 此类需集成feign的接口进行实现

 

```java
@FeignClient(name="business",configuration=My_FeignConfig.class,fallback = FallBackBusinessDao.class)

@Component
public class FallBackBusinessDao implements BusinessDao {
    @Override
    public String testHttp() {
        return "fall back testHttpMethod()";
    }

    @Override
    public String getParam(String userName) {
        return null;
    }
}
```

3.fallbackFactory

不可同时使用fallback与fallbackFactory

```java
@FeignClient(name="business",configuration=My_FeignConfig.class/*,fallback = FallBackBusinessDao.class*/,fallbackFactory =FallBackFactoryBusinessDao.class )
/*==========================================分割线===============================*/

@Component
public class FallBackFactoryBusinessDao implements FallbackFactory<BusinessDao> {
    static Logger logger = LoggerFactory.getLogger(FallBackFactoryBusinessDao.class);
    @Override
    public BusinessDao create( Throwable throwable) {
        logger.info("进入Create Method方法　ｔｈｒｏｗａｂｌｅ :[{}]",throwable.getMessage());
        return new BusinessDao() {
            @Override
            public String testHttp() {
                return "返回信息为：" ;
            }

            @Override
            public String getParam(String userName) {
                return null;
            }
        };
    }
}
```

