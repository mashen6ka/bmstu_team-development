package com.journey.server.service;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.entity.PlaceEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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

    public int createPlace(PlaceEntity place) throws SQLException {
        return repo.createPlace(place);
    }

    public void updatePlace(int id, CreatePlaceDTO place) {
        repo.updatePlace(id, place);
    }

    public void updateIsVisited(int id, boolean isVisited) {
        repo.updateIsVisited(id, isVisited);
    }
}
