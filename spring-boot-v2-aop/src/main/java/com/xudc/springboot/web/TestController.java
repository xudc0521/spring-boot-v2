package com.xudc.springboot.web;

import com.xudc.springboot.annotation.ShowLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 */
@RestController
public class TestController {

    @GetMapping("/test/{id}")
    @ShowLog("输出日志")
    public String test(@PathVariable String id, @RequestParam(required = false) String name){
        return "success";
    }
}
