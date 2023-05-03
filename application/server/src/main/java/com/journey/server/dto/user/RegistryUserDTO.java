package com.journey.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность DTO, содержащая информацию о пользователе, которую тот указывает при регистрации
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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
