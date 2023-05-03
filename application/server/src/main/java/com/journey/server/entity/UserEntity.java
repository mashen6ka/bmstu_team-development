package com.journey.server.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * Сущность, описывающая пользователя, поля соответствуют полям в базе данных
 */
@Builder
@Getter
public class UserEntity {
    /**
     * Идентификатор пользователя
     */
    int id;

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
