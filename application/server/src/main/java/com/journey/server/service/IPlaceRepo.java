package com.journey.server.service;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.entity.PlaceEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IPlaceRepo {
    ArrayList<PlaceEntity> getPlaceListByUserId(int userId) throws SQLException;

    ArrayList<PlaceEntity> getPlaceListByUserId(int userId, boolean isVisited) throws SQLException;

    PlaceEntity getPlaceById(int id) throws SQLException;

    void deletePlaceById(int id) throws SQLException;

    int createPlace(PlaceEntity place) throws SQLException;

    void updatePlace(int id, PlaceEntity place) throws SQLException;

    void updateIsVisited(int id, boolean isVisited) throws SQLException;
}
