package com.journey.server.utils;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.dto.place.FullInfoPlaceDTO;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class PlaceObjectMother {
    private static int dtTime = 1682003554;

    public static CreatePlaceDTO getSomeNewInputPlace() {
        return CreatePlaceDTO.builder()
                .isVisited(false)
                .title("Новое место")
                .cardText("Описание нового места")
                .dttmUpdate(dtTime)
                .build();
    }

    public static FullInfoPlaceDTO getSomeNewOutputPlace() {
        return FullInfoPlaceDTO.builder()
                .id(31)
                .authorName("Мария Слепокурова")
                .isVisited(false)
                .title("Новое место")
                .cardText("Описание нового места")
                .dttmUpdate(dtTime)
                .build();
    }

    public static FullInfoPlaceDTO getSomeExistingOutputPlace() {
        return FullInfoPlaceDTO.builder()
                .id(12)
                .authorName("Мария Слепокурова")
                .isVisited(false)
                .title("Шанхай")
                .cardText("Описание 12")
                .dttmUpdate(dtTime)
                .build();
    }
}
