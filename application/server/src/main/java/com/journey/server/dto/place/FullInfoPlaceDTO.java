package com.journey.server.dto.place;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FullInfoPlaceDTO {
    String authorName;
    boolean isVisited;
    String title;
    String cardText;
}