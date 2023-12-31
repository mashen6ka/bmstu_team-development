package com.journey.server.service;

import com.journey.server.entity.PlaceEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс в слое сервисов, обращающийся к репозиторию мест
 */
@Service
public class PlaceService {
    /**
     * Репозиторий, работающий с местами в базе данных
     */
    private final IPlaceRepo repo;

    public PlaceService(IPlaceRepo repo) {
        this.repo = repo;
    }

    /**
     * Получение всех мест пользователя по его ID
     * @param userId идентификатор пользователя, места которого требуется получить
     * @return список сущностей, описывающих места данного пользователя
     * @throws SQLException  при неуспешном подключении или внутренней ошибке базы данных
     */
    public ArrayList<PlaceEntity> getPlaceListByUserId(int userId) throws SQLException {
        return repo.getPlaceListByUserId(userId);
    }

    /**
     * Получение информации о месте по ее идентификатору
     * @param id идентификатор места, информацию о котором требуется получить
     * @return объект PlaceEntity с информацией из БД о заданном месте
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    public PlaceEntity getPlaceById(int id) throws SQLException {
        return repo.getPlaceById(id);
    }

    /**
     * Удаление места по ID
     * @param id идентификатор места, информацию о котором требуется удалить
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    public void deletePlaceById(int id) throws SQLException {
        repo.deletePlaceById(id);
    }

    /**
     * Создание нового места в БД
     * @param place объект с информацией, которую пользователь ввел при создании карточки места
     * @return идентификатор, назначенный в БД новому месту
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    public int createPlace(PlaceEntity place) throws SQLException {
        return repo.createPlace(place);
    }

    /**
     * Обновление нового места в БД
     * @param id идентификатор места, информацию о котором требуется обновить
     * @param place объект с информацией, которую пользователь ввел при редактировании карточки места
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    public void updatePlace(int id, PlaceEntity place) throws SQLException {
        repo.updatePlace(id, place);
    }

    /**
     * Перевод места в список посещенных или желаемых
     * @param id идентификатор места, статус посещенности которого требуется обновить
     * @param place объект с информацией, которую пользователь ввел при обновлении статуса посещенности места
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    public void updateIsVisited(int id, PlaceEntity place) throws SQLException {
        repo.updateIsVisited(id, place);
    }
}
