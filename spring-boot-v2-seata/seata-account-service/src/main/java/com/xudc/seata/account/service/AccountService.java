package com.xudc.seata.account.service;

/**
 * @author xudc
 * @date 2020/2/12 20:49
 */
public interface AccountService {
    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);
}
