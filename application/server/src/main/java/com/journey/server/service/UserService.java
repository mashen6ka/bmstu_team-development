package com.journey.server.service;

import com.journey.server.entity.UserEntity;
import com.journey.server.exceptions.LoginConflictException;
import com.journey.server.exceptions.WrongPasswordException;
import com.journey.server.jwt.JwtProvider;
import com.journey.server.jwt.JwtRequestDTO;
import com.journey.server.jwt.JwtResponseDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final IUserRepo repo;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public UserService(IUserRepo repo, JwtProvider provider) {
        this.repo = repo;
        this.jwtProvider = provider;
    }

    public UserEntity getUserById(int id) throws SQLException {
        return repo.getUserById(id);
    }

    public int register(UserEntity user) throws SQLException, LoginConflictException {
        return repo.createUser(user);
    }

    public JwtResponseDTO auth(@NonNull JwtRequestDTO authRequest) throws SQLException {
        int id = 0;
        try {
            id = repo.authenticate(authRequest.getLogin(), authRequest.getHash());
        } catch (WrongPasswordException e) {
            e.printStackTrace();
        }

        UserEntity user = repo.getUserById(id);
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getLogin(), refreshToken);

        return new JwtResponseDTO(accessToken, refreshToken);

    }
}
