package com.journey.server.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlaceEntity {
    int id;
    int authorId;
    boolean isVisited;
    String title;
    int dttmUpdate;
    String cardText;
}
