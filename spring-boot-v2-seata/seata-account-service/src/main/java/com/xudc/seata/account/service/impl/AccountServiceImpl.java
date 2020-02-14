package com.xudc.seata.account.service.impl;

import com.xudc.seata.account.mapper.AccountMapper;
import com.xudc.seata.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xudc
 * @date 2020/2/12 20:49
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired(required = false)
    private AccountMapper accountMapper;

    @Override
    public void debit(String userId, int money) {
        accountMapper.debit(userId, money);
    }
}
