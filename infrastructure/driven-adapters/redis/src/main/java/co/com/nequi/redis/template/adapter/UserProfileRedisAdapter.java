package co.com.nequi.redis.template.adapter;

import co.com.nequi.model.user.model.UserProfile;
import co.com.nequi.model.user.gateways.UserProfileDBRedisGateway;
import co.com.nequi.redis.template.entity.UserRedis;
import co.com.nequi.redis.template.helper.ReactiveTemplateAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class UserProfileRedisAdapter extends ReactiveTemplateAdapterOperations<UserProfile,String, UserRedis>
implements UserProfileDBRedisGateway {

    public UserProfileRedisAdapter(ReactiveRedisConnectionFactory connectionFactory, ObjectMapper mapper) {


        super(connectionFactory, mapper, d -> mapper.map(d, UserProfile.class));
    }
}
