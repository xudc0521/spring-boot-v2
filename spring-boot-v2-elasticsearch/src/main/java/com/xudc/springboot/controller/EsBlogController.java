package com.xudc.springboot.controller;

import com.xudc.springboot.dto.BlogDTO;
import com.xudc.springboot.service.EsBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2018/12/15 16:14
 */
@RestController
@RequestMapping("/blog")
public class EsBlogController {

    @Autowired
    private EsBlogService esBlogService;

    @GetMapping("/query")
    public Object query(BlogDTO dto){
        return esBlogService.listPage(dto).getContent();
    }
}
