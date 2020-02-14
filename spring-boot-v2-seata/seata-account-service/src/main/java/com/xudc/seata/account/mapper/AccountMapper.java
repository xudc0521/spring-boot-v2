package com.xudc.seata.account.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author xudc
 * @date 2020/2/12 20:47
 */
public interface AccountMapper {
    /**
     * 扣款
     * @param userId
     * @param money
     */
    @Update("UPDATE tb_account SET money = money - #{money} WHERE user_id = #{userId}")
    void debit(@Param("userId") String userId,@Param("money") int money);
}
