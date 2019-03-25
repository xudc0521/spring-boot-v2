package com.xudc.webflux.controller;

import com.xudc.webflux.domain.User;
import com.xudc.webflux.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author xudc
 * @date 2019/3/25 16:09
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 以数组形式一次性返回数据
     * @return
     */
    @GetMapping("/")
    public Object findAll(){
        return userRepository.findAll();
    }

    /**
     * 以SSE形式多次返回数据
     * @return
     */
    @GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findStreamAll(){
        return userRepository.findAll();
    }

    /**
     * 添加数据
     * @param user
     * @return
     */
    @PostMapping("/")
    public Mono<User> save(@RequestBody User user){
        // Spring Data JPA 中，新增和修改都是save。有id的是修改，id为空的是新增，这里设置id为空
        Mono<User> userMono = userRepository.save(user.setId(null));
        return userMono;
    }

    /**
     * 根据id删除用户 存在的时候返回200, 不存在返回404
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        // userRepository.deleteById(id); 没有返回值，不能判断数据是否存在
        return userRepository.findById(id)
                // 1. 当要操作数据，并返回一个Mono的时候，使用flatMap； 2. 如果不操作数据，只是转换数据，则使用map
                .flatMap(user -> userRepository.delete(user)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 修改数据 存在的时候返回200 和修改后的数据, 不存在的时候返回404
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Object update(@PathVariable String id,
                         @RequestBody User user){
        return userRepository.findById(id)
                // 因JPA的save方法既是保存也是更新，这里我们同样先查询，再操作数据。防止id不存在的时候，变成新增数据了。
                .flatMap(u -> userRepository.save(u.setAge(user.getAge()).setName(user.getName())))
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * 根据ID查找用户 存在返回用户信息, 不存在返回404
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findById(@PathVariable("id") String id){
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
