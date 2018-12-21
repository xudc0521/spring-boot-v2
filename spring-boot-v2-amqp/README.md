# spring-boot-v2-amqp
- 自动配置
    - RabbitAutoConfiguration;
    - 自动配置了连接工厂ConnectionFactory;
    - RabbitProperties封装了RabbitMQ的配置属性;
    - RabbitTemplate:RabbitMQ模板,负责RabbitMQ发送和接收消息;
    - AmqpAdmin:RabbitMQ系统管理组件;
