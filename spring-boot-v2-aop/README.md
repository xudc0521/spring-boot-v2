# SpringBoot + AOP + 自定义注解实现统一日志处理 

>在实际开发中，我们经常需要对接口方法打印出日志，比如参数等。如果每个方法里都要手动去写，那样代码显得太冗余了，而且日志风格也参差不齐。\
>本文将使用Spring Boot、Spring AOP结合自定义注解，实现统一的日志处理。

## 添加依赖
因为我将会使用json序列化，所以加入了`fastjson`的依赖.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.47</version>
</dependency>
```

## 主启动类
如果使用`Spring Initializr`生成项目，则无须手动编写。
```java
@SpringBootApplication
public class SpringBootAopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAopApplication.class, args);
    }
}
```
## 编写注解
```java
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Target(ElementType.METHOD) // 定义在方法上
@Documented
public @interface ShowLog {
    String value() default "";
}
```

## 编写Aspect
使用`@Aspect`注解声明是一个切面，并且使用`@Component`注解加入到IoC容器中。
```java
@Aspect
@Component
@Slf4j
public class AnnotationLogAspect {

    @Pointcut("execution(public * com.xudc.springboot.web.*.*(..))")
    public void aopLog(){
    }

    @Before("aopLog() && @annotation(showLog)")
    public void doBefore(JoinPoint joinPoint, ShowLog showLog) {
        Object[] args = joinPoint.getArgs();
        log.info("参数={}", JSON.toJSONString(args));
        Signature signature = joinPoint.getSignature();
        log.info("方法={}", signature.getName());
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer url = request.getRequestURL();
        log.info("请求地址={}", url);

        Map<String, Object> params = new HashMap<>(16);
        request.getParameterMap().forEach((key, values) -> {
            if (values.length == 1) {
                params.put(key, values[0]);
            } else {
                params.put(key, values);
            }
        });
        log.info("请求参数={}", JSON.toJSONString(params));
        log.info("注解value={}", showLog.value());
    }
}
```

## 编写接口
```java
@RestController
public class TestController {

    @GetMapping("/test/{id}")
    @ShowLog("输出日志")
    public String test(@PathVariable String id, @RequestParam(required = false) String name){
        return "success";
    }
}
```
## 详细代码

|||   
---|---
[GitHub](https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-aop)|[码云](https://gitee.com/xudc/spring-boot-v2/tree/master/spring-boot-v2-aop)