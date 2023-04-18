package com.journey.server.service;

import com.journey.server.entity.UserEntity;
import com.journey.server.exceptions.LoginConflictException;

import java.sql.SQLException;

public interface IUserRepo {
    UserEntity getUserById(int id);

    int createUser(UserEntity user) throws LoginConflictException, SQLException;
}
