package com.xudc.jms.web;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2019/4/16 12:23
 */
@RestController
public class MsgController {

    private final JmsTemplate jmsTemplate;

    public MsgController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/send")
    public String sendMsg(String msg){
        // 这么写的话，即便启用pub-sub模式，不需要增加额外配置JmsListenerContainerFactory，也可以实现Topic和Queue共存。
        jmsTemplate.convertAndSend("queue.test",msg);
        // jmsTemplate.convertAndSend(new ActiveMQQueue("queue.test"), msg);
        return "SUCCESS";
    }

    @GetMapping("/sendTopic")
    public String sendTopic(String msg) {
        jmsTemplate.convertAndSend(new ActiveMQTopic("topic.test"), msg);
        return "SUCCESS";
    }
}
