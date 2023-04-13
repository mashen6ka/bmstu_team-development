package com.journey.server.controller;

import com.journey.server.dto.place.FullInfoPlaceDTO;
import com.journey.server.entity.PlaceEntity;
import com.journey.server.entity.UserEntity;
import com.journey.server.mapper.PlaceMapper;
import com.journey.server.service.PlaceService;
import com.journey.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Tag(name = "PLACES")
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;
    private final UserService userService;
    private final PlaceMapper mapper;

    public PlaceController(PlaceService placeService, UserService userService, PlaceMapper mapper) {
        this.placeService = placeService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get place list")
    @GetMapping
    public ArrayList<FullInfoPlaceDTO> getPlaceList(@RequestParam int userId) {
        ArrayList<PlaceEntity> places = placeService.getPlacesListByUserId(userId);
        UserEntity user = userService.getUserById(userId);

        ArrayList<FullInfoPlaceDTO> fullInfoPlaceDTOS = new ArrayList<>();
        for (PlaceEntity place : places) {
            fullInfoPlaceDTOS.add(mapper.toFullInfoPlaceDTO(place, user));
        }

        return fullInfoPlaceDTOS;
    }
}
