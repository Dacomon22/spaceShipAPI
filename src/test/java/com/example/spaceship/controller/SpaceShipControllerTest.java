
package com.example.spaceship.controller;

import com.example.spaceship.domain.model.SpaceShip;
import com.example.spaceship.service.SpaceShipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author santi
 */
@WebMvcTest(SpaceShipController.class)
public class SpaceShipControllerTest {@Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceShipService spaceShipService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        SpaceShip ship = new SpaceShip("1", "USS Enterprise", List.of(), "Starship", "Jean-Luc Picard", "Federation");
        Mockito.when(spaceShipService.getSpaceShipById("1")).thenReturn(Optional.of(ship));
        Mockito.when(spaceShipService.getAllSpaceShip(0, 10)).thenReturn(List.of(ship));
    }

    @Test
    public void testGetAllSpaceShips() throws Exception {
        mockMvc.perform(get("/api/spaceships")
                .param("offset", "0")
                .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("USS Enterprise"));
    }

    @Test
    public void testGetSpaceShipById() throws Exception {
        mockMvc.perform(get("/api/spaceships/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("USS Enterprise"));
    }

    @Test
    public void testGetSpaceShipByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/spaceships/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetSpaceShipByName() throws Exception {
        Mockito.when(spaceShipService.getSpaceShipByName("Enterprise")).thenReturn(List.of(new SpaceShip("1", "USS Enterprise", List.of(), "Starship", "Jean-Luc Picard", "Federation")));
        mockMvc.perform(get("/api/spaceships/search")
                .param("name", "Enterprise"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("USS Enterprise"));
    }

    @Test
    public void testSaveSpaceShip() throws Exception {
        SpaceShip ship = new SpaceShip("2", "USS Voyager", List.of(), "Starship", "Kathryn Janeway", "Federation");
        mockMvc.perform(post("/api/spaceships")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ship)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateSpaceShip() throws Exception {
        SpaceShip ship = new SpaceShip("2", "USS Voyager Updated", List.of(), "Starship", "Kathryn Janeway", "Federation");
        mockMvc.perform(put("/api/spaceships/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ship)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSpaceShipById() throws Exception {
        mockMvc.perform(delete("/api/spaceships/1"))
                .andExpect(status().isNoContent());
    }
}