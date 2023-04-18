package com.journey.server.service;

import com.journey.server.entity.UserEntity;
import com.journey.server.exceptions.LoginConflictException;
import com.journey.server.exceptions.WrongPasswordException;

import java.sql.SQLException;

public interface IUserRepo {
    UserEntity getUserById(int id) throws SQLException;

    int createUser(UserEntity user) throws LoginConflictException, SQLException;

    int authenticate(String login, String hash) throws WrongPasswordException, SQLException ;
}
