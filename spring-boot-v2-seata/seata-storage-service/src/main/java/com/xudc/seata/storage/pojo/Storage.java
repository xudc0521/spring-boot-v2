package com.xudc.seata.storage.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xudc
 * @date 2020/2/12 18:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    private Long id;
    private String commodityCode;
    private Integer count;
}
