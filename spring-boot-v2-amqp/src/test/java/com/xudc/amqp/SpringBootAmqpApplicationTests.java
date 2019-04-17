package com.xudc.amqp;

import com.xudc.amqp.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootAmqpApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void contextLoads() {
    }


    @Test
    public void testConvertAndSend1(){
        Map<String,Object> map = new HashMap<>(16);
        map.put("msg","单播消息");
        map.put("data", Arrays.asList("andy",21,true));
        rabbitTemplate.convertAndSend("exchange.direct","andy.news",map);
    }

    @Test
    public void testRecv1(){
        Object o = rabbitTemplate.receiveAndConvert("andy.news");
        System.err.println(o.getClass());
        System.err.println(o);
    }

    @Test
    public void testSend2(){
        rabbitTemplate.convertAndSend("exchange.direct","andy.news",new Book("夏雪宜","AMQP In Action"));
    }

    @Test
    public void testFanout(){
        rabbitTemplate.convertAndSend("exchange.fanout","",new Book("红楼梦","曹雪芹"));
    }


    @Test
    public void testAddExchange(){
        Exchange exchange = new DirectExchange("amqpAdmin.exchange");
        amqpAdmin.declareExchange(exchange);
    }

    @Test
    public void testAddQueue(){
        amqpAdmin.declareQueue(new Queue("amqpAdmin.queue"));
    }

    @Test
    public void testBinding(){
        Binding binding = new Binding("amqpAdmin.queue", Binding.DestinationType.QUEUE,"amqpAdmin.exchange","amqpKey",null);
        amqpAdmin.declareBinding(binding);
    }
}

