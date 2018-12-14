package com.xudc.springboot.controller;

import com.xudc.springboot.entity.User;
import com.xudc.springboot.test02.service.UserServiceTest02;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2018/12/14 12:23
 */
@RestController
public class UserController {

    @Autowired
    private UserServiceTest02 userServiceTest02;

    @GetMapping("/add")
    public int addUser(User user){
        return userServiceTest02.insertUserTest01AndTest02(user.getName(),user.getAge());
    }

}
