package com.xudc.springboot.controller;

import com.xudc.springboot.bean.Employee;
import com.xudc.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2018/12/16 23:55
 */
@RestController
public class EmpController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Object getEmp(@PathVariable Integer id) {
        Employee employee = employeeService.find(id);
        return employee;
    }

    @GetMapping("/emp")
    public Object update(Employee employee) {
        employeeService.update(employee);
        return employeeService.find(employee.getId());
    }

    @GetMapping(value = "/emp/del/{id}")
    public Object delete(@PathVariable Integer id) {
        return employeeService.delete(id);
    }

    @GetMapping(value = "/emp/name/{name}")
    public Object getEmpByName(@PathVariable String name){
        Employee employee = employeeService.findByLastName(name);
        return employee;
    }
}
