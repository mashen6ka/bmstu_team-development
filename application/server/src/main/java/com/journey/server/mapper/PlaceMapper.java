package com.journey.server.mapper;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.dto.place.FullInfoPlaceDTO;
import com.journey.server.dto.place.UpdateIsVisitedDTO;
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
     * @param username имя пользователя, создавшего место
     * @return DTO с информацией о месте, необходимой пользователю
     */
    public FullInfoPlaceDTO toFullInfoPlaceDTO(PlaceEntity place, String username) {
        return FullInfoPlaceDTO.builder()
                .id(place.getId())
                .title(place.getTitle())
                .authorName(username)
                .cardText(place.getCardText())
                .isVisited(place.isVisited())
                .dttmUpdate(place.getDttmUpdate())
                .build();
    }

    /**
     * Конвертация из сущности DTO создания места в сущность БД
     * @param dto DTO с информацией о месте, полученной от пользователя
     * @return сущность БД, описывающая место
     */
        public PlaceEntity fromCreatePlaceDTO(CreatePlaceDTO dto, int authorId) {
        return PlaceEntity.builder()
                .authorId(authorId)
                .title(dto.getTitle())
                .isVisited(dto.isVisited())
                .dttmUpdate(dto.getDttmUpdate())
                .cardText(dto.getCardText())
                .build();
    }

    /**
     * Конвертация из сущности DTO обновления статуса посещенности места в сущность БД
     * @param dto DTO с информацией о месте, полученной от пользователя
     * @return сущность БД, описывающая место
     */
    public PlaceEntity fromUpdateIsVisitedDTO(UpdateIsVisitedDTO dto) {
        return PlaceEntity.builder()
                .isVisited(dto.isVisited())
                .dttmUpdate(dto.getDttmUpdate())
                .build();
    }
}
