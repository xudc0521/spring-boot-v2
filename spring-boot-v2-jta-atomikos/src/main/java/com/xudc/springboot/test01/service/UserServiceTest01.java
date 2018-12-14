package com.xudc.springboot.test01.service;

import com.xudc.springboot.test01.mapper.UserMapperTest01;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xudc
 */
@Service
@Slf4j
public class UserServiceTest01 {
	@Autowired
	private UserMapperTest01 userMapperTest01;

	@Transactional(transactionManager = "test1TransactionManager",rollbackFor = RuntimeException.class)
	public int insertUser(String name, Integer age) {
		int insertUserResult = userMapperTest01.insert(name, age);
		log.info("######insertUserResult:{}##########", insertUserResult);
		int i = 1 / age;
		// 怎么样验证事务开启成功!~
		return insertUserResult;
	}

}
