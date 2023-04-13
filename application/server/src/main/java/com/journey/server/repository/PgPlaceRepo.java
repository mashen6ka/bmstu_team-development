package com.journey.server.repository;

import com.journey.server.entity.PlaceEntity;
import com.journey.server.service.IPlaceRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PgPlaceRepo implements IPlaceRepo {

    @Override
    public ArrayList<PlaceEntity> getPlacesListByUserId(int userId) {
        ArrayList<PlaceEntity> placeEntities = new ArrayList<>();

        placeEntities.add(PlaceEntity.builder().title("Lalala").dttmUpdate(1681401615).build());
        placeEntities.add(PlaceEntity.builder().title("Pupupu").dttmUpdate(168140161).build());

        return placeEntities;
    }
}
