package com.journey.server.controller;

import com.journey.server.dto.user.RegistryUserDTO;
import com.journey.server.exceptions.LoginConflictException;
import com.journey.server.jwt.JwtRequestDTO;
import com.journey.server.jwt.JwtResponseDTO;
import com.journey.server.mapper.UserMapper;
import com.journey.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Класс, описывающий контроллер, обрабатывающий запросы клиента, связанные с авторизацией и регистрацией
 */
@RestController
@Tag(name = "USERS")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    /**
     * Сервис, работающий с пользователями
     */
    private final UserService userService;

    /**
     * Конвертер сущностей, описывающих пользователя
     */
    private final UserMapper mapper;

    /**
     * Конструктор контроллера
     * @param userService сервис, работающий с пользователями
     * @param mapper конвертер сущностей, описывающих место
     */
    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * Создание новой записи о пользователе в базе данных
     * @param user сущность RegistryUserDTO, содержашая информацию о пользователе, введенную при его регистрации
     * @return при успешной регистрации - новый URI и статус 201 CREATED, при конфликте логинов - 409 CONFLICT
     * @throws URISyntaxException при неуспешном создании URI нового пользователя
     */
    @Operation(summary = "User registration")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistryUserDTO user) throws URISyntaxException {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus;

        try {
            int id = userService.register(mapper.fromRegistryUserDTO(user));
            httpStatus = HttpStatus.CREATED;

            URI location = new URI("/users/" + id);
            responseHeaders.setLocation(location);
        } catch (LoginConflictException e) {
            httpStatus = HttpStatus.CONFLICT;
            e.printStackTrace();
        } catch (SQLException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }

        return new ResponseEntity<>(responseHeaders, httpStatus);
    }

    /**
     * Аутентификация пользователя
     * @param request сущность JwtRequestDTO, содержашая логин и пароль, введенные пользователем
     * @return сущность с информацией о сгенерированных для пользователя токенах и статус 201 CREATED, если
     * пароль подошел, иначе в полях accessToken, refreshToken отдается значение null, и статус 403 FORBIDDEN
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Operation(summary = "User authorization")
    @PostMapping("/auth")
    public ResponseEntity<JwtResponseDTO> auth(@RequestBody JwtRequestDTO request) throws SQLException {
        HttpStatus httpStatus;

        JwtResponseDTO response = userService.auth(request);

        if (response.getAccessToken() == null)
            httpStatus = HttpStatus.FORBIDDEN;
        else
            httpStatus = HttpStatus.CREATED;

        return new ResponseEntity<>(response, httpStatus);
    }
}
