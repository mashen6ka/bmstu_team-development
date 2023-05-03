package com.journey.server.mapper;

import com.journey.server.dto.user.RegistryUserDTO;
import com.journey.server.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Класс, конвертирующий DTO с информацией о пользователях в сущность БД
 */
@Component
public class UserMapper {
    /**
     * Конвертация DTO в сущность БД
     * @param dto DTO с информацией о пользователе, полученной при регистрации
     * @return сущность БД, описывающая пользователя
     */
    public UserEntity fromRegistryUserDTO(RegistryUserDTO dto) {
        return UserEntity.builder()
                .login(dto.getLogin())
                .hash(dto.getHash())
                .name(dto.getName())
                .build();
    }
}
