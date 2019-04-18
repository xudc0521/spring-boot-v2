package com.xudc.router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author xudc
 */
@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringBootWebfluxRouterFunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxRouterFunctionApplication.class, args);
    }
}
