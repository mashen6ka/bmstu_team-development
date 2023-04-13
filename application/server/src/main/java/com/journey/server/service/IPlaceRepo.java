package com.journey.server.service;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.entity.PlaceEntity;

import java.util.ArrayList;

public interface IPlaceRepo {
    ArrayList<PlaceEntity> getPlaceListByUserId(int userId);

    PlaceEntity getPlaceById(int id);

    void deletePlaceById(int id);

    int createPlace(PlaceEntity place);

    void updatePlace(int id, CreatePlaceDTO place);

    void updateIsVisited(int id, boolean isVisited);
}
