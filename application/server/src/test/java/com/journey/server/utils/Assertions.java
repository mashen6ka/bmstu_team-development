package com.journey.server.utils;

import static org.junit.jupiter.api.Assertions.*;
import com.journey.server.dto.place.FullInfoPlaceDTO;

public class Assertions {
    public static void assertDTOEquals(FullInfoPlaceDTO dto1, FullInfoPlaceDTO dto2) {
        assertEquals(dto1.getId(), dto2.getId());
        assertEquals(dto1.getAuthorName(), dto2.getAuthorName());
        assertEquals(dto1.getTitle(), dto2.getTitle());
        assertEquals(dto1.isVisited(), dto2.isVisited());
        assertEquals(dto1.getDttmUpdate(), dto2.getDttmUpdate());
        assertEquals(dto1.getCardText(), dto2.getCardText());
    }
}
