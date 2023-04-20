package com.journey.server.controller;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.dto.place.FullInfoPlaceDTO;
import com.journey.server.entity.PlaceEntity;
import com.journey.server.entity.UserEntity;
import com.journey.server.mapper.PlaceMapper;
import com.journey.server.service.PlaceService;
import com.journey.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
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

    @Operation(summary = "Get place list by userId")
    @GetMapping
    public ArrayList<FullInfoPlaceDTO> getPlaceListByUserId(@RequestParam int userId) throws SQLException {
        ArrayList<PlaceEntity> places = placeService.getPlaceListByUserId(userId);
        UserEntity user = userService.getUserById(userId);

        ArrayList<FullInfoPlaceDTO> fullInfoPlaceDTOS = new ArrayList<>();
        for (PlaceEntity place : places) {
            fullInfoPlaceDTOS.add(mapper.toFullInfoPlaceDTO(place, user));
        }

        return fullInfoPlaceDTOS;
    }

    @Operation(summary = "Get place by id")
    @GetMapping("/{id:\\d+}")
    public FullInfoPlaceDTO getPlaceById(@PathVariable int id) throws Exception {
        PlaceEntity place = placeService.getPlaceById(id);
        UserEntity user = userService.getUserById(place.getAuthorId());

        return mapper.toFullInfoPlaceDTO(place, user);
    }

    @Operation(summary = "Delete place by id")
    @DeleteMapping("/{id:\\d+}")
    public void deletePlaceById(@PathVariable int id) throws SQLException {
        placeService.deletePlaceById(id);
    }

    @Operation(summary = "Create place")
    @PostMapping
    public ResponseEntity<String> createPlace(CreatePlaceDTO place) throws URISyntaxException, SQLException {
        int id = placeService.createPlace(mapper.fromCreatePlaceDTO(place));

        URI location = new URI("/places/" + id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);

        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Update place")
    @PutMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePlace(@PathVariable int id, @RequestBody CreatePlaceDTO place) {
        placeService.updatePlace(id, mapper.fromCreatePlaceDTO(place));
    }

    @Operation(summary = "Update isVisited field")
    @PatchMapping("/{id:\\d+}")
    public void updateIsVisited(@PathVariable int id, @RequestBody boolean isVisited) throws SQLException {
        placeService.updateIsVisited(id, isVisited);
    }
}
