package com.xudc.springboot;

import com.xudc.springboot.config.DBConfig1;
import com.xudc.springboot.config.DBConfig2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author xudc
 */
@SpringBootApplication
@EnableConfigurationProperties(value = {DBConfig1.class, DBConfig2.class})
public class SpringBootV2JtaAtomikosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootV2JtaAtomikosApplication.class, args);
    }

}

