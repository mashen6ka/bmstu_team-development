package com.journey.server.mapper;

import com.journey.server.dto.place.FullInfoPlaceDTO;
import com.journey.server.entity.PlaceEntity;
import com.journey.server.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper {
    public FullInfoPlaceDTO toFullInfoPlaceDTO(PlaceEntity place, UserEntity user) {
        return new FullInfoPlaceDTO(user.getName(), place.isVisited(), place.getTitle(), place.getCardText());
    }
}
