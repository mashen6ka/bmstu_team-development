package com.journey.server.dto.place;

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
     * Идентификатор пользователя, создавшего карточку места
     */
    int authorId;

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
}
