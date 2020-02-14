package com.xudc.seata.order.mapper;

import com.xudc.seata.order.pojo.Order;
import org.apache.ibatis.annotations.Insert;

/**
 * @author xudc
 * @date 2020/2/12 20:20
 */
public interface OrderMapper {

    /**
     * 插入订单
     * @param order
     * @return
     */
    @Insert("INSERT INTO tb_order(user_id, commodity_code, count, money) VALUES(#{userId}, #{commodityCode}, #{count}, #{money})")
    void insert(Order order);
}
