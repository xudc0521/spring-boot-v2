package com.xudc.seata.order.controller;

import com.xudc.seata.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2020/2/12 20:27
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String create(@RequestParam String userId,@RequestParam String commodityCode,@RequestParam int orderCount) {
        orderService.create(userId, commodityCode, orderCount);
        return "create order success!";
    }
}
