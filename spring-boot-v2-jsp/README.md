# Spring Boot 2.X整合JSP
> 虽然Spring Boot官方不推荐使用JSP技术，但是对于一些小公司或者ZF网站，还是有必要的。本文将简单介绍Spring Boot 2.X如何整合JSP实现快速开发。

## 新建项目
这里可以用 **Spring Initializr** 快速创建一个`Spring Boot`项目，修改打包方式位`war`：
![packaging](https://img-blog.csdnimg.cn/20200302172325177.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
或者修改`pom.xml`中的`packaging`属性为`war`:
```xml
<packaging>war</packaging>
```
## 添加依赖
`pom.xml`：
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-jasper</artifactId>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
		</dependency>
```
## 新建目录
新建`src/main/webapp/WEB-INF/views`目录用于存放`jsp`资源文件：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020030217291214.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
## 修改配置
修改`application.yml` / `application.properties`文件：
```yaml
spring:
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
```
## 编写代码
- 创建`index.jsp`:
```jsp
<%--
  Created by IntelliJ IDEA.
  User: xudc
  Date: 2018/12/11
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Welcome,${name}!</h3>

This is a jsp page.<br/>

</body>
</html>
```
- 新建`controller`
```java
/**
 * @author xudc
 * @date 2018/12/11 21:19
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name","xudc");
        return "index";
    }
}
```

## 启动运行
- 配置外部Tomcat、Jetty等Web容器运行：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200302174820209.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
- 以Maven Plugins的spring-boot:run运行;
切换到对应目录，直接在命令行输入以下命令启动：

```bash
mvn spring-boot:run
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200302175012534.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
## 访问测试
浏览器打开<http://localhost:8080/>：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200302175152442.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
OK.至此，`Spring Boot 2`就简单的实现对`JSP`的支持了。
> 这里还是推荐大家用Spring Boot 官方推荐的模板引擎，比如：thymeleaf、freemarker等.

## 完整代码
|||
|---|---|
|[Github](https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-jsp)|[码云](https://gitee.com/xudc/spring-boot-v2/tree/master/spring-boot-v2-jsp)|
