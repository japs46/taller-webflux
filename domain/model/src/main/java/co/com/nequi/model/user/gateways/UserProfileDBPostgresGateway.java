package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.model.UserProfile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserProfileDBPostgresGateway {

    public Flux<UserProfile> getAll();

    public Flux<UserProfile> getByFirstName(String firstName);

    public Mono<UserProfile> getById(Long id);

    public Mono<UserProfile> create(UserProfile userProfile);
}
