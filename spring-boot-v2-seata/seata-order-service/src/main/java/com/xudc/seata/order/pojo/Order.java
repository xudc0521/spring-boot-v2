package com.xudc.seata.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xudc
 * @date 2020/2/12 20:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /**
     *   `id` int(11) NOT NULL AUTO_INCREMENT,
     *   `user_id` varchar(255) DEFAULT NULL,
     *   `commodity_code` varchar(255) DEFAULT NULL,
     *   `count` int(11) DEFAULT '0',
     *   `money` int(11) DEFAULT '0',
     */
    private Long id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private Integer money;
}
