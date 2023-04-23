package com.journey.server.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * Сущность DTO, содержащая информацию о пользователе, которую тот указывает при регистрации
 */
@Builder
@Data
public class RegistryUserDTO {
    /**
     * Логин пользователя
     */
    String login;

    /**
     * Хеш пароля пользователя
     */
    String hash;

    /**
     * Имя пользователя
     */
    String name;
}
