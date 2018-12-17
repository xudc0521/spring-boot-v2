package com.xudc.springboot.service.impl;

import com.xudc.springboot.bean.Department;
import com.xudc.springboot.mapper.DepartmentMapper;
import com.xudc.springboot.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xudc
 * @date 2018/12/16 23:38
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * save dept
     * @param department
     * @return
     */
    @Override
    public int save(Department department) {
        return departmentMapper.insertDept(department);
    }

    /**
     * del dept
     * @param id
     * @return
     */
    @Override
    public int delete(Integer id) {
        return departmentMapper.deleteById(id);
    }

    /**
     * upd dept
     * @param department
     * @return
     */
    @Override
    public int update(Department department) {
        return departmentMapper.updateDept(department);
    }

    /**
     * query dept
     * @param id
     * @return
     */
    @Override
    public Department find(Integer id) {
        return departmentMapper.getDeptById(id);
    }
}
