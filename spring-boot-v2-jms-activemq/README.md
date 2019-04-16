# Spring Boot 2.X - Spring Boot整合JMS之ActiveMQ
> `Spring Boot 2`整合ActiveMQ案例。文中`Spring Boot`版本为`2.1.4.RELEASE`。

## 1.创建项目并引入相关依赖
利用Spring Initializr快速创建一个Spring Boot项目，主要依赖如下：

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-activemq</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```
## 2.编写配置
这里需要先安装了ActiveMQ，安装方式请参阅 [Linux安装ActiveMQ](https://blog.csdn.net/xudc0521/article/details/89329696)，并在Queues中添加一条Queue，名称为`queue.test`
```yaml
spring:
  activemq:
    broker-url: tcp://192.168.0.2:61616 # activemq消息组件的连接主机
    user: admin
    password: admin # 账号密码默认为admin
```
## 3.开启JMS功能
在主启动类`XxxApplication`上添加`@EnableJms`注解启动JMS功能
```java
@SpringBootApplication
@EnableJms
public class SpringBootActivemqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootActivemqApplication.class, args);
    }
}
```
## 4.消息监听
新建一个消费者监听器，用于监听MQ消费。用`@JmsListener`注解标注，主要要加入`Spring`容器中
```java
@Component
public class ConsumerListener {

    @JmsListener(destination = "queue.test")
    public void receive(String message) {
        System.err.println("消费者接收到消息：" + message);
    }
}
```
## 5.发送消息接口
编写一个Controller，用于发送MQ测试
```java
@RestController
public class MsgController {

    private final JmsTemplate jmsTemplate;

    public MsgController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/send")
    public String sendMsg(String msg){
        jmsTemplate.convertAndSend("queue.test",msg);
        return "SUCCESS";
    }
}
```
## 6.测试
[启动ActiveMQ](https://blog.csdn.net/xudc0521/article/details/89329696)，启动XxxApplication服务；
发送消息测试，控制台消费监听者打印出了接收到的消息。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190416145756923.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
至此，实现了简单的点对点的发送接收消息。

完整代码：[Github](https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-jms-activemq)