/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.example.spaceship.infrastructure.repository;

import com.example.spaceship.domain.model.Production;
import com.example.spaceship.domain.model.SpaceShip;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 *
 * @author santi
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpaceShipRepositoryPostgresTest {

    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("ships")
            .withUsername("myuser")
            .withPassword("mypassword");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    static {
        postgreSQLContainer.start();
    }

    @Autowired
    SpaceShipRepositoryPostgres repository;

     @SpyBean
     JdbcTemplate jdbcTemplate;
     
    @Test
    public void testFindById() {
        var actual = repository.findById("1");
        assertEquals("USS Enterprise", actual.get().name());
        assertEquals("Starship", actual.get().type());
        assertEquals("Jean-Luc Picard", actual.get().lastCaptain());
        assertEquals(2, actual.get().appearances().size());
        assertEquals("Federation", actual.get().faction());
    }

    @Test
    public void testFindByNameContaining() {
        var actual = repository.findByNameContaining("USS");
        assertEquals(2, actual.size());
        assertEquals("Starship", actual.get(0).type());
        assertEquals("Explorer", actual.get(1).type());
    }

    @Test
    public void testFindAll() {
        var actual = repository.findAll(2, 3);
        assertEquals(3, actual.size());
        assertEquals("Defiant", actual.get(0).name());
        assertEquals("Excelsior", actual.get(1).name());
        assertEquals("Prometheus", actual.get(2).name());
    }

    @Test
    public void testCreateSpaceShip() {
        SpaceShip ship = new SpaceShip("8", "VoLVO", List.of(
                new Production.Movie("Star Trek: The Motion Picture", (short) 1979),
                new Production.Serie("Star Trek: Voyager", (short) 7),
                new Production.Serie("Star Trek: The Next Generation", (short) 7)
        ), "Starship", "Kathryn Janeway", "Federation");

        repository.create(ship);

        Optional<SpaceShip> savedShip = repository.findById("8");
        assertTrue(savedShip.isPresent());
        assertEquals("VoLVO", savedShip.get().name());
        assertEquals(3, savedShip.get().appearances().size());
    }

    @Test
    public void testUpdateSpaceShip() {
        SpaceShip ship = new SpaceShip("1", "USS Voyager Updated", List.of(
                new Production.Movie("Star Trek: The Motion Picture", (short) 1979),
                new Production.Serie("Star Trek: Voyager", (short) 7)
        ), "Starship", "Kathryn Janeway", "Federation");

        repository.update(ship);

        Optional<SpaceShip> updatedShip = repository.findById("1");
        assertTrue(updatedShip.isPresent());
        assertEquals("USS Voyager Updated", updatedShip.get().name());
        assertEquals(2, updatedShip.get().appearances().size());
    }

    @Test
    public void testDeleteSpaceShip() {

        repository.delete("2");

        Optional<SpaceShip> deletedShip = repository.findById("2");
        assertFalse(deletedShip.isPresent());
        List<Integer> productionIds = jdbcTemplate.queryForList("SELECT production_id FROM spaceship_production WHERE spaceship_id = ?", Integer.class, "2");
        assertTrue(productionIds.isEmpty());
    }

}
