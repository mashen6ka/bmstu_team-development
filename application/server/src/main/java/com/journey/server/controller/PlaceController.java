package com.journey.server.controller;

import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.dto.place.FullInfoPlaceDTO;
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
     * Получение всех мест пользователя по его ID - запрос GET
     * @param userId идентификатор пользователя, места которого требуется получить
     * @return список сущностей, описывающих места данного пользователя
     * @throws SQLException  при неуспешном подключении или внутренней ошибке базы данных
     */
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

    /**
     * Получение информации о месте по ее идентификатору - запрос GET
     * @param id идентификатор места, информацию о котором требуется получить
     * @return объект FullInfoPlaceDTO с информацией о заданном месте
     * @throws Exception при неуспешном подключении или внутренней ошибке базы данных
     */
    @Operation(summary = "Get place by id")
    @GetMapping("/{id:\\d+}")
    public FullInfoPlaceDTO getPlaceById(@PathVariable int id) throws Exception {
        PlaceEntity place = placeService.getPlaceById(id);
        UserEntity user = userService.getUserById(place.getAuthorId());

        return mapper.toFullInfoPlaceDTO(place, user);
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
    public ResponseEntity<String> createPlace(CreatePlaceDTO place) throws URISyntaxException, SQLException {
        int id = placeService.createPlace(mapper.fromCreatePlaceDTO(place));

        URI location = new URI("/places/" + id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);

        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
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
        placeService.updatePlace(id, mapper.fromCreatePlaceDTO(place));
    }

    /**
     * Перевод места в список посещенных или желаемых - запрос PATCH
     * @param id идентификатор места, статус посещенности которого требуется обновить
     * @param isVisited флаг, помечаем место как посещенное (true) или желаемое (false)
     * @throws SQLException при неуспешном подключении или внутренней ошибке базы данных
     */
    @Operation(summary = "Update isVisited field")
    @PatchMapping("/{id:\\d+}")
    public void updateIsVisited(@PathVariable int id, @RequestBody boolean isVisited) throws SQLException {
        placeService.updateIsVisited(id, isVisited);
    }
}
