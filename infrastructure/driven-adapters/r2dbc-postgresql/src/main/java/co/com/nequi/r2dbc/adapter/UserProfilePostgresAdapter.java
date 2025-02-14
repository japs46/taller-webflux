package co.com.nequi.r2dbc.adapter;

import co.com.nequi.model.user.model.UserProfile;
import co.com.nequi.model.user.gateways.UserProfileDBPostgresGateway;
import co.com.nequi.r2dbc.mapper.UserProfileMapper;
import co.com.nequi.r2dbc.repository.UserProfileEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserProfilePostgresAdapter implements UserProfileDBPostgresGateway {

    private final UserProfileEntityRepository userEntityRepository;

    @Override
    public Flux<UserProfile> getAll() {
        return userEntityRepository.findAll()
                .map(UserProfileMapper::toDomain);
    }

    @Override
    public Flux<UserProfile> getByFirstName(String firstName) {
        return userEntityRepository.findByFirstName(firstName)
                .map(UserProfileMapper::toDomain);
    }

    @Override
    public Mono<UserProfile> getById(Long id) {
        return userEntityRepository.findById(id)
                .map(UserProfileMapper::toDomain);
    }

    @Override
    public Mono<UserProfile> create(UserProfile userProfile) {

        return userEntityRepository.insert(userProfile.getId(),userProfile.getEmail(),userProfile.getFirstName(),
                        userProfile.getLastName(),userProfile.getAvatar())
                .map(UserProfileMapper::toDomain);
    }
}
