package com.journey.server.service;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.entity.PlaceEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IPlaceRepo {
    ArrayList<PlaceEntity> getPlaceListByUserId(int userId) throws SQLException;

    ArrayList<PlaceEntity> getPlaceListByUserId(int userId, boolean isVisited) throws SQLException;

    PlaceEntity getPlaceById(int id);

    void deletePlaceById(int id);

    int createPlace(PlaceEntity place) throws SQLException;

    void updatePlace(int id, CreatePlaceDTO place);

    void updateIsVisited(int id, boolean isVisited);
}
