package com.kridan.storage.auth.adapter.out.persistence;

import com.kridan.storage.auth.application.domain.model.User;

public class UserEntityMapper {
    public static User toDomain(UserEntity userEntity) {
        return new User()
                .setId(userEntity.getId())
                .setLogin(userEntity.getLogin())
                .setPassword(userEntity.getPassword());
    }
    public static UserEntity toEntity(User user) {
        return new UserEntity()
                .setId(user.getId())
                .setLogin(user.getLogin())
                .setPassword(user.getPassword());
    }
}
