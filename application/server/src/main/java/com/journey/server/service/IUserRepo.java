package com.journey.server.service;

import com.journey.server.entity.UserEntity;
import com.journey.server.exceptions.LoginConflictException;
import com.journey.server.exceptions.WrongPasswordException;

import java.sql.SQLException;

/**
 * Интерфейс репозитория используется для работы с таблицей, отвечающей за пользователей в базе данных
 */
public interface IUserRepo {
    /**
     * Получение информации о пользователе по идентификатору
     * @param id идентификатор пользователя, информацию о котором требуется получить
     * @return объект UserEntity, содержащий информацию о пользователе
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    UserEntity getUserById(int id) throws SQLException;

    /**
     * Создание новой записи о пользователе в базе данных
     * @param user сущность UserEntity, содержашая информацию о пользователе при его регистрации
     * @return идентификатор, назначенный новому пользователю в базе данных
     * @throws LoginConflictException если пользователь с указанным при регистрации логином уже занят
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    int createUser(UserEntity user) throws LoginConflictException, SQLException;

    /**
     * Проверка логина и пароля при входе
     * @param login логин пользователя
     * @param hash хеш пароля пользователя (SHA-256)
     * @return идентификатор пользователя, если в БД есть запись с указанными логином и паролем
     * @throws WrongPasswordException при несоответствии введенных логина и пароля
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    int authenticate(String login, String hash) throws WrongPasswordException, SQLException ;
}
