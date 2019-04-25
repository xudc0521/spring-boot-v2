# Spring Boot 2.X - Spring Boot整合RocketMQ
> Spring Boot 2 整合 RocketMQ入门实战。本文Spring Boot版本：`2.1.4.RELEASE`，RocketMQ使用官方的最新的Starter，版本为：`2.0.2`。

## 引入依赖
新建SpringBoot项目，引入如下依赖：

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.rocketmq/rocketmq-spring-boot-starter -->
        <dependency>
            <groupId>org.apache.rocketmq</groupId>
            <artifactId>rocketmq-spring-boot-starter</artifactId>
            <version>2.0.2</version>
        </dependency>
```
## 编写配置
需要启动RocketMQ服务，具体安装启动请参阅 [Windows安装配置RocketMQ](https://blog.csdn.net/xudc0521/article/details/89503439)
```yaml
server:
  port: 80
rocketmq:
  name-server: 127.0.0.1:9876 # 自己的RocketMQ服务地址
  producer:
    send-message-timeout: 300000
    group: my-group
```
## 编写接口
对外暴露一个接口用于发送消息

```java
@RestController
public class ProducerController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/send")
    public String send(String msg) {
        rocketMQTemplate.convertAndSend("test-topic",msg);
        return "success";
    }
}
```
## 消费监听
消费者需要实现`RocketMQListener<T>`接口：
```java
@Service
@RocketMQMessageListener(consumerGroup = "my-consumer_test-topic", topic = "test-topic")
public class RocketConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.err.println("接收到消息：" + message);
    }
}
```
## 启动测试
启动服务，并访问发送消息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190424225131734.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
我们看到控制台接收到并打印出发送的消息了。

## 项目地址
完整代码：[GitHub](https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-rocketmq)