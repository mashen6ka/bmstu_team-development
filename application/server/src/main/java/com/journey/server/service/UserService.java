package com.journey.server.service;

import com.journey.server.entity.UserEntity;
import com.journey.server.exceptions.LoginConflictException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {
    private final IUserRepo repo;

    public UserService(IUserRepo repo) {
        this.repo = repo;
    }

    public UserEntity getUserById(int id) {
        return repo.getUserById(id);
    }

    public int register(UserEntity user) throws SQLException, LoginConflictException {
        return repo.createUser(user);
    }
}
