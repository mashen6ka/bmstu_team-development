package com.journey.server.service;

import com.journey.server.entity.PlaceEntity;

import java.util.ArrayList;

public interface IPlaceRepo {
    ArrayList<PlaceEntity> getPlacesListByUserId(int userId);
}
