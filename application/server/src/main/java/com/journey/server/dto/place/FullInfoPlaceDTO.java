package com.journey.server.dto.place;

import lombok.Builder;
import lombok.Data;

/**
 * Сущность DTO, содержащая информацию о месте, которая возвращается пользователю при GET-запросах
 */
@Builder
@Data
public class FullInfoPlaceDTO {
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
    String dttmUpdate;
}