# spring-boot-v2-webflux
> Spring Boot 2.X WebFlux 用法案例

## 1. 新建项目
利用Spring Initializr工具创建一个SpringBoot项目`spring-boot-v2-webflux`，添加如下依赖：
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
## 2. 开启注解
在主启动程序`SpringBootV2WebfluxApplication`上添加注解`@EnableReactiveMongoRepositories`:
```java
@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringBootV2WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootV2WebfluxApplication.class, args);
    }

}
```
## 3. 创建实体对象
创建一个实体User:
```java
@Document(collection = "user")
@Data
@Accessors(chain = true) // 启用链式写法
public class User {
    @Id
    private String id;
    private String name;
    private int age;
}
```

## 4. 定义一个Repository
新建一个接口UserRepository接口，并继承ReactiveMongoRepository：
```java
public interface UserRepository extends ReactiveMongoRepository<User,String> {
}
```

## 5. 启动MongoDB数据库
## 6. 编写配置文件application.yml
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost/test
```
## 7. 编写Controller测试
`UserController.java`:
```java
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 以数组形式一次性返回数据
     * @return
     */
    @GetMapping("/")
    public Object findAll(){
        return userRepository.findAll();
    }

    /**
     * 以SSE形式多次返回数据
     * @return
     */
    @GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findStreamAll(){
        return userRepository.findAll();
    }

    /**
     * 添加数据
     * @param user
     * @return
     */
    @PostMapping("/")
    public Mono<User> save(@RequestBody User user){
        // Spring Data JPA 中，新增和修改都是save。有id的是修改，id为空的是新增，这里设置id为空
        Mono<User> userMono = userRepository.save(user.setId(null));
        return userMono;
    }

    /**
     * 根据id删除用户 存在的时候返回200, 不存在返回404
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity> delete(@PathVariable String id){
        // userRepository.deleteById(id); 没有返回值，不能判断数据是否存在
        return userRepository.findById(id)
                // 1. 当要操作数据，并返回一个Mono的时候，使用flatMap； 2. 如果不操作数据，只是转账数据，则使用map
                .flatMap(user -> userRepository.delete(user)
                        .then(Mono.just(new ResponseEntity(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    /**
     * 修改数据 存在的时候返回200 和修改后的数据, 不存在的时候返回404
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Object update(@PathVariable String id,
                         @RequestBody User user){
        return userRepository.findById(id)
                // 因JPA的save方法既是保存也是更新，这里我们同样先查询，再操作数据。防止id不存在的时候，变成新增数据了。
                .flatMap(u -> userRepository.save(u.setAge(user.getAge()).setName(user.getName())))
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * 根据ID查找用户 存在返回用户信息, 不存在返回404
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findById(@PathVariable("id") String id){
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
```
## 8. 测试接口
> 这里用一个谷歌插件`Restlet Client - REST API Testing`进行REST接口测试
1. POST请求：<http://localhost:8080/user/>  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190325204307543.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

2. PUT请求：<http://localhost:8080/user/5c98cca80e2697437ced4879>
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019032520463576.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

3. GET请求：<http://localhost:8080/user/5c98cca80e2697437ced4879>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190325205055318.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
GET请求2：<http://localhost:8080/user/>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190325205326351.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

4. DELETE请求：<http://localhost:8080/user/5c98cef70e2697437ced487a>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190325211250769.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

