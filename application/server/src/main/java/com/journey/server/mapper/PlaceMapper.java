package com.journey.server.mapper;

import com.journey.server.dto.place.FullInfoPlaceDTO;
import com.journey.server.entity.PlaceEntity;
import com.journey.server.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class PlaceMapper {
    public FullInfoPlaceDTO toFullInfoPlaceDTO(PlaceEntity place, UserEntity user) {
        Instant instant = Instant.ofEpochSecond(place.getDttmUpdate());
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return FullInfoPlaceDTO.builder()
                .title(place.getTitle())
                .authorName(user.getName())
                .cardText(place.getCardText())
                .isVisited(place.isVisited())
                .dttmUpdate(dateTime.format(formatter))
                .build();
    }
}
