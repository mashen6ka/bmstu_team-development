package com.journey.server.controller;

import com.journey.server.dto.user.RegistryUserDTO;
import com.journey.server.exceptions.LoginConflictException;
import com.journey.server.jwt.JwtRequestDTO;
import com.journey.server.jwt.JwtResponseDTO;
import com.journey.server.mapper.UserMapper;
import com.journey.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@Tag(name = "USERS")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Operation(summary = "User registration")
    @PostMapping("/registry")
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
