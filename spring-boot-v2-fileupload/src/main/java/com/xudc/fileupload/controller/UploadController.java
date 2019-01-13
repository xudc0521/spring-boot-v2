package com.xudc.fileupload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xudc
 * @date 2019/1/13 12:56
 */
@RestController
@Slf4j
public class UploadController {
    /**
     * 访问上传页面
     * @return
     */
    @GetMapping("/index")
    public Object index(){
        return new ModelAndView("index");
    }

    /**
     * 单文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload1")
    public Object upload1(MultipartFile file) throws IOException {
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", file.getOriginalFilename());
        log.info("[文件大小] - [{}]", file.getSize());
        Path dest = Paths.get("D:\\upload\\" + file.getOriginalFilename());
        file.transferTo(dest);
        Map<String,Object> result = getResult(file);
        return result;
    }

    /**
     * 多文件上传
     * @param files
     * @return
     * @throws IOException
     */
    @PostMapping("/upload2")
    public Object upload2(@RequestParam("file") MultipartFile[] files) throws IOException {
        if (ObjectUtils.isEmpty(files)) {
            return "文件不能为空";
        }
        List<Map<String,Object>> list = new ArrayList<>();
        String basePath = "D:\\upload\\multi\\";
        for (MultipartFile file : files) {
            file.transferTo(new File(basePath + file.getOriginalFilename()));
            Map<String,Object> result = getResult(file);
            list.add(result);
        }
        return list;
    }

    private Map<String,Object> getResult(MultipartFile file) {
        Map<String,Object> result = new HashMap<>(4);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize() + "");
        return result;
    }
}
