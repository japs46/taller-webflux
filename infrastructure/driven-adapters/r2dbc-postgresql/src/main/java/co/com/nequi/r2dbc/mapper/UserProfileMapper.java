package co.com.nequi.r2dbc.mapper;

import co.com.nequi.model.user.model.UserProfile;
import co.com.nequi.r2dbc.entity.UserProfileEntity;

public class UserProfileMapper {
    public static UserProfile toDomain(UserProfileEntity entity) {
        return UserProfile.builder()
                        .id(entity.getId())
                        .email(entity.getEmail())
                        .firstName(entity.getFirstName())
                        .lastName(entity.getLastName())
                        .avatar(entity.getAvatar())
                .build();
    }

    public static UserProfileEntity toEntity(UserProfile model) {
        return UserProfileEntity.builder()
                .id(model.getId())
                .email(model.getEmail())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .avatar(model.getAvatar())
                .build();
    }
}
