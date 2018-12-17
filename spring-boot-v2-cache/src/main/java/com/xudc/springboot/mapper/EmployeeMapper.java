package com.xudc.springboot.mapper;

import com.xudc.springboot.bean.Employee;
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
public interface EmployeeMapper {

    @Select("SELECT * FROM employee WHERE id = #{id}")
    Employee getEmpById(Integer id);

    @Update("UPDATE employee SET lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} WHERE id=#{id}")
    int updateEmp(Employee employee);

    @Delete("DELETE FROM employee WHERE id=#{id}")
    int deleteEmpById(Integer id);

    @Insert("INSERT INTO employee(lastName, email, gender, d_id) VALUES(#{lastName}, #{email}, #{gender}, #{dId})")
//    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insertEmp(Employee employee);

    @Select("SELECT * FROM employee WHERE lastName = #{lastName}")
    Employee getEmpByLastName(String lastName);
}
