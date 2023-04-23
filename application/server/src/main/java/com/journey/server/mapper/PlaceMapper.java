package com.journey.server.mapper;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.dto.place.FullInfoPlaceDTO;
import com.journey.server.entity.PlaceEntity;
import com.journey.server.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Класс, конвертирующий DTO с информацией о местах в сущности БД и обратно
 */
@Component
public class PlaceMapper {
    /**
     * Конвертация из сущности БД в сущность DTO
     * @param place сущность БД, описывающая место
     * @param user сущность БД, описывающая пользователя, создавшего место
     * @return DTO с информацией о месте, необходимой пользователю
     */
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

    /**
     * Конвертация из сущности DTO в сущность БД
     * @param dto DTO с информацией о месте, полученной от пользователя
     * @return сущность БД, описывающая место
     */
    public PlaceEntity fromCreatePlaceDTO(CreatePlaceDTO dto) {
        return PlaceEntity.builder()
                .authorId(dto.getAuthorId())
                .title(dto.getTitle())
                .isVisited(dto.isVisited())
                .dttmUpdate(dto.getDttmUpdate())
                .cardText(dto.getCardText())
                .build();
    }
}
