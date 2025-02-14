package co.com.nequi.api.router;

import co.com.nequi.api.handler.UserProfileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class UserProfileRouter {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserProfileHandler userHandler) {

        return RouterFunctions
                .nest(RequestPredicates.path("/users"),
                        RouterFunctions.route()
                                .GET("", userHandler::getAllUsers)
                                .GET("/{id}", userHandler::getUserById)
                                .GET("/filter/{firstName}", userHandler::getUserByFirstName)
                                .POST("/{id}", userHandler::createUserExternal)
                                .build()
                );
    }
}
