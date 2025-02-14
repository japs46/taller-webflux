package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.model.UserProfile;
import reactor.core.publisher.Mono;

public interface UserProfileWebClientGateway {

    public Mono<UserProfile> getUserById(Long id);
}
