# Spring Boot - Valid Form表单参数验证

>本文介绍使用@Valid优雅的进行Form表单参数校验，避免大量扎眼的`if(){...}`语句...

## 主要依赖

lombok插件主要为了简化代码，自行视情况添加使用。\
thymeleaf主要为了页面测试
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

## 创建实体类

这里我们创建一个`Person`实体类，来进行后续的演示：

```java
@Data
public class Person {

    @NotNull
    @Size(min = 4, max = 16, message = "用户名须在4-16位之间")
    private String userName;

    @NotNull
    @Min(value = 18, message = "年龄最小18岁")
    private Integer age;
}
```

>- `@NotNull`用来标示字段非空;
>- `@Size(min = 4, max = 16, message = "用户名须在4-16位之间")`用来限定字符长度, 这个表示字符长度在4-16之间, message用于校验失败时的提示信息;
>- `@Min(value = 18, message = "年龄最小18岁")`表示最小值时18, 不允许小于18;

## 创建Controller

这里我们新建一个Controller用于测试

```java
@Controller
public class DemoController {

    @GetMapping("/")
    public String formPage(Person person){
        return "form";
    }

    @PostMapping("/")
    public String checkParams(@Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        return "success";
    }
}
```
>- `@Valid`用于收集属性进行参数校验;
>- `BindingResult`可以进行测试检索验证错误，返回验证结果信息;

## 创建HTML页面

新建一个HTML页面(form.html)用于表单提交测试

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Params Valid Demo</title>
</head>
<body>
<form action="#" th:action="@{/}" th:object="${person}" method="post">
    <table>
        <tr>
            <td>UserName:</td>
            <td><input type="text" th:field="*{userName}" th:autocomplete="off"/></td>
            <td th:if="${#fields.hasErrors('userName')}" th:errors="*{userName}">UserName Error</td>
        </tr>
        <tr>
            <td>Age:</td>
            <td><input type="text" th:field="*{age}" th:autocomplete="off"/></td>
            <td th:if="${#fields.hasErrors('age')}" th:errors="*{age}">Age Error</td>
        </tr>
        <tr>
            <td><button type="submit">Submit</button></td>
        </tr>
    </table>
</form>
</body>
</html>
```

创建一个成功页面(success.html)，用于校验通过跳转：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h3>SUCCESS.</h3>
</body>
</html>
```

## 运行并测试

运行启动类：

```java
@SpringBootApplication
public class BootValidApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootValidApplication.class, args);
    }
}
```

- 启动后，打开表单页，输入不合参数并提交：
![](https://pic.downk.cc/item/5e9dc1fac2a9a83be5d32106.jpg)
![](https://pic.downk.cc/item/5e9dc081c2a9a83be5d20482.jpg)
![](https://pic.downk.cc/item/5e9dc04cc2a9a83be5d1c64d.png)

- 输入正确的参数提交：
![](https://pic.downk.cc/item/5e9dbf69c2a9a83be5d0b510.jpg)
![](https://pic.downk.cc/item/5e9dbf92c2a9a83be5d0e626.jpg)

## 完整代码

|||
|---|---|
|[Github](https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-param-valid)|[码云](https://gitee.com/xudc/spring-boot-v2/tree/master/spring-boot-v2-param-valid)|