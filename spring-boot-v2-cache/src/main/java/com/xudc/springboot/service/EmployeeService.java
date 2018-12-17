package com.xudc.springboot.service;

import com.xudc.springboot.bean.Employee;

/**
 * @author xudc
 * @date 2018/12/16 22:36
 */
public interface EmployeeService {
    /**
     * query emp by id
     * @param id
     * @return
     */
    Employee find(Integer id);

    /**
     * insert emp
     * @param employee
     * @return
     */
    int save(Employee employee);

    /**
     * del emo by id
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * upd emp
     * @param employee
     * @return
     */
    int update(Employee employee);

    /**
     * get emp by lastName
     * @param lastName
     * @return
     */
    Employee findByLastName(String lastName);
}
