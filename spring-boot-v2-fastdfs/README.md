# spring-boot-v2-fastdfs
> Spring Boot 2.X 整合 FastDFS分布式文件系统 Demo.
## 使用Spring Initialize 新建一个Spring Boot项目,引入相关依赖:
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
<dependency>
    <groupId>org.csource</groupId>
    <artifactId>fastdfs-client-java</artifactId>
    <version>1.27-SNAPSHOT</version>
</dependency>
```
`注:fastdfs-client-java依赖包需要自行打包,详情请参照:`
[fastdfs-client-java](https://github.com/happyfish100/fastdfs-client-java)

## 配置文件
`config/fdfs_client.conf`:
``` properties
connect_timeout=30
network_timeout=60
http.tracker_server_port=80
# 取决于自己的FastDFS服务器,和/etc/fdfs/storage.conf设置的tracker_server保持一致
tracker_server=xxx.xxx.xxx.xx:22122
```
`application.properties`:
```properties
server.port=80
# 文件最大size
spring.servlet.multipart.max-file-size=2MB
# FastDFS服务器地址
app.base.url=http://xxx.xxx.xxx.xxx/
```


## 编写FastDFS工具类
```java
public class FastDFSClient {
	private TrackerClient trackerClient;
	private TrackerServer trackerServer;
	private StorageServer storageServer;
	private StorageClient1 storageClient;
	
	public FastDFSClient(String conf) throws Exception {
		if (conf.contains("classpath:")) {
			conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
		}
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient1(trackerServer, storageServer);
	}
	
	/**
	 * 上传文件方法
	 * @param fileName 文件全路径
	 * @param extName 文件扩展名，不包含（.）
	 * @param metas 文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
		String result = storageClient.upload_file1(fileName, extName, metas);
		return result;
	}
	
	public String uploadFile(String fileName) throws Exception {
		return uploadFile(fileName, null, null);
	}
	
	public String uploadFile(String fileName, String extName) throws Exception {
		return uploadFile(fileName, extName, null);
	}
	
	/**
	 * 上传文件方法
	 * @param fileContent 文件的内容，字节数组
	 * @param extName 文件扩展名
	 * @param metas 文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
		String result = storageClient.upload_file1(fileContent, extName, metas);
		return result;
	}
	
	public String uploadFile(byte[] fileContent) throws Exception {
		return uploadFile(fileContent, null, null);
	}
	
	public String uploadFile(byte[] fileContent, String extName) throws Exception {
		return uploadFile(fileContent, extName, null);
	}
}
```
## 编写前端页面
`(新建上传页面index.html,成功后跳转页面success.html)`
```html
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件上传Demo</title>
</head>
<body>
<h2>单文件上传示例</h2>
<div>
    <form method="POST" enctype="multipart/form-data" action="/upload">
        <p>
            文件：<input type="file" name="file"/>
            <input type="submit" value="上传"/>
        </p>
    </form>
</div>
</body>
</html>
```
```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

上传成功：
<img src="" th:src="${imgUrl}" width="50%">
</body>
</html>
```

## 编写Controller:
```java
@RestController
@Slf4j
public class UploadController {
    /**
     * fastDFS服务器地址
     */
    @Value("${app.base.url}")
    private String appUrl;

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
     * @throws Exception
     */
    @PostMapping("/upload")
    public Object upload(MultipartFile file) throws Exception {
        // 文件名
        String originalFilename = file.getOriginalFilename();
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", originalFilename);
        log.info("[文件大小] - [{}]", file.getSize());
        ModelAndView view = new ModelAndView("success");
        FastDFSClient client = new FastDFSClient("config/fdfs_client.conf");
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        log.info("[文件扩展名] = [{}]", extName);
        String uploadFile = client.uploadFile(file.getBytes(), extName);
        String imgUrl = appUrl + uploadFile;
        view.addObject("imgUrl", imgUrl);
        return view;
    }
}
```

## 运行主程序，访问
运行`XXXApplication.main()`,访问`http://localhost/index`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190113165219317.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
上传图片,成功:
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190113165511119.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)

> 详细代码
>>  GitHub：https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-fastdfs