package com.journey.server.controller;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.dto.place.FullInfoPlaceDTO;
import com.journey.server.dto.place.UpdateIsVisitedDTO;
import com.journey.server.entity.PlaceEntity;
import com.journey.server.entity.UserEntity;
import com.journey.server.mapper.PlaceMapper;
import com.journey.server.service.PlaceService;
import com.journey.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс, описывающий контроллер, обрабатывающий запросы клиента, связанные с местами
 */
@RestController
@Tag(name = "PLACES")
@RequestMapping("/places")
@SecurityRequirement(name = "bearerAuth")
public class PlaceController {
    /**
     * Сервис, работающий с местами
     */
    private final PlaceService placeService;

    /**
     * Сервис, работающий с пользователями
     */
    private final UserService userService;

    /**
     * Конвертер сущностей, описывающих место
     */
    private final PlaceMapper mapper;

    /**
     * Конструктор контроллера
     * @param placeService сервис, работающий с местами
     * @param userService сервис, работающий с пользователями
     * @param mapper конвертер сущностей, описывающих место
     */
    public PlaceController(PlaceService placeService, UserService userService, PlaceMapper mapper) {
        this.placeService = placeService;
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * Получение всех мест пользователя по его ID (берется из токена) - запрос GET
     * @return список сущностей, описывающих места данного пользователя
     * @throws SQLException  при неуспешном подключении или внутренней ошибке базы данных
     */
    @Operation(summary = "Get place list by userId")
    @GetMapping
    public ArrayList<FullInfoPlaceDTO> getPlaceListOfTheUser() throws SQLException {
        int userId = userService.getAuthInfo().getId();
        String username = userService.getAuthInfo().getUsername();
        ArrayList<PlaceEntity> places = placeService.getPlaceListByUserId(userId);

        ArrayList<FullInfoPlaceDTO> fullInfoPlaceDTOS = new ArrayList<>();
        for (PlaceEntity place : places) {
            fullInfoPlaceDTOS.add(mapper.toFullInfoPlaceDTO(place, username));
        }

        return fullInfoPlaceDTOS;
    }

    /**
     * Получение информации о месте по ее идентификатору - запрос GET
     * @param id идентификатор места, информацию о котором требуется получить
     * @return сущность с информацией о карточке места и статус 200 OK, если место существует,
     * иначе путое тело и статус 404 NOT FOUND
     * @throws Exception при неуспешном подключении или внутренней ошибке базы данных
     */
    @Operation(summary = "Get place by id")
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<FullInfoPlaceDTO> getPlaceById(@PathVariable int id) throws Exception {
        String username = userService.getAuthInfo().getUsername();

        PlaceEntity place = placeService.getPlaceById(id);
        if (place == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (place.getAuthorId() != userService.getAuthInfo().getId())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.status(HttpStatus.OK).body(mapper.toFullInfoPlaceDTO(place, username));
    }

    /**
     * Удаление места по ID - запрос DELETE
     * @param id идентификатор места, информацию о котором требуется удалить
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Operation(summary = "Delete place by id")
    @DeleteMapping("/{id:\\d+}")
    public void deletePlaceById(@PathVariable int id) throws SQLException {
        placeService.deletePlaceById(id);
    }

    /**
     * Создание нового места в БД- запрос POST
     * @param place объект с информацией, которую пользователь ввел при создании карточки места
     * @return новый URI и статус 201 CREATED при успешном добавлении места
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     * @throws URISyntaxException при неуспешном создании URI нового места
     */
    @Operation(summary = "Create place")
    @PostMapping
    public ResponseEntity<String> createPlace(@RequestBody CreatePlaceDTO place) throws URISyntaxException, SQLException {
        int id = placeService.createPlace(mapper.fromCreatePlaceDTO(place, userService.getAuthInfo().getId()));

        URI location = new URI("/places/" + id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);

        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).build();
    }

    /**
     * Обновление нового места в БД - запрос PUT
     * @param id идентификатор места, информацию о котором требуется обновить
     * @param place объект с информацией, которую пользователь ввел при редактировании карточки места
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Operation(summary = "Update place")
    @PutMapping("/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePlace(@PathVariable int id, @RequestBody CreatePlaceDTO place) throws SQLException {
        placeService.updatePlace(id, mapper.fromCreatePlaceDTO(place, userService.getAuthInfo().getId()));
    }

    /**
     * Перевод места в список посещенных или желаемых - запрос PATCH
     * @param id идентификатор места, статус посещенности которого требуется обновить
     * @param updateIsVisitedDTO объект с информацией, которую пользователь ввел при обновлении статуса посещенности места
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Operation(summary = "Update isVisited field")
    @PatchMapping("/{id:\\d+}")
    public void updateIsVisited(@PathVariable int id, @RequestBody UpdateIsVisitedDTO updateIsVisitedDTO)
            throws SQLException {
        placeService.updateIsVisited(id, mapper.fromUpdateIsVisitedDTO(updateIsVisitedDTO));
    }
}
