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

    public ArrayList<PlaceEntity> getPlacesListByUserId(int userId) {
        return repo.getPlacesListByUserId(userId);
    }
}
