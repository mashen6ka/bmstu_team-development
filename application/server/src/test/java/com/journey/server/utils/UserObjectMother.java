package com.journey.server.utils;

import com.journey.server.dto.user.RegistryUserDTO;
import com.journey.server.jwt.JwtRequestDTO;

public class UserObjectMother {
    public static RegistryUserDTO getNewUser() {
        return RegistryUserDTO.builder()
                .login("user100")
                .hash("1234567865432345678765432123456543")
                .name("Тестовое Имя")
                .build();
    }

    public static RegistryUserDTO getNewUserWithConfilctLogin() {
        return RegistryUserDTO.builder()
                .login("user10")
                .hash("1234567865432345678765432123456543")
                .name("Тестовое Имя")
                .build();
    }

    public static JwtRequestDTO getCorrectJwtRequest() {
        JwtRequestDTO dto = new JwtRequestDTO();
        dto.setLogin("user1");
        dto.setHash("0a041b9462caa4a31bac3567e0b6e6fd9100787db2ab433d96f6d178cabfce90");
        return dto;
    }

    public static JwtRequestDTO getIncorrectJwtRequest() {
        JwtRequestDTO dto = new JwtRequestDTO();
        dto.setLogin("user1");
        dto.setHash("0a049462caa4a31bac3567e0b6e6fd9100787db2ab433d96f6d178cabfce90");
        return dto;
    }
}
