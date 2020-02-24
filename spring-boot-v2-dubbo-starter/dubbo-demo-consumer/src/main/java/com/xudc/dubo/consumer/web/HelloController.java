package com.xudc.dubo.consumer.web;

import com.xudc.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2020/2/22 21:29
 */
@RestController
public class HelloController {

    @Reference
    private HelloService helloService;

    @GetMapping("/{name}")
    public String sayHello(@PathVariable String name) {
        return helloService.sayHello(name);
    }
}
