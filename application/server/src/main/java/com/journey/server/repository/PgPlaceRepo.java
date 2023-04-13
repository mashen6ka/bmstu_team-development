package com.journey.server.repository;

import com.journey.server.entity.PlaceEntity;
import com.journey.server.service.IPlaceRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PgPlaceRepo implements IPlaceRepo {

    @Override
    public ArrayList<PlaceEntity> getPlaceListByUserId(int userId) {
        ArrayList<PlaceEntity> placeEntities = new ArrayList<>();

        placeEntities.add(PlaceEntity.builder().title("Lalala").dttmUpdate(1681401615).build());
        placeEntities.add(PlaceEntity.builder().title("Pupupu").dttmUpdate(168140161).build());

        return placeEntities;
    }

    @Override
    public PlaceEntity getPlaceById(int id) {
        return PlaceEntity.builder().title("okokok").dttmUpdate(1681000015).build();
    }

    @Override
    public void deletePlaceById(int id) {

    }

    @Override
    public int createPlace(PlaceEntity place) {
        return 0;
    }
}
