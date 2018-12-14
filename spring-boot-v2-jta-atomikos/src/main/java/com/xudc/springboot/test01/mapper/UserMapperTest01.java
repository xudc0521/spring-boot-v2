package com.xudc.springboot.test01.mapper;

import com.xudc.springboot.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author xudc
 */
public interface UserMapperTest01 {
	/**
	 * 查询语句
	 * @param name
	 * @return
	 */
	@Select("SELECT * FROM user WHERE name = #{name}")
	User findByName(@Param("name") String name);

	/**
	 * 添加
	 * @param name
	 * @param age
	 * @return
	 */
	@Insert("INSERT INTO user(name, age) VALUES(#{name}, #{age})")
	int insert(@Param("name") String name, @Param("age") Integer age);
}
