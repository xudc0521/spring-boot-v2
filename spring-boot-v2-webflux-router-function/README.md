# spring-boot-v2-webflux-router-function
> Spring Boot 2 WebFlux 之 `Router Functions`方式实现，文中`Spring Boot`版本为`2.1.3.RELEASE`
## 1. 创建SpringBoot项目：
通过`Spring Initializr`工具创建一个SpringBoot项目，引入以下相关依赖：
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
```
## 2. 编写配置
`application.yml`:
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost/webflux
```
注：Mongo 和 Spring Boot 配置中的默认端口均是`27017`，这里可以不配。

## 3. 修改主启动类
在主运行程序上加注解`@EnableReactiveMongoRepositories`启用响应式Mongo仓库功能
```java
@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringBootV2WebfluxRouterFunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootV2WebfluxRouterFunctionApplication.class, args);
    }
}
```

## 4. 编写Bean
创建一个User实体：
`User.java`
```java
@Document(collection = "user")
@Data
@Accessors(chain = true)
public class User {
    @Id
    private String id;
    private String name;
    private int age;
}
```

PS：这里说一下这个注解`@Accessors(chain = true)`，这个是Lombok插件提供的注解，表示支持链式写法。\
如下这种一直.set的写法便是链式写法：\
`User user = new User().setId(1).setName("Cindy").setAge(27);`

## 5. 编写`Repository`
`UserRepository.java`:
```java
@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {
}
```
## 6. 编写`Handler`
`UserHandler.java`:
```java
@Component
public class UserHandler {

    private final UserRepository repository;

    public UserHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Mono<ServerResponse> body = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(repository.findAll(), User.class);
        return body;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        Mono<ServerResponse> responseMono = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(repository.saveAll(userMono), User.class);
        return responseMono;
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ServerResponse> responseMono = repository.findById(id)
                .flatMap(user -> repository.delete(user).then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
        return responseMono;
    }
}
```

PS：这里采用推荐的构造方法注入Bean，也可以采用注解`@Autowired`自动注入。

## 7. 编写`RouterFunction`
```java
@SpringBootConfiguration
public class AllRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler handler){
        return RouterFunctions.nest(RequestPredicates.path("/user"),
                RouterFunctions.route(RequestPredicates.GET("/"),handler::findAll)
                .andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), handler::save)
                .andRoute(RequestPredicates.DELETE("/{id}"), handler::deleteById));
    }
}
```
注：
- `RequestPredicates.path("/user")`就相当于Controller类上的注解`@RequestMapping("/user")`;
- `RouterFunctions.route(RequestPredicates.GET("/"),handler::findAll)`相当于是findAll()方法上的注解`@GetMapping("/")`;
- `andRoute`：增加新的Router路由
- `RequestPredicates.POST("/"` -> `@PostMapping("/")`, `RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)` -> 接受JSON格式数据 -> `@RequestBody`
- `RequestPredicates.DELETE("/{id}")` -> `@DeleteMapping("/{id}")`

## 8. API测试
> 启动MongoDB数据库并进行API测试
1. GET：<http://localhost:8080/user/>
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019032611040895.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

2. POST：<http://localhost:8080/user/>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190326110632679.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

3. DELETE：<http://localhost:8080/user/5c9996f3bafeb94808f94246>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190326110746627.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

## 9. 增加数据校验
### 9.1 自定义一个异常类：
```java
@Data
public class CheckException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * 异常字段的名字
	 */
	private String fieldName;	
	/**
	 * 异常字段的值
	 */
	private String fieldValue;

	public CheckException(String fieldName, String fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
```
### 9.2 编写校验工具类
我们在静态变量里定义不允许的`name`值是`admin`、`root`
```java
public class CheckUtil {
	private static final String[] INVALID_NAMES = { "admin", "root" };
	/**
	 * 校验名字, 不成功抛出校验异常
	 * @param value
	 */
	public static void checkName(String value) {
		Stream.of(INVALID_NAMES).filter(name -> name.equalsIgnoreCase(value))
				.findAny().ifPresent(name -> {
					throw new CheckException("name", value);
				});
	}
}
``` 
### 9.3 修改`Handler`
修改UserHandler#save(ServerRequest request)方法：\
之前在MVC里，我们可以直接拿到user对象，直接`CheckUtil.checkName(user.getName());`即可；\
现在我们得到的是一个Mono<User>，我们发现一个方法`.block()`，这个可以得到泛型对象，我们可以通过`User user = request.bodyToMono(User.class).block();`得到User对象，然后check，测试后，我们发现会报以下异常，大概意思就是` block()/blockFirst()/blockLast()都是阻塞的，以上在线程reactor-http-nio-2中都是不支持的`
```
java.lang.IllegalStateException: block()/blockFirst()/blockLast() are blocking, which is not supported in thread reactor-http-nio-2
	at reactor.core.publisher.BlockingSingleSubscriber.blockingGet(BlockingSingleSubscriber.java:77) ~[reactor-core-3.2.6.RELEASE.jar:3.2.6.RELEASE]
	at reactor.core.publisher.Mono.block(Mono.java:1494) ~[reactor-core-3.2.6.RELEASE.jar:3.2.6.RELEASE]
```

这里操作数据，我们用flatMap(),把该方法改成如下：
```java
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        Mono<ServerResponse> responseMono = userMono.flatMap(user -> {
            CheckUtil.checkName(user.getName());
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(repository.save(user), User.class);
        });
        return responseMono;
    }
```
### 9.4 新增异常处理`Handler`
```java
/**
 * 设置优先级小于-1即可，因为默认的异常处理器DefaultErrorWebExceptionHandler优先级是-1
 * @see ErrorWebFluxAutoConfiguration#errorWebExceptionHandler(org.springframework.boot.web.reactive.error.ErrorAttributes)
 * @author xudc
 */
@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();
		// 设置响应头400
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		// 设置返回类型
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
		// 异常信息
		String errorMsg = toStr(ex);
		DataBuffer db = response.bufferFactory().wrap(errorMsg.getBytes());
		return response.writeWith(Mono.just(db));
	}

	private String toStr(Throwable ex) {
		// 已知异常
		if (ex instanceof CheckException) {
			CheckException e = (CheckException) ex;
			return e.getFieldName() + ": invalid value [" + e.getFieldValue() + "]";
		}
		// 未知异常, 需要打印堆栈, 方便定位
		else {
			ex.printStackTrace();
			return ex.toString();
		}
	}
}
```
这里我们设置自定义异常处理的`Handler`的优先级Order为-2（Order值越小，优先级越高），这是因为WebFlux默认的异常处理器的优先级是-1（源码如下图），我们需要自定义的异常处理器优先级高于默认的：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190326152939740.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

### 9.5 我们来测试一下修改后的代码
- name校验通过的情况：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190326151657437.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

- name校验不通过的情况：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190326153911529.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)