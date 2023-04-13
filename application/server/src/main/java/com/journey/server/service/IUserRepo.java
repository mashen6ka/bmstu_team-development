package com.journey.server.service;

import com.journey.server.entity.UserEntity;

public interface IUserRepo {
    UserEntity getUserById(int id);

    int createUser(UserEntity user);
}
