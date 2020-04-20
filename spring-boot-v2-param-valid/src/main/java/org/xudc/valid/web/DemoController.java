package org.xudc.valid.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.xudc.valid.entity.Person;

import javax.validation.Valid;

/**
 * @author xudc
 */
@Controller
public class DemoController {

    @GetMapping("/")
    public String formPage(Person person){
        return "form";
    }

    @PostMapping("/")
    public String checkParams(@Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        return "success";
    }
}
