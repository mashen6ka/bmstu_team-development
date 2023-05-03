package com.journey.server.dto.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность DTO, содержащая информацию о месте (is_visited, dttm_update), которую указывает пользователь при изменении статуса посещенности места
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIsVisitedDTO {
    /**
     * Флаг посещенности места (true - посещенное, false - желаемое)
     */
    boolean isVisited;

    /**
     * Время последнего обновления карточки места (в секундах от начала Эпохи)
     */
    int dttmUpdate;

    @JsonProperty("isVisited")
    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
