### 基础使用

1.增加pom依赖

```xml
 <!--断路器依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>
```

2.启动类增加Hystrix启动注解

```java
/*Hystrix开启断路器*/
@EnableCircuitBreaker
```

3.接口上使用

```java
@Autowired
    private RestTemplate restTemplate;
    @GetMapping("/h")
    /*执行hystrix时调用的方法名称 调用方法的入参与返回值必须一致*/
    @HystrixCommand(fallbackMethod="getParamFallBackMethod")
    public String getParam(@RequestParam String name){
        return restTemplate.postForEntity("http://business/getParam",null,String.class,name).getBody();
    }

    public String getParamFallBackMethod(@RequestParam String name){
        return "Default Message -Name!";
    }

```

