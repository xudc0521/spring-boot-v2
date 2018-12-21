package com.xudc.amqp.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xudc
 * @date 2018/12/19 21:19
 */
@Configuration
public class MyAmqpConfig {

    /**
     * 配置 JSON 的 MessageConverter,否则默认采用jdk序列号的,储存的值为一长串序列化后的字符串,如"rO0ABXNyABFqYXZhLnV0aWwuSGFzaE1hcAUH..."
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
