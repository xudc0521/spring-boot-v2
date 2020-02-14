package com.xudc.seata.account.controller;

import com.xudc.seata.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xudc
 * @date 2020/2/12 20:53
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/debit")
    public String debit(@RequestParam String userId,@RequestParam int money) {
        accountService.debit(userId, money);
        return "account debit success!";
    }
}
