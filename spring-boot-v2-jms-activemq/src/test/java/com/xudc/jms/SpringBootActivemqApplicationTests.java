package com.xudc.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootActivemqApplicationTests {

    @Autowired
    public JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    public JmsTemplate jmsTemplate;

    @Test
    public void testSend(){
        jmsMessagingTemplate.convertAndSend("queue.test", "发送消息测试");
    }

    @Test
    public void contextLoads() {
    }

}
