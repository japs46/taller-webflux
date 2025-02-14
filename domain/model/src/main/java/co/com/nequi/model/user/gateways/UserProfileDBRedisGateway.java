package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.model.UserProfile;
import reactor.core.publisher.Mono;

public interface UserProfileDBRedisGateway {

    public Mono<UserProfile> findById(String id);

    public Mono<UserProfile> save(String id,UserProfile userProfile);

}
