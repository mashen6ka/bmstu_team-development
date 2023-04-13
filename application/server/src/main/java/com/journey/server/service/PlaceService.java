package com.journey.server.service;

import com.journey.server.entity.PlaceEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlaceService {
    private final IPlaceRepo repo;

    public PlaceService(IPlaceRepo repo) {
        this.repo = repo;
    }

    public ArrayList<PlaceEntity> getPlaceListByUserId(int userId) {
        return repo.getPlaceListByUserId(userId);
    }

    public PlaceEntity getPlaceById(int id) {
        return repo.getPlaceById(id);
    }

    public void deletePlaceById(int id) {
        repo.deletePlaceById(id);
    }

    public int createPlace(PlaceEntity place) {
        return repo.createPlace(place);
    }
}
