package com.xudc.dubbo.provider.service;

import com.xudc.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author xudc
 * @date 2020/2/22 20:35
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
