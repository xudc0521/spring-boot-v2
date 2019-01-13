package com.xudc.fastdfs.controller;

import com.xudc.fastdfs.util.FastDFSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xudc
 * @date 2019/1/13 12:56
 */
@RestController
@Slf4j
public class UploadController {
    /**
     * fastDFS服务器地址
     */
    @Value("${app.base.url}")
    private String appUrl;

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
     * @throws Exception
     */
    @PostMapping("/upload")
    public Object upload(MultipartFile file) throws Exception {
        // 文件名
        String originalFilename = file.getOriginalFilename();
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", originalFilename);
        log.info("[文件大小] - [{}]", file.getSize());
        ModelAndView view = new ModelAndView("success");
        FastDFSClient client = new FastDFSClient("config/fdfs_client.conf");
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        log.info("[文件扩展名] = [{}]", extName);
        String uploadFile = client.uploadFile(file.getBytes(), extName);
        String imgUrl = appUrl + uploadFile;
        view.addObject("imgUrl", imgUrl);
        return view;
    }
}
