package com.xudc.jms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author xudc
 * @date 2019/4/16 12:17
 */
@Component
public class ConsumerListener {

    @JmsListener(destination = "queue.test")
    public void receive(String message) {
        System.err.println("消费者接收到消息：" + message);
    }
}
