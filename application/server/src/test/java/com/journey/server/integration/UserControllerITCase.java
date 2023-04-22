package com.journey.server.integration;

import com.journey.server.controller.UserController;
import com.journey.server.dto.user.RegistryUserDTO;
import com.journey.server.jwt.JwtRequestDTO;
import com.journey.server.jwt.JwtResponseDTO;
import com.journey.server.utils.PropertiesUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static com.journey.server.utils.UserObjectMother.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerITCase {
    @Autowired
    private UserController controller;

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
    public void registerITCase() throws URISyntaxException {
        // arrange
        RegistryUserDTO dto = getNewUser();

        // act
        ResponseEntity<String> response = controller.register(dto);

        // assert
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void registerConflictITCase() throws URISyntaxException {
        // arrange
        RegistryUserDTO dto = getNewUserWithConfilctLogin();

        // act
        ResponseEntity<String> response = controller.register(dto);

        // assert
        assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void authITCase() throws SQLException {
        // arrange
        JwtRequestDTO dto = getCorrectJwtRequest();

        // act
        ResponseEntity<JwtResponseDTO> response = controller.auth(dto);

        // assert
        assertNotNull(response.getBody().getAccessToken());
        assertNotNull(response.getBody().getRefreshToken());
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void authWrongCredentialsITCase() throws SQLException {
        // arrange
        JwtRequestDTO dto = getIncorrectJwtRequest();

        // act
        ResponseEntity<JwtResponseDTO> response = controller.auth(dto);

        // assert
        assertNull(response.getBody().getAccessToken());
        assertNull(response.getBody().getRefreshToken());
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
}
