package com.xudc.amqp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit // 开启基于注解的RabbitMQ模式
public class SpringBootV2AmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootV2AmqpApplication.class, args);
    }

}

