package com.xudc.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xudc
 * @date 2018/12/11 21:19
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name","xudc");
        return "index";
    }
}
