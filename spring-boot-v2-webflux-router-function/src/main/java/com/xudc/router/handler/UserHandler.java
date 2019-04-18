package com.xudc.router.handler;

import com.xudc.router.domain.User;
import com.xudc.router.repository.UserRepository;
import com.xudc.router.util.CheckUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author xudc
 * @date 2019/3/25 21:58
 */
@Component
public class UserHandler {

    private final UserRepository repository;

    public UserHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Mono<ServerResponse> body = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(repository.findAll(), User.class);
        return body;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        Mono<ServerResponse> responseMono = userMono.flatMap(user -> {
            CheckUtil.checkName(user.getName());
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(repository.save(user), User.class);
        });
        return responseMono;
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ServerResponse> responseMono = repository.findById(id)
                .flatMap(user -> repository.delete(user).then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
        return responseMono;
    }
}
