package com.xudc.seata.order.service;

/**
 * @author xudc
 * @date 2020/2/12 20:18
 */
public interface OrderService {
    /**
     * 创建订单
     */
    void create(String userId, String commodityCode, int orderCount);
}
