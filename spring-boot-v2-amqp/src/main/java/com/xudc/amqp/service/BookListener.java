package com.xudc.amqp.service;

import com.xudc.amqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author xudc
 * @date 2018/12/19 22:01
 */
@Service
public class BookListener {

    @RabbitListener(queues = "andy.news")
    public void receive(Book book) {
        System.err.println("andy.news监听到消息=" + book);
    }

    @RabbitListener(queues = "andy")
    public void receive2(Message message) {
        System.err.println("andy监听到消息" + new String(message.getBody()));
    }
}
