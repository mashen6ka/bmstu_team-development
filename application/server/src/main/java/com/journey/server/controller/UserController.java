package com.journey.server.controller;

import com.journey.server.dto.user.RegistryUserDTO;
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
        int id = userService.register(mapper.fromRegistryUserDTO(user));

        HttpHeaders responseHeaders = new HttpHeaders();

        HttpStatus httpStatus;
        if (id > 0) {
            httpStatus = HttpStatus.CREATED;

            URI location = new URI("/users/" + id);
            responseHeaders.setLocation(location);
        } else {
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(responseHeaders, httpStatus);
    }
}
