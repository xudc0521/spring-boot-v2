package com.xudc.swagger;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xudc
 */
@SpringBootApplication
@EnableSwagger2Doc
public class SpringBootSwaggerStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSwaggerStarterApplication.class, args);
    }
}
