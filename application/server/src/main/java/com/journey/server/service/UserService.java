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

/**
 * Класс в слое сервисов, реализующий авторизацию и регистрацию пользователей
 */
@Service
public class UserService {
    /**
     * Репозиторий, работающий с пользователями в базе данных
     */
    private final IUserRepo repo;

    /**
     * Словарь, хранящий ID пользователя и соответствующий refresh-токен
     */
    private final Map<Integer, String> refreshStorage = new HashMap<>();

    /**
     * Объект, выдающий и валидирующий токены
     */
    private final JwtProvider jwtProvider;

    /**
     * Конструктор сервиса
     * @param repo объект репозитория, работающего с пользователями в БД
     * @param provider объект, генерирующий и валидирующий токены
     */
    public UserService(IUserRepo repo, JwtProvider provider) {
        this.repo = repo;
        this.jwtProvider = provider;
    }

    /**
     * Получение информации о пользователе по идентификатору
     * @param id идентификатор пользователя, информацию о котором требуется получить
     * @return объект UserEntity, содержащий информацию о пользователе
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    public UserEntity getUserById(int id) throws SQLException {
        return repo.getUserById(id);
    }

    /**
     * Создание новой записи о пользователе в базе данных
     * @param user сущность UserEntity, содержашая информацию о пользователе при его регистрации
     * @return идентификатор, назначенный новому пользователю
     * @throws LoginConflictException если пользователь с указанным при регистрации логином уже занят
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    public int register(UserEntity user) throws SQLException, LoginConflictException {
        return repo.createUser(user);
    }

    /**
     * Аутентификация пользователя
     * @param authRequest сущность JwtRequestDTO, содержашая логин и пароль, введенные пользователем
     * @return сущность с информацией о сгенерированных для пользователя токенах, если
     * пароль подошел, иначе в полях accessToken, refreshToken отдается значение null
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    public JwtResponseDTO auth(@NonNull JwtRequestDTO authRequest) throws SQLException {
        int id = 0;
        try {
            id = repo.authenticate(authRequest.getLogin(), authRequest.getHash());
        } catch (WrongPasswordException e) {
            e.printStackTrace();
            return new JwtResponseDTO(null, null);
        }

        UserEntity user = repo.getUserById(id);
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getId(), refreshToken);

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    /**
     * Получение свежего токена access
     * @param refreshToken refresh-токен, полученный от пользователя
     * @return сущность с информацией о сгенерированных для пользователя токенах, если
     * пароль подошел, иначе в полях accessToken, refreshToken отдается значение null
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
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

    /**
     * Получение свежего токена refresh (и вместе с ним генерируется access-токен)
     * @param refreshToken refresh-токен, полученный от пользователя
     * @return сущность с информацией о сгенерированных для пользователя токенах
     * @throws AuthException при невалидном refresh-токене
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
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

    /**
     * Получение информации о пользователе из JWT-токена
     * @return объект JwtAuthentication, получаемый из контекста JWT-токена
     */
    public JwtAuthentication getAuthInfo() {
        JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        auth.setRole(JwtRole.USER);
        return auth;
    }
}
