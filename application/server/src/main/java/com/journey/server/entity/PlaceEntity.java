package com.journey.server.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * Сущность, описывающая место, поля соответствуют полям в базе данных
 */
@Builder
@Getter
public class PlaceEntity {
    /**
     * Идентификатор места
     */
    int id;

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
