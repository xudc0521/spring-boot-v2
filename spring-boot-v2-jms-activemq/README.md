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

# Spring Boot 2.X - Spring Boot整合JMS之ActiveMQ
> `Spring Boot 2`整合ActiveMQ案例之订阅发布方式。文中`Spring Boot`版本为`2.1.4.RELEASE`。

上篇 [Spring Boot 2.X - Spring Boot整合JMS之ActiveMQ](https://blog.csdn.net/xudc0521/article/details/89333506)介绍了ActiveMQ点对点的模式，这里我们来看下订阅发布模式。
## 1.修改配置
开启pub-sub模式，默认是关闭的，也就是默认是点对点模式：
```yaml
spring:
  activemq:
    broker-url: tcp://192.168.0.2:61616 # activemq消息组件的连接主机
    user: admin
    password: admin # 账号密码默认为admin
  jms:
    pub-sub-domain: true # 启用发布订阅模式（默认关闭是点对点模式）
```
## 2.订阅者	
新建订阅者`TopicSub `
```java
@Component
public class TopicSub {

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive1(String message) {
        System.err.println("Topic.Subscribe.receive1接收消息：" + message);
    }

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive2(String message) {
        System.err.println("Topic.Subscribe.receive2接收消息：" + message);
    }

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive3(String message) {
        System.err.println("Topic.Subscribe.receive3接收消息：" + message);
    }

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive4(String message) {
        System.err.println("Topic.Subscribe.receive4接收消息：" + message);
    }
}
```
并在ActiveMQ管理后台增加一条名为`topic.test`的Topic，如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190416213231226.png)
## 3.修改接口
在`MsgController`中增加消息发布接口
```java
    @GetMapping("/sendTopic")
    public String sendTopic(String msg) {
        jmsTemplate.convertAndSend(new ActiveMQTopic("topic.test"), msg);
        return "SUCCESS";
    }
```
## 4.启动测试
启动ActiveMQ服务，并启动XxxApplication本项目，测试
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190416213710184.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
## 5.问题？
这里有个问题，我们一下之前的点对点消息模式：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190416213904914.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
> 我们发现，并没有看到消费的信息打印出来。查阅ActiveMQ管理后台，有消息未被消费
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190416214119281.png)
## 6.点对点和发布订阅共存解决
### 6.1 新建配置类
新建配置类，并添加Bean：`JmsListenerContainerFactory`，设置监听容器支持Pub-Sub模式
```java
@Configuration
public class MyConfig {

    @Bean
    public JmsListenerContainerFactory<?> topicListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
```
### 6.2 修改配置
注释掉`spring.jms.pub-sub-domain`，或者设为`false`
```yaml
spring:
  activemq:
    broker-url: tcp://192.168.0.2:61616 # activemq消息组件的连接主机
    user: admin
    password: admin # 账号密码默认为admin
#  jms:
#    pub-sub-domain: true # 启用发布订阅模式（默认关闭是点对点模式）
```
### 6.3 修改订阅者
指定`containerFactory`为上一步设置的`topicListenerContainerFactory`。
```java
@Component
public class TopicSub {

    @JmsListener(destination = "topic.test",containerFactory = "topicListenerContainerFactory")
    public void receive1(String message) {
        System.err.println("Topic.Subscribe.receive1接收消息：" + message);
    }

    @JmsListener(destination = "topic.test",containerFactory = "topicListenerContainerFactory")
    public void receive2(String message) {
        System.err.println("Topic.Subscribe.receive2接收消息：" + message);
    }

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive3(String message) {
        System.err.println("Topic.Subscribe.receive3接收消息：" + message);
    }

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive4(String message) {
        System.err.println("Topic.Subscribe.receive4接收消息：" + message);
    }
}
```
### 6.4 启动测试
启动ActiveMQ和本项目，
1. 测试点对点模式：![在这里插入图片描述](https://img-blog.csdnimg.cn/20190416215025593.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
2. 测试发布订阅模式：![在这里插入图片描述](https://img-blog.csdnimg.cn/20190416215151618.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
已经成功实现点对点和发布订阅模式共存了。
## 7.发现另一种方法
在实际使用中，我发现，其实可以不用配置Bean，原生可以实现2中模式共存的情况。
### 7.1 修改配置
再次开启pub-sub模式
```yaml
spring:
  activemq:
    broker-url: tcp://192.168.0.2:61616 # activemq消息组件的连接主机
    user: admin
    password: admin # 账号密码默认为admin
  jms:
    pub-sub-domain: true # 启用发布订阅模式（默认关闭是点对点模式）
```
### 7.2 注释掉配置类
我们不需要这个配置类，注释掉
```java
@Configuration
public class MyConfig {

    // @Bean
    // public JmsListenerContainerFactory<?> topicListenerContainerFactory(ConnectionFactory connectionFactory) {
    //     DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    //     factory.setPubSubDomain(true);
    //     factory.setConnectionFactory(connectionFactory);
    //     return factory;
    // }
}
```
### 7.3 修改订阅者
去掉指定的`containerFactory`

```java
@Component
public class TopicSub {

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive1(String message) {
        System.err.println("Topic.Subscribe.receive1接收消息：" + message);
    }

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive2(String message) {
        System.err.println("Topic.Subscribe.receive2接收消息：" + message);
    }

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive3(String message) {
        System.err.println("Topic.Subscribe.receive3接收消息：" + message);
    }

    @JmsListener(destination = "topic.test"/*,containerFactory = "topicListenerContainerFactory"*/)
    public void receive4(String message) {
        System.err.println("Topic.Subscribe.receive4接收消息：" + message);
    }
}
```
### 7.4 修改点对点调用方法
修改Controller中点对点调用方法：

```java
    @GetMapping("/send")
    public String sendMsg(String msg){
        // 这么写的话，即便启用pub-sub模式，不需要增加额外配置JmsListenerContainerFactory，也可以实现Topic和Queue共存。
        jmsTemplate.convertAndSend("queue.test",msg);
        // jmsTemplate.convertAndSend(new ActiveMQQueue("queue.test"), msg);
        return "SUCCESS";
    }
```
### 7.5 启动测试
1. 发布订阅模式：![在这里插入图片描述](https://img-blog.csdnimg.cn/20190416220334639.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
2. 点对点模式：![在这里插入图片描述](https://img-blog.csdnimg.cn/20190416220414820.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
OK。这样也实现了点对点和发布订阅两种模式的共存。
## 8.项目代码
完整代码：[Github](https://github.com/xudc0521/spring-boot-v2/tree/master/spring-boot-v2-jms-activemq)