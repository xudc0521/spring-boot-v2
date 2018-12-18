package com.xudc.springboot;

import com.xudc.springboot.bean.Employee;
import com.xudc.springboot.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootV2CacheApplicationTests {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedisPutObj(){
        Employee employee = employeeService.find(1);
        System.err.println(employee);
        redisTemplate.opsForValue().set("e2",employee);
    }

}

