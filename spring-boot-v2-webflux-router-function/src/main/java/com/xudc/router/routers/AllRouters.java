package com.xudc.router.routers;

import com.xudc.router.handler.UserHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


/**
 * @author xudc
 * @date 2019/3/25 22:53
 */
@SpringBootConfiguration
public class AllRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler handler){
        return RouterFunctions.nest(RequestPredicates.path("/user"),
                RouterFunctions.route(RequestPredicates.GET("/"),handler::findAll)
                .andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), handler::save)
                .andRoute(RequestPredicates.DELETE("/{id}"), handler::deleteById));
    }
}
