package com.journey.server.dto.place;

import lombok.Builder;
import lombok.Data;

/**
 * Сущность DTO, содержащая информацию о месте (is_visited, dttm_update), которую указывает пользователь при изменении статуса посещенности места
 */
@Builder
@Data
public class UpdateIsVisitedDTO {
    /**
     * Флаг посещенности места (true - посещенное, false - желаемое)
     */
    boolean isVisited;

    /**
     * Время последнего обновления карточки места (в секундах от начала Эпохи)
     */
    int dttmUpdate;
}
