package com.xudc.springboot.mapper;

import com.xudc.springboot.domian.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author xudc
 * @date 2018/11/28 15:46
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where user_id = #{userId}")
    User getUserByUserId(@Param("userId") Integer userId);

    @Options(useGeneratedKeys = true,keyProperty = "userId")
    @Insert("insert user(user_name,age) values(#{userName},#{age})")
    int insertUser(User user);

    @Delete("delete from user where user_id = #{userId}")
    int deleteUser(Integer userId);

    @Update("update user set user_name=#{userName},age=#{age} where user_id = #{userId}")
    int updateUser(User user);
}
