package com.journey.server.utils;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.dto.place.FullInfoPlaceDTO;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class PlaceObjectMother {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static LocalDateTime dtTime = LocalDateTime.of(2023, Month.APRIL, 20, 15, 12);

    public static CreatePlaceDTO getSomeNewInputPlace() {
        int curTime = (int) dtTime.toEpochSecond(ZoneOffset.UTC);
        return CreatePlaceDTO.builder()
                .authorId(3)
                .isVisited(false)
                .title("Новое место")
                .cardText("Описание нового места")
                .dttmUpdate(curTime)
                .build();
    }

    public static FullInfoPlaceDTO getSomeNewOutputPlace() {
        return FullInfoPlaceDTO.builder()
                .authorName("Мария Слепокурова")
                .isVisited(false)
                .title("Новое место")
                .cardText("Описание нового места")
                .dttmUpdate(dtTime.format(formatter))
                .build();
    }

    public static FullInfoPlaceDTO getSomeExistingOutputPlace() {
        return FullInfoPlaceDTO.builder()
                .authorName("Мария Слепокурова")
                .isVisited(false)
                .title("Шанхай")
                .cardText("Описание 12")
                .dttmUpdate(dtTime.format(formatter))
                .build();
    }
}
