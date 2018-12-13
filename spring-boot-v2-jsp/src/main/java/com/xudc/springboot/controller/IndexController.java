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

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("name","xudc");
        model.addAttribute("age",27);
        return "index";
    }
}
