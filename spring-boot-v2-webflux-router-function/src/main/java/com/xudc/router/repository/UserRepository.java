package com.xudc.router.repository;

import com.xudc.router.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xudc
 * @date 2019/3/25 16:05
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {

}
