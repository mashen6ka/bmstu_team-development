package com.journey.server.integration;

import com.journey.server.controller.PlaceController;
import com.journey.server.dto.place.CreatePlaceDTO;
import com.journey.server.dto.place.FullInfoPlaceDTO;
import com.journey.server.dto.place.UpdateIsVisitedDTO;
import com.journey.server.utils.PropertiesUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.journey.server.utils.Assertions.assertDTOEquals;
import static com.journey.server.utils.PlaceObjectMother.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceControllerITCase {
    @Autowired
    private PlaceController controller;

    @Before
    public void setUp() throws IOException, InterruptedException {
        String[] cmd = {"/bin/sh", "-c", "cd " + System.getProperty("user.dir")
                        + "/../sql && ./prepare_data.sh " + PropertiesUtil.get("db.password") + " && cd ../server"};
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
    }

    @After
    public void tearDown() throws IOException, InterruptedException {
        String[] cmd = {"/bin/sh", "-c", "cd " + System.getProperty("user.dir")
                        + "/../sql && ./rollback_data.sh " + PropertiesUtil.get("db.password") + " && cd ../server"};
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
    }

    @Test
    public void getPlaceListByUserLengthITCase() throws SQLException {
        // act
        ArrayList<FullInfoPlaceDTO> res = controller.getPlaceListByUserId(4);

        // assert
        assertEquals(res.size(), 3);
    }

    @Test
    public void getPlaceListByUserRightUserITCase() throws SQLException {
        // act
        ArrayList<FullInfoPlaceDTO> res = controller.getPlaceListByUserId(4);

        // assert
        for (FullInfoPlaceDTO dto : res)
            assertEquals(dto.getAuthorName(), "Николай Трошкин");
    }

    @Test
    public void getPlaceListByUserNoSuchUserITCase() throws SQLException {
        // act
        ArrayList<FullInfoPlaceDTO> res = controller.getPlaceListByUserId(100);

        // assert
        assertEquals(res.size(), 0);
    }

    @Test
    public void getPlaceByIdITCase() throws Exception {
        // arrange
        FullInfoPlaceDTO outDTO = getSomeExistingOutputPlace();

        // act
        FullInfoPlaceDTO dto = controller.getPlaceById(12).getBody();

        // assert
        assertNotNull(dto);
        assertDTOEquals(dto, outDTO);
    }

    @Test
    public void getPlaceByIdNoSuchPlaceITCase() throws Exception {
        // act
        HttpStatusCode httpStatusCode = controller.getPlaceById(100).getStatusCode();

        // assert
        assertEquals(HttpStatus.NOT_FOUND, httpStatusCode);
    }

    @Test
    public void deletePlaceByIdITCase() throws Exception {
        // act
        controller.deletePlaceById(10);
        HttpStatusCode httpStatusCode = controller.getPlaceById(10).getStatusCode();

        // assert
        assertEquals(HttpStatus.NOT_FOUND, httpStatusCode);
    }

    @Test
    public void deletePlaceByIdNoSuchPlaceITCase() throws Exception {
        // act
        controller.deletePlaceById(100);
    }

    @Test
    public void createPlaceITCase() throws Exception {
        // arrange
        CreatePlaceDTO inDTO = getSomeNewInputPlace();
        FullInfoPlaceDTO outDTO = getSomeNewOutputPlace();

        // act
        ResponseEntity<String> response = controller.createPlace(inDTO);

        // assert
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        // act
        FullInfoPlaceDTO dto = controller.getPlaceById(31).getBody();

        // assert
        assertNotNull(dto);
        assertDTOEquals(dto, outDTO);
    }

    @Test
    public void updatePlaceByIdITCase() throws Exception {
        // arrange
        FullInfoPlaceDTO initDTO = getSomeExistingOutputPlace();
        CreatePlaceDTO inDTO = getSomeNewInputPlace();
        FullInfoPlaceDTO outDTO = getSomeNewOutputPlace();

        // act
        FullInfoPlaceDTO dto = controller.getPlaceById(12).getBody();

        // assert
        assertNotNull(dto);
        assertDTOEquals(dto, initDTO);

        // act
        controller.updatePlace(12, inDTO);
        dto = controller.getPlaceById(12).getBody();

        // assert
        assertNotNull(dto);
        assertDTOEquals(dto, outDTO);
    }

    @Test
    public void updatePlaceByIdNoSuchPlaceITCase() throws Exception {
        // arrange
        CreatePlaceDTO inDTO = getSomeNewInputPlace();

        // act
        controller.updatePlace(100, inDTO);
        HttpStatusCode httpStatusCode = controller.getPlaceById(100).getStatusCode();

        // assert
        assertEquals(HttpStatus.NOT_FOUND, httpStatusCode);
    }

    // @Test
    // public void updateIsVisitedITCase() throws Exception {
    //     // arrange
    //     FullInfoPlaceDTO initDTO = getSomeExistingOutputPlace();
    //     FullInfoPlaceDTO outDTO = getSomeExistingOutputPlace();
    //     outDTO.setVisited(true);

    //     // act
    //     FullInfoPlaceDTO dto = controller.getPlaceById(12).getBody();

    //     // assert
    //     assertNotNull(dto);
    //     assertDTOEquals(dto, initDTO);

    //     // act
    //     controller.updateIsVisited(12, UpdateIsVisitedDTO.builder().isVisited(true).dttmUpdate(1682345338).build());
    //     dto = controller.getPlaceById(12).getBody();

    //     // assert
    //     assertNotNull(dto);
    //     assertDTOEquals(dto, outDTO);
    // }

    @Test
    public void updateIsVisitedNoSuchPlaceITCase() throws Exception {
        // act
        controller.updateIsVisited(100, UpdateIsVisitedDTO.builder().isVisited(true).dttmUpdate(1682345338).build());
        HttpStatusCode httpStatusCode = controller.getPlaceById(100).getStatusCode();

        // assert
        assertEquals(HttpStatus.NOT_FOUND, httpStatusCode);
    }
}
