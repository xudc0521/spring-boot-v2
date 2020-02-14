package com.xudc.seata.storage.service;

/**
 * @author xudc
 * @date 2020/2/12 17:26
 */
public interface StorageService {
    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}
