package com.journey.server.dto.place;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreatePlaceDTO {
    int authorId;
    boolean isVisited;
    String title;
    int dttmUpdate;
    String cardText;
}
