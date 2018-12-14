package com.xudc.springboot;

import com.xudc.springboot.domian.User;
import com.xudc.springboot.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootV2DruidApplicationTests {


    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testDataSource(){
        System.err.println(dataSource);
        System.err.println(dataSource.getClass());
        try {
            Connection connection = dataSource.getConnection();
            System.err.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUser(){
        User user = new User();
        user.setUserName("test");
        user.setAge(18);
        int count = userMapper.insertUser(user);
        System.out.println(count);
        user = userMapper.getUserByUserId(1);
        System.out.println(user);
        user.setAge(21);
        count = userMapper.updateUser(user);
        System.out.println(count);


    }

}

