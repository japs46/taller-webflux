package co.com.nequi.consumer.mapper;

import co.com.nequi.consumer.response.Data;
import co.com.nequi.model.user.model.UserProfile;

public class UserMapper {

    public static UserProfile toDomain(Data data){
        return UserProfile.builder()
                .id(data.getId())
                .email(data.getEmail())
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .avatar(data.getAvatar())
                .build();
    }
}
