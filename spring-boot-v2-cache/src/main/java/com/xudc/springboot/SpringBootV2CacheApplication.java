package com.xudc.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author xudc
 */
@EnableCaching //开启基于注解的缓存
@SpringBootApplication
public class SpringBootV2CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootV2CacheApplication.class, args);
    }

}

