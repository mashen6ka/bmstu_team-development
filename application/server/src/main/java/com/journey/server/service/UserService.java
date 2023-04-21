package com.journey.server.service;

import com.journey.server.entity.UserEntity;
import com.journey.server.exceptions.LoginConflictException;
import com.journey.server.exceptions.WrongPasswordException;
import com.journey.server.jwt.*;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final IUserRepo repo;
    private final Map<Integer, String> refreshStorage = new HashMap<>();
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
        refreshStorage.put(user.getId(), refreshToken);

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    public JwtResponseDTO getAccessToken(@NonNull String refreshToken) throws SQLException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final int id = claims.get("id", Integer.class);
            final String saveRefreshToken = refreshStorage.get(id);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = repo.getUserById(id);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponseDTO(accessToken, null);
            }
        }

        return new JwtResponseDTO(null, null);
    }

    public JwtResponseDTO refresh(@NonNull String refreshToken) throws AuthException, SQLException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final int id = claims.get("id", Integer.class);
            final String saveRefreshToken = refreshStorage.get(id);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = repo.getUserById(id);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                refreshStorage.put(user.getId(), newRefreshToken);
                return new JwtResponseDTO(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        auth.setRole(JwtRole.USER);
        return auth;
    }
}
