package com.xudc.springboot.mapper;

import com.xudc.springboot.bean.Department;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author xudc
 * @date 2018/12/16 22:21
 */
@Mapper
public interface DepartmentMapper {
    @Insert("INSERT INTO department(departmentName) VALUES(#{departmentName})")
    int insertDept(Department department);

    @Delete("DELETE FROM department WHERE id=#{id}")
    int deleteById(Integer id);

    @Update("UPDATE department SET departmentName=#{departmentName} WHERE id=#{id}")
    int updateDept(Department department);

    @Select("SELECT * FROM department WHERE id = #{id}")
    Department getDeptById(Integer id);
}
