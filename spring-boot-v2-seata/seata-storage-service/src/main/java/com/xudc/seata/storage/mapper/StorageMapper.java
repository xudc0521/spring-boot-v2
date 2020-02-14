package com.xudc.seata.storage.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author xudc
 * @date 2020/2/12 18:30
 */
public interface StorageMapper {
    /**
     * 扣减库存
     * @param commodityCode
     * @param count
     */
    @Update("UPDATE tb_storage SET count = count - #{count} WHERE commodity_code = #{commodityCode}")
    void deduct(@Param("commodityCode") String commodityCode, @Param("count") int count);
}
