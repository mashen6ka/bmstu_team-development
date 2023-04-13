package com.journey.server.repository;

import com.journey.server.entity.UserEntity;
import com.journey.server.service.IUserRepo;
import org.springframework.stereotype.Repository;

@Repository
public class PgUserRepo implements IUserRepo {
    @Override
    public UserEntity getUserById(int id) {
        return UserEntity.builder().name("Bob").build();
    }
}
