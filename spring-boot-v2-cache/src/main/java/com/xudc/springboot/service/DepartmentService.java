package com.xudc.springboot.service;

import com.xudc.springboot.bean.Department;

/**
 * @author xudc
 * @date 2018/12/16 23:29
 */
public interface DepartmentService {
    /**
     * save dept
     * @param department
     * @return
     */
    int save(Department department);

    /**
     * del dept
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * upd dept
     * @param department
     * @return
     */
    int update(Department department);

    /**
     * query dept
     * @param id
     * @return
     */
    Department find(Integer id);
}
