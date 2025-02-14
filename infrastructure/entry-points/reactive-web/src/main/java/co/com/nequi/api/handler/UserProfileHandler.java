package co.com.nequi.api.handler;

import co.com.nequi.api.helper.LogMessages;
import co.com.nequi.model.enums.TechnicalMessage;
import co.com.nequi.model.exceptions.BusinessException;
import co.com.nequi.model.exceptions.TechnicalException;
import co.com.nequi.usecase.user.UserProfileUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
@Slf4j
public class UserProfileHandler {

    private final UserProfileUseCase userUseCase;

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        log.info(LogMessages.INIT_PROCESS, LogMessages.FETCHING_ALL_USERS);

        return userUseCase.getAll()
                .doOnSubscribe(subscription -> log.info(LogMessages.SUBSCRIPTION, LogMessages.FETCHING_ALL_USERS))
                .doOnNext(user -> log.info(LogMessages.USER_FOUND, user))
                .collectList()
                .doOnSuccess(users -> log.info(LogMessages.USERS_FOUND_ALL, users.size()))
                .doOnError(e -> log.error(LogMessages.ERROR, LogMessages.FETCHING_ALL_USERS, e.getMessage()))
                .flatMap(users -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(users)
                )
                .onErrorResume(TechnicalException.class, e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(TechnicalMessage.INTERNAL_ERROR.getMessage()))
                .doFinally(status -> log.info(LogMessages.COMPLETED, LogMessages.FETCHING_ALL_USERS, status));
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));

        log.info(LogMessages.INIT_PROCESS, String.format(LogMessages.GET_USER_BY_ID,id));

        return userUseCase.getById(id)
                .doOnSuccess(userFound -> log.info(LogMessages.USER_FOUND, userFound))
                .flatMap(userProfile -> ServerResponse.ok().bodyValue(userProfile))
                .doOnSubscribe(subscription -> log.info(LogMessages.SUBSCRIPTION, String.format(LogMessages.GET_USER_BY_ID,id)))
                .doOnError(e -> log.error(LogMessages.ERROR, LogMessages.ERROR_GET_USER_BY_ID, id, e.getMessage()))
                .onErrorResume(BusinessException.class, e ->
                        ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(e.getMessage())
                )
                .onErrorResume(TechnicalException.class, e ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(e.getMessage())
                )
                .doFinally(status -> log.info(LogMessages.COMPLETED, String.format(LogMessages.GET_USER_BY_ID,id),
                        status));
    }

    public Mono<ServerResponse> getUserByFirstName(ServerRequest serverRequest) {
        String firstName = serverRequest.pathVariable("firstName");

        log.info(LogMessages.INIT_PROCESS, String.format(LogMessages.GET_USER_BY_FIRST_NAME,firstName));

        return userUseCase.getByFirstName(firstName)
                .collectList()
                .flatMap(users -> {
                    log.info(LogMessages.USERS_FOUND_FIRSTNAME, users.size(), firstName);
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(users);
                })
                .doOnSubscribe(subscription -> log.info(LogMessages.SUBSCRIPTION, String.format(LogMessages.GET_USER_BY_FIRST_NAME,firstName)))
                .doOnError(e -> log.error(LogMessages.ERROR, LogMessages.ERROR_GET_USER_BY_FIRST_NAME, firstName, e.getMessage()))
                .onErrorResume(TechnicalException.class, e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(TechnicalMessage.INTERNAL_ERROR.getMessage()))
                .doFinally(status -> log.info(LogMessages.COMPLETED , String.format(LogMessages.GET_USER_BY_FIRST_NAME,
                                firstName),
                        status));
    }

    public Mono<ServerResponse> createUserExternal(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));

        log.info(LogMessages.INIT_PROCESS, String.format(LogMessages.CREATE_USER_EXTERNAL,id));

        return userUseCase.createExternal(id)
                .doOnSuccess(savedUser -> log.info(LogMessages.SUCCESS, String.format(LogMessages.CREATE_USER_EXTERNAL,savedUser.getId())))
                .flatMap(userProfile -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userProfile))
                .doOnSubscribe(subscription -> log.info(LogMessages.SUBSCRIPTION,String.format(LogMessages.CREATE_USER_EXTERNAL,id)))
                .doOnError(e -> log.error(LogMessages.ERROR, LogMessages.ERROR_CREATE_USER_EXTERNAL, id, e.getMessage()))
                .onErrorResume(TechnicalException.class, e ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(TechnicalMessage.INTERNAL_ERROR.getMessage())
                )
                .doFinally(status -> log.info(LogMessages.COMPLETED,String.format(LogMessages.CREATE_USER_EXTERNAL,id), status));
    }
}
