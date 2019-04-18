# Spring Boot 2.X - Spring Boot整合Swagger2
> Spring Boot能够快速开发应用程序，而Swagger2则能够快速的生成Restful APIs接口文档。本文将就两者进行简单的整合应用。
> 文中Spring Boot版本为`2.1.4.RELEASE`，Swagger2版本为`2.9.2`

## 添加依赖
利用Spring Initializr快速创建一个Spring Boot应用，并添加Swagger2依赖，主要依赖如下：

```xml
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>${swagger.version}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>${swagger.version}</version>
        </dependency>
```
## 创建Swagger2配置

```java
@SpringBootConfiguration
@EnableSwagger2
public class SwaggerConfig {
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xudc.swagger.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger构建Restful API文档")
                .description("文档除了查看API还可以进行调试")
                .contact(new Contact("xudc", "https://blog.csdn.net/xudc0521", "e-mail"))
                .version("1.0.0")
                .build();
    }
}
```
## 新建实体

```java
@Data
@Accessors(chain = true)
public class User {
    private Integer id;
    private String name;
    private Integer age;
}
```
## 创建接口并构建文档内容

```java
@RestController
@RequestMapping("/user")
public class UserController {

    private static Map<Integer, User> userMap = Maps.newConcurrentMap();
    private final static String SUCCESS = "success";

    @ApiOperation("获取用户列表")
    @GetMapping("")
    public List<User> list(){
        return Lists.newArrayList(userMap.values());
    }
    @ApiOperation(value = "创建用户",notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user",value = "用户实体json",required = true,dataType = "User")
    @PostMapping("")
    public String save(@RequestBody User user) {
        userMap.put(user.getId(), user);
        return SUCCESS;
    }

    @ApiOperation(value = "查询用户",notes = "根据id查找用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int")
    @GetMapping("/{id}")
    public User find(@PathVariable Integer id){
        User user = userMap.get(id);
        return user;
    }

    @ApiOperation(value = "删除用户",notes = "根据id删除指定用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        userMap.remove(id);
        return SUCCESS;
    }

    @ApiOperation(value = "修改用户",notes = "根据id修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "user", value = "用户实体json", required = true, dataType = "User")
    })
    @PutMapping("/{id}")
    public String update(@RequestBody User user, @PathVariable Integer id){
        User u = userMap.get(id);
        u.setName(user.getName()).setAge(user.getAge());
        userMap.put(id, u);
        return SUCCESS;
    }
}
```

## 访问接口文档
启动服务，访问`http://localhost:8080/swagger-ui.html`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190418160439460.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
点击user-controller进入接口调试，点击对应功能右上角的`Try it out`进入调试
1. 添加用户（编写号json后，点击下方的Execute执行）：![在这里插入图片描述](https://img-blog.csdnimg.cn/20190418160633845.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
2. 查询list：![在这里插入图片描述](https://img-blog.csdnimg.cn/20190418160937956.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
## 项目地址
完整代码：[Github](https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-swagger)