package com.xudc.rocketmq.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xudc
 * @date 2019/4/24 22:03
 */
@RestController
public class ProducerController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/send")
    public String send(String msg) {
        rocketMQTemplate.convertAndSend("test-topic",msg);
        return "success";
    }
}
