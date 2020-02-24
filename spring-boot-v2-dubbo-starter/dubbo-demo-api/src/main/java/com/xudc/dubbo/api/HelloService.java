package com.xudc.dubbo.api;

/**
 * @author xudc
 * @date 2020/2/22 20:28
 */
public interface HelloService {
    /**
     * Hello, ${name}
     * @param name
     * @return
     */
    String sayHello(String name);
}
