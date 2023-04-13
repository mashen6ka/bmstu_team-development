package com.journey.server.service;

import com.journey.server.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IUserRepo repo;

    public UserService(IUserRepo repo) {
        this.repo = repo;
    }

    public UserEntity getUserById(int id) {
        return repo.getUserById(id);
    }
}
