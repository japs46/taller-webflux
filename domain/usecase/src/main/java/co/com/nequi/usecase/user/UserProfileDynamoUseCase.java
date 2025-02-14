package co.com.nequi.usecase.user;

import co.com.nequi.model.user.gateways.UserProfileDynamoBdGateway;
import co.com.nequi.model.user.model.UserProfile;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserProfileDynamoUseCase {

    private final UserProfileDynamoBdGateway userProfileDynamoBdGateway;

    public Mono<UserProfile> save(UserProfile userProfile){
        return userProfileDynamoBdGateway.save(userProfile);
    }
}
