package com.xudc.jms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author xudc
 * @date 2019/4/16 20:05
 */
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
