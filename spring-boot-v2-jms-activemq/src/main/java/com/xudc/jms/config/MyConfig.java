package com.xudc.jms.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author xudc
 * @date 2019/4/16 20:22
 */
@Configuration
public class MyConfig {

    // @Bean
    // public JmsListenerContainerFactory<?> topicListenerContainerFactory(ConnectionFactory connectionFactory) {
    //     DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    //     factory.setPubSubDomain(true);
    //     factory.setConnectionFactory(connectionFactory);
    //     return factory;
    // }
}
