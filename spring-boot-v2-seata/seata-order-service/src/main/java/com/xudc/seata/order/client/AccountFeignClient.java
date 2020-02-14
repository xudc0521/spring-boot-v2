package com.xudc.seata.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xudc
 * @date 2020/2/12 21:03
 */
@FeignClient("account-service") // 调用account-service服务
public interface AccountFeignClient {
    /**
     * 从用户账户中借出
     * @param userId
     * @param money
     * @return
     */
    @GetMapping("/account/debit")
    String debit(@RequestParam String userId, @RequestParam int money);
}
