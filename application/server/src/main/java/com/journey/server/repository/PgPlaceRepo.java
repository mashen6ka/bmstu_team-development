package com.journey.server.repository;

import com.journey.server.entity.PlaceEntity;
import com.journey.server.service.IPlaceRepo;
import com.journey.server.utils.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Репозиторий используется для работы с таблицей places базы данных PostgreSQL
 * Для подключения используется драйвер JDBC
 */
@Repository
public class PgPlaceRepo implements IPlaceRepo {
    /**
     * Объект подключения к БД
     */
    private final Connection conn = ConnectionManager.open();

    /**
     * Получение информации обо всех местах, созданных пользователем
     * @param userId идентификатор пользователя, места которого требуется получить
     * @return список объектов PlaceEntity, содержащий информацию о всех местах, желаемых и посещенных пользователем
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public ArrayList<PlaceEntity> getPlaceListByUserId(int userId) throws SQLException {
        ArrayList<PlaceEntity> places = new ArrayList<>();

        String getPlaces = "SELECT place_id, author_id, is_visited, title, " +
                "dttm_update, card_text FROM public.places WHERE author_id = ? " +
                "ORDER BY dttm_update DESC";

        PreparedStatement userPlaceQuery = conn.prepareStatement(getPlaces);
        userPlaceQuery.setInt(1, userId);
        ResultSet rs = userPlaceQuery.executeQuery();

        while (rs.next()) {
            places.add(PlaceEntity.builder()
                    .id(rs.getInt("place_id"))
                    .authorId(rs.getInt("author_id"))
                    .isVisited(rs.getBoolean("is_visited"))
                    .title(rs.getString("title"))
                    .dttmUpdate(rs.getInt("dttm_update"))
                    .cardText(rs.getString("card_text"))
                    .build());
        }

        return places;
    }

    /**
     * Получение информации о желаемых или посещенных местах, созданных пользователем
     * @param userId идентификатор пользователя, места которого требуется получить
     * @param isVisited флаг, требуется список желаемых или посещенных мест (true, если посещенных)
     * @return список объектов PlaceEntity, содержащий информацию о всех местах, желаемых или посещенных пользователем (в зависимости от isVisited)
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public ArrayList<PlaceEntity> getPlaceListByUserId(int userId, boolean isVisited) throws SQLException {
        ArrayList<PlaceEntity> places = new ArrayList<>();

        String getPlaces = "SELECT place_id, author_id, is_visited, title, " +
                "dttm_update, card_text FROM public.places " +
                "WHERE author_id = ? AND is_visited = ? " +
                "ORDER BY dttm_update DESC";

        PreparedStatement userPlaceQuery = conn.prepareStatement(getPlaces);
        userPlaceQuery.setInt(1, userId);
        userPlaceQuery.setBoolean(2, isVisited);
        ResultSet rs = userPlaceQuery.executeQuery();

        while (rs.next()) {
            places.add(PlaceEntity.builder()
                    .id(rs.getInt("place_id"))
                    .authorId(rs.getInt("author_id"))
                    .isVisited(rs.getBoolean("is_visited"))
                    .title(rs.getString("title"))
                    .dttmUpdate(rs.getInt("dttm_update"))
                    .cardText(rs.getString("card_text"))
                    .build());
        }

        return places;
    }

    /**
     * Получение информации о месте по ее идентификатору
     * @param id идентификатор места, информацию о котором требуется получить
     * @return объект PlaceEntity с информацией из БД о заданном месте
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public PlaceEntity getPlaceById(int id) throws SQLException {
        PlaceEntity place = null;

        String getPlace = "SELECT place_id, author_id, is_visited, title, " +
                "dttm_update, card_text FROM public.places WHERE place_id = ?";

        PreparedStatement placeQuery = conn.prepareStatement(getPlace);
        placeQuery.setInt(1, id);
        ResultSet rs = placeQuery.executeQuery();

        while (rs.next()) {
            place = PlaceEntity.builder()
                    .id(rs.getInt("place_id"))
                    .authorId(rs.getInt("author_id"))
                    .isVisited(rs.getBoolean("is_visited"))
                    .title(rs.getString("title"))
                    .dttmUpdate(rs.getInt("dttm_update"))
                    .cardText(rs.getString("card_text"))
                    .build();
        }

        return place;
    }

    /**
     * Удаление места по ID
     * @param id идентификатор места, информацию о котором требуется удалить
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public void deletePlaceById(int id) throws SQLException {
        String deletePlace = "DELETE FROM public.places WHERE place_id = ?";

        PreparedStatement placeDeletion = conn.prepareStatement(deletePlace);
        placeDeletion.setInt(1, id);
        placeDeletion.executeUpdate();
    }

    /**
     * Создание нового места в БД
     * @param place объект с информацией, которую пользователь ввел при создании карточки места
     * @return идентификатор, назначенный в БД новому месту
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public int createPlace(PlaceEntity place) throws SQLException {
        int id = 0;
        try {
            String placeAdd = "INSERT INTO public.places " +
                                "(author_id, is_visited, title, dttm_update, card_text) " +
                                "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement placeInsertion = conn.prepareStatement(placeAdd);
            placeInsertion.setInt(1, place.getAuthorId());
            placeInsertion.setBoolean(2, place.isVisited());
            placeInsertion.setString(3, place.getTitle());
            placeInsertion.setInt(4, place.getDttmUpdate());
            placeInsertion.setString(5, place.getCardText());
            placeInsertion.executeUpdate();

            String checkId = "SELECT place_id FROM public.places " +
                                "WHERE author_id = ? AND dttm_update = ?";
            PreparedStatement check = conn.prepareStatement(checkId);
            check.setInt(1, place.getAuthorId());
            check.setInt(2, place.getDttmUpdate());

            ResultSet rs = check.executeQuery();
            rs.next();
            id = rs.getInt("place_id");
        } catch (SQLException e) {
            e.printStackTrace();

            throw new SQLException("Не удалось добавить место " +
                        "в базу данных");
        }

        return id;
    }

    /**
     * Обновление нового места в БД
     * @param id идентификатор места, информацию о котором требуется обновить
     * @param place объект с информацией, которую пользователь ввел при редактировании карточки места
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public void updatePlace(int id, PlaceEntity place) throws SQLException {
        String updPlace = "UPDATE public.places " +
                "SET (is_visited, title, dttm_update, card_text) = (?, ?, ?, ?) " +
                "WHERE place_id = ?";

        PreparedStatement placeUpdate = conn.prepareStatement(updPlace);
        placeUpdate.setBoolean(1, place.isVisited());
        placeUpdate.setString(2, place.getTitle());
        placeUpdate.setInt(3, place.getDttmUpdate());
        placeUpdate.setString(4, place.getCardText());
        placeUpdate.setInt(5, id);
        placeUpdate.executeUpdate();
    }

    /**
     * Перевод места в список посещенных или желаемых
     * @param id идентификатор места, статус посещенности которого требуется обновить
     * @param isVisited флаг, помечаем место как посещенное (true) или желаемое (false)
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Override
    public void updateIsVisited(int id, boolean isVisited) throws SQLException {
        String setVisitedPlace = "UPDATE public.places SET is_visited = ? WHERE place_id = ?";

        PreparedStatement placeSetVisit = conn.prepareStatement(setVisitedPlace);
        placeSetVisit.setBoolean(1, isVisited);
        placeSetVisit.setInt(2, id);
        placeSetVisit.executeUpdate();
    }
}
