# spring-boot-v2-fileupload
> Spring Boot 2.X 上传(多)文件 Demo
1. 导入相关依赖:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
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
```
2. 修改配置application.properties:
```properties
# 设置访问端口号为80
server.port=80
# 禁用thymeleaf缓存
spring.thymeleaf.cache=false
# 默认上传文件最大1M
spring.servlet.multipart.max-file-size=2MB
# 上传请求最大为10M(默认10M)
spring.servlet.multipart.max-request-size=10MB
# 是否支持批量上传，默认支持
spring.servlet.multipart.enabled=true
```
注:上传文件超出大小报异常:
```
org.apache.tomcat.util.http.fileupload.FileUploadBase$FileSizeLimitExceededException: The field file exceeds its maximum permitted size of 2097152 bytes.
```
3. 创建上传页面：\
`
在src\main\resources\templates\下新建index.html
`
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件上传Demo</title>
</head>
<body>
<h2>单文件上传示例</h2>
<div>
    <form method="POST" enctype="multipart/form-data" action="/upload1">
        <p>
            文件：<input type="file" name="file"/>
            <input type="submit" value="上传"/>
        </p>
    </form>
</div>

<hr/>
<h2>多文件上传示例</h2>
<div>
    <form method="POST" enctype="multipart/form-data" action="/upload2">
        <p>
            文件1：<input type="file" name="file"/>
        </p>
        <p>
            文件2：<input type="file" name="file"/>
        </p>
        <p>
            <input type="submit" value="上传"/>
        </p>
    </form>
</div>
</body>
</html>
```
4. 编写后端代码:\
`新建controller.UploadController`
```java
@RestController
@Slf4j
public class UploadController {
    /**
     * 访问上传页面
     * @return
     */
    @GetMapping("/index")
    public Object index(){
        return new ModelAndView("index");
    }

    /**
     * 单文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload1")
    public Object upload1(MultipartFile file) throws IOException {
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", file.getOriginalFilename());
        log.info("[文件大小] - [{}]", file.getSize());
        Path dest = Paths.get("D:\\upload\\" + file.getOriginalFilename());
        file.transferTo(dest);
        Map<String,Object> result = getResult(file);
        return result;
    }

    /**
     * 多文件上传
     * @param files
     * @return
     * @throws IOException
     */
    @PostMapping("/upload2")
    public Object upload2(@RequestParam("file") MultipartFile[] files) throws IOException {
        if (ObjectUtils.isEmpty(files)) {
            return "文件不能为空";
        }
        List<Map<String,Object>> list = new ArrayList<>();
        String basePath = "D:\\upload\\multi\\";
        for (MultipartFile file : files) {
            file.transferTo(new File(basePath + file.getOriginalFilename()));
            Map<String,Object> result = getResult(file);
            list.add(result);
        }
        return list;
    }

    private Map<String,Object> getResult(MultipartFile file) {
        Map<String,Object> result = new HashMap<>(4);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize() + "");
        return result;
    }
}
```
6. 启动主方法
```java
@SpringBootApplication
public class SpringBootV2FileuploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootV2FileuploadApplication.class, args);
    }
}
```
7. 访问`http://localhost/index`:
![上传页面](https://img-blog.csdnimg.cn/20190113150713779.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

>详细代码：
>>GitHub：<https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-fileupload>