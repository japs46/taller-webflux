package co.com.nequi.consumer.rest;

import co.com.nequi.consumer.mapper.UserMapper;
import co.com.nequi.consumer.response.UserResponse;
import co.com.nequi.model.user.model.UserProfile;
import co.com.nequi.model.user.gateways.UserProfileWebClientGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserRestConsumer implements UserProfileWebClientGateway {

    private final WebClient client;
    // these methods are an example that illustrates the implementation of WebClient.
    // You should use the methods that you implement from the Gateway from the domain.
    @CircuitBreaker(name = "getById" /*, fallbackMethod = "testGetOk"*/)
    public Mono<UserProfile> getUserById(Long id) {
        return client
                .get()
                .uri("/" +id)
                .retrieve()
                .onStatus(httpStatus -> httpStatus == HttpStatus.NOT_FOUND,
                        response -> response.bodyToMono(String.class)
                                .flatMap(bodyResponse -> Mono.error(
                                        new NoSuchElementException("No existe el usuario externo: "
                                        + bodyResponse)
                                )))
                .onStatus(httpStatus -> httpStatus == HttpStatus.INTERNAL_SERVER_ERROR,
                        response -> response.bodyToMono(String.class)
                                .flatMap(bodyResponse -> Mono.error(
                                        new RuntimeException(bodyResponse)
                                ))
                )
                .bodyToMono(UserResponse.class)
                .log()
                .map(response -> UserMapper.toDomain(response.getData()));
    }

// Possible fallback method
//    public Mono<String> testGetOk(Exception ignored) {
//        return client
//                .get() // TODO: change for another endpoint or destination
//                .retrieve()
//                .bodyToMono(String.class);
//    }

   /* @CircuitBreaker(name = "testPost")
    public Mono<UserResponse> testPost() {
        ObjectRequest request = ObjectRequest.builder()
            .val1("exampleval1")
            .val2("exampleval2")
            .build();
        return client
                .get()
                .uri("/" + id)
                .body(Mono.just(request), ObjectRequest.class)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }*/
}
