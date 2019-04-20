# Spring Boot 2.X - Spring Boot整合Swagger2(starter方式)
> 上一篇 [Spring Boot 2.X - Spring Boot整合Swagger2](https://blog.csdn.net/xudc0521/article/details/89381298) 我们介绍了Swagger2和Spring Boot 2之间的整合，虽然功能强大，但也是写了很多重复的代码。这一章，我们使用SpringForAll社区提供的一个swagger-starter进行整合，一切都变得很简单了。这里感谢spring4all社区。

## 引入依赖
修改上一章项目的pom文件，去掉swagger和swagger-ui依赖，添加swagger-starter依赖，完整依赖如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.xudc</groupId>
    <artifactId>spring-boot-v2-swagger2-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-boot-v2-swagger2-starter</name>
    <description>Demo project for Spring Boot Swagger2 Starter</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.spring4all/swagger-spring-boot-starter -->
        <dependency>
            <groupId>com.spring4all</groupId>
            <artifactId>swagger-spring-boot-starter</artifactId>
            <version>1.9.0.RELEASE</version>
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
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

## 添加@EnableSwagger2Doc注解
在主启动类上添加`@EnableSwagger2Doc`注解，开启swagger功能：

```
package com.xudc.swagger;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xudc
 */
@SpringBootApplication
@EnableSwagger2Doc
public class SpringBootSwaggerStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSwaggerStarterApplication.class, args);
    }
}
```
## 创建实体类

```java
package com.xudc.swagger.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xudc
 * @date 2019/4/18 15:07
 */
@Data
@Accessors(chain = true)
public class User {
    private Integer id;
    private String name;
    private Integer age;
}
```
## 创建Controller对外接口
创建Controller对外接口用于测试
```java
package com.xudc.swagger.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xudc.swagger.pojo.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author xudc
 * @date 2019/4/18 15:06
 */
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
## 接口文档测试
1. POST
	Curl
	```
	curl -X POST "http://localhost:8080/user" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"age\": 12, \"id\": 1, \"name\": \"Cindy\"}"
	```
	Request URL
	```
	http://localhost:8080/user
	```
	Response body
	```
	success
	```
	Response headers
	```
	content-length: 7 
 	content-type: text/plain;charset=UTF-8 
 	date: Sat, 20 Apr 2019 14:01:13 GMT 
	```
2. GET
	Curl
	```
	curl -X GET "http://localhost:8080/user/1" -H "accept: */*"
	```
	Request URL
	```
	http://localhost:8080/user/1
	```
	Response body
	```json
	{
	  "id": 1,
	  "name": "Cindy",
	  "age": 12
	}
	```
	Response headers
	```
	 content-type: application/json;charset=UTF-8 
	 date: Sat, 20 Apr 2019 14:01:30 GMT 
	 transfer-encoding: chunked 
	```
3. DELETE
	Curl
	```
	curl -X DELETE "http://localhost:8080/user/1" -H "accept: */*"
	```
	Request URL
	```
	http://localhost:8080/user/1
	```
	Response body
	```
	success
	```
	Response headers
	```
	 content-length: 7 
	 content-type: text/plain;charset=UTF-8 
	 date: Sat, 20 Apr 2019 14:19:27 GMT
	```
## 参数配置
### 参数示例

```properties
swagger.enabled=true

swagger.title=spring-boot-starter-swagger
swagger.description=Starter for swagger 2.x
swagger.version=1.4.0.RELEASE
swagger.license=Apache License, Version 2.0
swagger.licenseUrl=https://www.apache.org/licenses/LICENSE-2.0.html
swagger.termsOfServiceUrl=https://github.com/dyc87112/spring-boot-starter-swagger
swagger.contact.name=didi
swagger.contact.url=http://blog.didispace.com
swagger.contact.email=dyc87112@qq.com
swagger.base-package=com.didispace
swagger.base-path=/**
swagger.exclude-path=/error, /ops/**

swagger.globalOperationParameters[0].name=name one
swagger.globalOperationParameters[0].description=some description one
swagger.globalOperationParameters[0].modelRef=string
swagger.globalOperationParameters[0].parameterType=header
swagger.globalOperationParameters[0].required=true
swagger.globalOperationParameters[1].name=name two
swagger.globalOperationParameters[1].description=some description two
swagger.globalOperationParameters[1].modelRef=string
swagger.globalOperationParameters[1].parameterType=body
swagger.globalOperationParameters[1].required=false

// 取消使用默认预定义的响应消息,并使用自定义响应消息
swagger.apply-default-response-messages=false
swagger.global-response-message.get[0].code=401
swagger.global-response-message.get[0].message=401get
swagger.global-response-message.get[1].code=500
swagger.global-response-message.get[1].message=500get
swagger.global-response-message.get[1].modelRef=ERROR
swagger.global-response-message.post[0].code=500
swagger.global-response-message.post[0].message=500post
swagger.global-response-message.post[0].modelRef=ERROR
```
### 默认的配置

```properties
- swagger.enabled=是否启用swagger，默认：true
- swagger.title=标题
- swagger.description=描述
- swagger.version=版本
- swagger.license=许可证
- swagger.licenseUrl=许可证URL
- swagger.termsOfServiceUrl=服务条款URL
- swagger.contact.name=维护人
- swagger.contact.url=维护人URL
- swagger.contact.email=维护人email
- swagger.base-package=swagger扫描的基础包，默认：全扫描
- swagger.base-path=需要处理的基础URL规则，默认：/**
- swagger.exclude-path=需要排除的URL规则，默认：空
- swagger.host=文档的host信息，默认：空
- swagger.globalOperationParameters[0].name=参数名
- swagger.globalOperationParameters[0].description=描述信息
- swagger.globalOperationParameters[0].modelRef=指定参数类型
- swagger.globalOperationParameters[0].parameterType=指定参数存放位置,可选header,query,path,body.form
- swagger.globalOperationParameters[0].required=指定参数是否必传，true,false
```

## 项目地址
完整代码：[Github](https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-swagger2-starter)