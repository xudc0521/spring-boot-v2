package com.xudc.springboot.service.impl;

import com.xudc.springboot.bean.Employee;
import com.xudc.springboot.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author xudc
 * @date 2018/12/16 23:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void find() {
        Employee employee = employeeService.find(1);
        System.err.println(employee);
    }

    @Test
    public void save() {
        int save = employeeService.save(new Employee("test", "123@qq.com", 1, 1));
        System.err.println(save);
    }

    @Test
    public void delete() {
        int delete = employeeService.delete(1);
        System.err.println(delete);
    }

    @Test
    public void update() {
        int update = employeeService.update(new Employee(1, "test001", "andy@qq.com", 0, 2));
        System.err.println(update);
    }
}