package com.journey.server.dto.place;

public record FullInfoPlaceDTO (
    String authorName,
    boolean isVisited,
    String title,
    String cardText
) {

}