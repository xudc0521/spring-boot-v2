package com.xudc.webflux.repository;

import com.xudc.webflux.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author xudc
 * @date 2019/3/25 16:05
 */
// @Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {
}
