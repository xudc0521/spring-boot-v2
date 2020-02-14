package com.xudc.seata.order.service.impl;

import com.xudc.seata.order.client.AccountFeignClient;
import com.xudc.seata.order.client.StorageFeignClient;
import com.xudc.seata.order.mapper.OrderMapper;
import com.xudc.seata.order.pojo.Order;
import com.xudc.seata.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xudc
 * @date 2020/2/12 20:21
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired(required = false)
    private OrderMapper orderMapper;

    @Autowired
    private AccountFeignClient accountFeignClient;

    @Autowired
    private StorageFeignClient storageFeignClient;

    @Override
    @GlobalTransactional/*(name = "my_test_tx_group", rollbackFor = {Exception.class,RuntimeException.class})*/
    public void create(String userId, String commodityCode, int orderCount) {
        int orderMoney = calculate(commodityCode, orderCount);
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(orderCount);
        order.setMoney(orderMoney);
        orderMapper.insert(order);

        // Feign远程调用，扣钱
        accountFeignClient.debit(userId, orderMoney);
        // 减库存
        storageFeignClient.deduct(commodityCode, orderCount);

        if (orderCount == 3) {
            throw new RuntimeException("异常回滚");
        }
    }

    private int calculate(String commodityCode, int orderCount) {
        // 这里固定一个商品10元,商品代码暂不用，仅作模拟演示
        return orderCount * 10;
    }
}
