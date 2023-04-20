package com.journey.server.repository;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.entity.PlaceEntity;
import com.journey.server.entity.UserEntity;
import com.journey.server.exceptions.LoginConflictException;
import com.journey.server.service.IPlaceRepo;
import com.journey.server.utils.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class PgPlaceRepo implements IPlaceRepo {
    private final Connection conn = ConnectionManager.open();

    @Override
    public ArrayList<PlaceEntity> getPlaceListByUserId(int userId) throws SQLException {
        ArrayList<PlaceEntity> places = new ArrayList<>();

        String getPlaces = "SELECT place_id, author_id, is_visited, title " +
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

    @Override
    public ArrayList<PlaceEntity> getPlaceListByUserId(int userId, boolean isVisited) throws SQLException {
        ArrayList<PlaceEntity> places = new ArrayList<>();

        String getPlaces = "SELECT place_id, author_id, is_visited, title " +
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

    @Override
    public PlaceEntity getPlaceById(int id) throws SQLException {
        PlaceEntity place = null;

        String getPlace = "SELECT place_id, author_id, is_visited, title " +
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

    @Override
    public void deletePlaceById(int id) throws SQLException {
        String deletePlace = "DELETE FROM public.places WHERE place_id = ?";

        PreparedStatement placeDeletion = conn.prepareStatement(deletePlace);
        placeDeletion.setInt(1, id);
        placeDeletion.executeUpdate();
    }

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

    @Override
    public void updatePlace(int id, PlaceEntity place) {

    }

    @Override
    public void updateIsVisited(int id, boolean isVisited) throws SQLException {
        String setVisitedPlace = "UPDATE public.places SET is_visited = ? WHERE place_id = ?";

        PreparedStatement placeSetVisit = conn.prepareStatement(setVisitedPlace);
        placeSetVisit.setBoolean(1, isVisited);
        placeSetVisit.setInt(2, id);
        placeSetVisit.executeUpdate();
    }
}
