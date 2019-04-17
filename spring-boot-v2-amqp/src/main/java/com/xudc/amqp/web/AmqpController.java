package com.xudc.amqp.web;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2019/4/17 17:52
 */
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

    // @GetMapping("/book")
    // public String book(){
    //     rabbitTemplate.convertAndSend("amq.direct", "andy", new Book("Andy","AMQP入门"));
    //     return SUCCESS;
    // }

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
