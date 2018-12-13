package com.xudc.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2018/12/13 11:29
 */
@RestController
@Slf4j
public class IndexController {

    @GetMapping("/index")
    public String index(Integer id) {
        return "SUCCESS" + id;
    }
}
