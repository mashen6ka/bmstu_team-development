package com.journey.server.dto.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность DTO, содержащая информацию о месте, которую указывает пользователь при создании/редактировании
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlaceDTO {
    /**
     * Флаг посещенности места (true - посещенное, false - желаемое)
     */
    boolean isVisited;

    /**
     * Заголовок карточки места
     */
    String title;

    /**
     * Время последнего обновления карточки места (в секундах от начала Эпохи)
     */
    int dttmUpdate;

    /**
     * Подробное описание карточки места
     */
    String cardText;

    @JsonProperty("isVisited")
    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
