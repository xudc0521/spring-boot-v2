package com.xudc.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringBootV2WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootV2WebfluxApplication.class, args);
    }

}
