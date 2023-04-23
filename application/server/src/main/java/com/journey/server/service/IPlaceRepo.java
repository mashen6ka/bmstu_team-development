package com.journey.server.service;

import com.journey.server.entity.PlaceEntity;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Интерфейс репозитория используется для работы с таблицей, отвечающей за места в базе данных
 */
public interface IPlaceRepo {
    /**
     * Получение информации обо всех местах, созданных пользователем
     * @param userId идентификатор пользователя, места которого требуется получить
     * @return список объектов PlaceEntity, содержащий информацию о всех местах, желаемых и посещенных пользователем
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    ArrayList<PlaceEntity> getPlaceListByUserId(int userId) throws SQLException;

    /**
     * Получение информации о желаемых или посещенных местах, созданных пользователем
     * @param userId идентификатор пользователя, места которого требуется получить
     * @param isVisited флаг, требуется список желаемых или посещенных мест (true, если посещенных)
     * @return список объектов PlaceEntity, содержащий информацию о всех местах, желаемых или посещенных пользователем (в зависимости от isVisited)
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    ArrayList<PlaceEntity> getPlaceListByUserId(int userId, boolean isVisited) throws SQLException;

    /**
     * Получение информации о месте по ее идентификатору
     * @param id идентификатор места, информацию о котором требуется получить
     * @return объект PlaceEntity с информацией из БД о заданном месте
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    PlaceEntity getPlaceById(int id) throws SQLException;

    /**
     * Удаление места по ID
     * @param id идентификатор места, информацию о котором требуется удалить
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    void deletePlaceById(int id) throws SQLException;

    /**
     * Создание нового места в БД
     * @param place объект с информацией, которую пользователь ввел при создании карточки места
     * @return идентификатор, назначенный в БД новому месту
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    int createPlace(PlaceEntity place) throws SQLException;

    /**
     * Обновление нового места в БД
     * @param id идентификатор места, информацию о котором требуется обновить
     * @param place объект с информацией, которую пользователь ввел при редактировании карточки места
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    void updatePlace(int id, PlaceEntity place) throws SQLException;

    /**
     * Перевод места в список посещенных или желаемых
     * @param id идентификатор места, статус посещенности которого требуется обновить
     * @param isVisited флаг, помечаем место как посещенное (true) или желаемое (false)
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    void updateIsVisited(int id, boolean isVisited) throws SQLException;
}
