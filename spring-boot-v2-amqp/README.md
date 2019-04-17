# Spring Boot 2.X - Spring Boot整合AMQP之RabbitMQ
> Spring Boot 2 整合RabbitMQ案例。

## RabbitMQ简介
**简介**
RabbitMQ是一个由erlang开发的AMQP(Advanved Message Queue Protocol)的开源实现。
核心概念
**Message**
消息，消息是不具名的，它由消息头和消息体组成。消息体是不透明的，而消息头则由一系列的可选属性组
成，这些属性包括routing-key（路由键）、 priority（相对于其他消息的优先权）、 delivery-mode（指出
该消息可能需要持久性存储）等。
**Publisher**
消息的生产者，也是一个向交换器发布消息的客户端应用程序。
**Exchange**
交换器，用来接收生产者发送的消息并将这些消息路由给服务器中的队列。
Exchange有4种类型： direct(默认)， fanout, topic, 和headers，不同类型的Exchange转发消息的策略有
所区别。
**Queue**
消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息
可投入一个或多个队列。消息一直在队列里面，等待消费者连接到这个队列将其取走。
**Binding**
绑定，用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换器和消息队列连
接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表。
Exchange 和Queue的绑定可以是多对多的关系。
**Connection**
网络连接，比如一个TCP连接。
**Channel**
信道，多路复用连接中的一条独立的双向数据流通道。信道是建立在真实的TCP连接内的虚
拟连接， AMQP 命令都是通过信道发出去的，不管是发布消息、订阅队列还是接收消息，这
些动作都是通过信道完成。因为对于操作系统来说建立和销毁 TCP 都是非常昂贵的开销，所
以引入了信道的概念，以复用一条 TCP 连接。
**Consumer**
消息的消费者，表示一个从消息队列中取得消息的客户端应用程序。
**Virtual Host**
虚拟主机，表示一批交换器、消息队列和相关对象。虚拟主机是共享相同的身份认证和加
密环境的独立服务器域。每个 vhost 本质上就是一个 mini 版的 RabbitMQ 服务器，拥有
自己的队列、交换器、绑定和权限机制。 vhost 是 AMQP 概念的基础，必须在连接时指定，
RabbitMQ 默认的 vhost 是 / 。
**Broker**
表示消息队列服务器实体
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190417190335676.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
## 引入依赖
利用`Spring Initializr`快速创建一个Spring Boot项目`spring-boot-v2-amqp`，主要依赖如下：

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```
## 编写配置
添加RabbitMQ的配置信息。RabbbitMQ安装请参阅 [Docker安装RabbitMQ](https://blog.csdn.net/xudc0521/article/details/89358344)
```yaml
spring:
  rabbitmq:
    host: 192.168.0.2 # RabbitMQ主机IP
    port: 5672 # 默认5672,一致可不写
    username: guest # 默认guest,一致可不写
    password: guest # 默认guest,一致可不写
#    virtual-host: / # 默认是"/",一致可不写,public void setVirtualHost(String virtualHost) {this.virtualHost = "".equals(virtualHost) ? "/": virtualHost;}

```
## 编写接口
新建一个`AmqpController`，用于发送消息
```java
@RestController
public class AmqpController {

    private final RabbitTemplate rabbitTemplate;

    public AmqpController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private final static String SUCCESS = "success";

    /**
     * 单点
     * @param msg
     * @return
     */
    @GetMapping("/direct")
    public String direct(String msg){
        rabbitTemplate.convertAndSend("amq.direct", "xudc", msg);
        return SUCCESS;
    }

    @GetMapping("/fanout")
    public String fanout(String msg) {
        rabbitTemplate.convertAndSend("amq.fanout", "", msg);
        return SUCCESS;
    }

    @GetMapping("/topic")
    public String topic(String msg){
        rabbitTemplate.convertAndSend("amq.topic", "xudc.#", msg);
        return SUCCESS;
    }
}
```
## 启用Rabbit注解
在主启动类上加上注解`@EnableRabbit`
```java
@SpringBootApplication
@EnableRabbit // 开启基于注解的RabbitMQ模式
public class SpringBootAmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAmqpApplication.class, args);
    }
}
```
## 消息监听
编写消息监听者
```java
@Component
public class AmqpListener {

    @RabbitListener(queues = "xudc")
    public void receive1(String message) {
        System.err.println("xudc -- receive1接收到消息：" + message);
    }

    @RabbitListener(queues = "xudc.book")
    public void receive2(String message) {
        System.err.println("xudc.book -- receive2接收到消息：" + message);
    }

    @RabbitListener(queues = "andy")
    public void receive3(String message) {
        System.err.println("andy -- receive3接收到消息：" + message);
    }
}
```
## 消息测试
1. direct![在这里插入图片描述](https://img-blog.csdnimg.cn/2019041718544197.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
2. fanout![在这里插入图片描述](https://img-blog.csdnimg.cn/20190417185543858.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
3. topic![在这里插入图片描述](https://img-blog.csdnimg.cn/20190417185631873.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0UwOTYyMDEyNg==,size_16,color_FFFFFF,t_70)
至此，实现了Spring Boot和RabbitMQ的简单整合。
