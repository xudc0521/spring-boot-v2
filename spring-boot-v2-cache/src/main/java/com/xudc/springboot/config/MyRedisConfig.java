package com.xudc.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * Redis配置
 * @author xudc
 * @date 2018/12/18 15:58
 */
@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置 Redis的默认序列化器,jdk默认的序列化器,在保存对象到Redis中,会有一些额外的序列化的东西保存进去,
        // 比如key="e2",对象为emp对象,Redis缓存中key是这个样子：\xAC\xED\x00\x05t\x00\x02e2
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
}
