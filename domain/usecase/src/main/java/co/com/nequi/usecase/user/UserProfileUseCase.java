package co.com.nequi.usecase.user;

import co.com.nequi.model.enums.TechnicalMessage;
import co.com.nequi.model.exceptions.BusinessException;
import co.com.nequi.model.exceptions.TechnicalException;
import co.com.nequi.model.user.gateways.UserProfileSqsGateway;
import co.com.nequi.model.user.model.UserProfile;
import co.com.nequi.model.user.gateways.UserProfileDBPostgresGateway;
import co.com.nequi.model.user.gateways.UserProfileDBRedisGateway;
import co.com.nequi.model.user.gateways.UserProfileWebClientGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserProfileUseCase {

    private final UserProfileDBPostgresGateway userProfileDBPostgresGateway;
    private final UserProfileWebClientGateway userProfileWebClientGateway;
    private final UserProfileDBRedisGateway userProfileDBRedisGateway;
    private final UserProfileSqsGateway userProfileSqsGateway;


    public Flux<UserProfile> getAll(){
        return userProfileDBPostgresGateway.getAll()
                .onErrorMap(ex -> new TechnicalException(ex, TechnicalMessage.UNEXPECTED_SERVICE_ERROR));
    }

    public Flux<UserProfile> getByFirstName(String firstName){
        return userProfileDBPostgresGateway.getByFirstName(firstName)
                .onErrorMap(ex -> new TechnicalException(ex, TechnicalMessage.UNEXPECTED_SERVICE_ERROR));
    }

    public Mono<UserProfile> getById(Long id){
        return userProfileDBRedisGateway.findById(id.toString())
                .switchIfEmpty(userProfileDBPostgresGateway.getById(id)
                    .switchIfEmpty(
                            Mono.error(new BusinessException(TechnicalMessage.USER_NOT_FOUND.getMessage() + id, TechnicalMessage.USER_NOT_FOUND))
                    )
                ).onErrorMap(ex -> new TechnicalException(ex, TechnicalMessage.UNEXPECTED_SERVICE_ERROR));
    }

    public Mono<UserProfile> createExternal(Long id) {
        return userProfileDBRedisGateway.findById(id.toString())
                .switchIfEmpty(userProfileDBPostgresGateway.getById(id)
                        .switchIfEmpty(userProfileWebClientGateway.getUserById(id)
                                        .flatMap(userProfileDBPostgresGateway::create)
                                                .doOnSuccess(userProfile ->
                                                        userProfileSqsGateway.sendUser(userProfile).subscribe()
                                                )
                                                .flatMap(userProfile ->
                                                        userProfileDBRedisGateway.save(id.toString(), userProfile)
                                                )
                                )
                )
                .cast(UserProfile.class)
                .onErrorMap(ex -> new TechnicalException(ex, TechnicalMessage.INTERNAL_ERROR));
    }
}
