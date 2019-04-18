package com.xudc.swagger.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xudc.swagger.pojo.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author xudc
 * @date 2019/4/18 15:06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static Map<Integer, User> userMap = Maps.newConcurrentMap();
    private final static String SUCCESS = "success";

    @ApiOperation("获取用户列表")
    @GetMapping("")
    public List<User> list(){
        return Lists.newArrayList(userMap.values());
    }
    @ApiOperation(value = "创建用户",notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user",value = "用户实体json",required = true,dataType = "User")
    @PostMapping("")
    public String save(@RequestBody User user) {
        userMap.put(user.getId(), user);
        return SUCCESS;
    }

    @ApiOperation(value = "查询用户",notes = "根据id查找用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int")
    @GetMapping("/{id}")
    public User find(@PathVariable Integer id){
        User user = userMap.get(id);
        return user;
    }

    @ApiOperation(value = "删除用户",notes = "根据id删除指定用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        userMap.remove(id);
        return SUCCESS;
    }

    @ApiOperation(value = "修改用户",notes = "根据id修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "user", value = "用户实体json", required = true, dataType = "User")
    })
    @PutMapping("/{id}")
    public String update(@RequestBody User user, @PathVariable Integer id){
        User u = userMap.get(id);
        u.setName(user.getName()).setAge(user.getAge());
        userMap.put(id, u);
        return SUCCESS;
    }
}
