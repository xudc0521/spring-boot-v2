package com.xudc.seata.storage.controller;

import com.xudc.seata.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2020/2/12 19:52
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * 扣减库存
     * @param commodityCode
     * @param count
     * @return
     */
    @GetMapping("/deduct")
    public String deduct(@RequestParam String commodityCode, @RequestParam int count) {
        storageService.deduct(commodityCode, count);
        return "storage deduct success!";
    }
}
