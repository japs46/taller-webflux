package co.com.nequi.r2dbc.repository;


import co.com.nequi.r2dbc.entity.UserProfileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserProfileEntityRepository extends ReactiveCrudRepository<UserProfileEntity,Long>{

    @Query("""
        INSERT INTO user_profile (id, email, first_name, last_name, avatar)
        VALUES (:id, :email, :firstName, :lastName, :avatar)
        RETURNING *
    """)
    Mono<UserProfileEntity> insert(Long id,String email, String firstName, String lastName, String avatar);

    @Query("SELECT * FROM user_profile WHERE LOWER(first_name) = LOWER(:firstName)")
    public Flux<UserProfileEntity> findByFirstName(String firstName);
}
