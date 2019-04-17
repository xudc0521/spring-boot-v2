package com.xudc.amqp.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xudc
 * @date 2018/12/19 22:01
 */
@Component
public class AmqpListener {

    // @RabbitListener(queues = "andy")
    // public void receive(Book book) {
    //     System.err.println("andy -- 监听到消息：" + book);
    // }

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
