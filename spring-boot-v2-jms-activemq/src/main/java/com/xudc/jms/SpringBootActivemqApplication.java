package com.xudc.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author xudc
 */
@SpringBootApplication
@EnableJms
public class SpringBootActivemqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootActivemqApplication.class, args);
    }
}
