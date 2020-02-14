package com.xudc.seata.storage.service.impl;

import com.xudc.seata.storage.mapper.StorageMapper;
import com.xudc.seata.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xudc
 * @date 2020/2/12 18:27
 */
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired(required = false)
    private StorageMapper storageMapper;

    @Override
    public void deduct(String commodityCode, int count) {
        storageMapper.deduct(commodityCode, count);
        // if (count == 3) {
        //     int i = count / 0;
        // }
    }
}
