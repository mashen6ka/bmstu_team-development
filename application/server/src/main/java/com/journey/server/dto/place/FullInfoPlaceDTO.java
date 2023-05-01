package com.journey.server.dto.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность DTO, содержащая информацию о месте, которая возвращается пользователю при GET-запросах
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullInfoPlaceDTO {
    /**
     * Идентификатор места
     */
    int id;

    /**
     * Имя пользователя, создавшего карточку места
     */
    String authorName;

    /**
     * Флаг посещенности места (true - посещенное, false - желаемое)
     */
    boolean isVisited;

    /**
     * Заголовок карточки места
     */
    String title;

    /**
     * Подробное описание карточки места
     */
    String cardText;

    /**
     * Время последнего обновления карточки места (в формате YYYY-MM-DD HH24:MI)
     */
    int dttmUpdate;

    @JsonProperty("isVisited")
    public boolean isVisited() {
        return this.isVisited;
    }
}