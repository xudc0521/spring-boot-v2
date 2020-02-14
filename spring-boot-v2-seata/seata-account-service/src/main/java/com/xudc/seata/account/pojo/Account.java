package com.xudc.seata.account.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xudc
 * @date 2020/2/12 20:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    /**
     *   `id` int(11) NOT NULL AUTO_INCREMENT,
     *   `user_id` varchar(255) DEFAULT NULL,
     *   `money` int(11) DEFAULT '0',
     */
    private Long id;
    private String userId;
    private Integer money;
}
