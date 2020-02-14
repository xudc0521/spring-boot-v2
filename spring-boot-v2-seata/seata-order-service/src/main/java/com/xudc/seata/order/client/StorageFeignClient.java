package com.xudc.seata.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xudc
 * @date 2020/2/13 17:36
 */
@FeignClient("storage-service")
public interface StorageFeignClient {
    /**
     * 扣减库存
     * @param commodityCode
     * @param count
     * @return
     */
    @GetMapping("/storage/deduct")
    String deduct(@RequestParam String commodityCode, @RequestParam int count);
}
