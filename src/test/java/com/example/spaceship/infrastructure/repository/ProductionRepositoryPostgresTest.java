package com.example.spaceship.infrastructure.repository;

import com.example.spaceship.domain.model.Production;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductionRepositoryPostgresTest {

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

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Autowired
    ProductionRepositoryPostgres repository;

    @SpyBean
    JdbcTemplate jdbcTemplate;

    /**
     * Test of findProcutions method, of class ProductionRepositoryPostgres.
     */
    @Test
    @Order(1)
    public void testFindAllProcutions() {

        var actual = repository.findAllProcutions();
        assertEquals(4, actual.size());
        assertTrue(actual.stream().anyMatch(prod -> switch (prod) {
            case Production.Serie serie ->
                serie.name().equals("Star Trek: The Next Generation");
            case Production.Movie movie ->
                false;
        }
        ));

        var cached = repository.findAllProcutions();
        assertEquals(4, cached.size());
        verify(jdbcTemplate, Mockito.times(1)).query(Mockito.anyString(), Mockito.any(ProductionRepositoryPostgres.ProductionRowMapper.class));
    }

    @Test
    @Order(2)
    public void testFindProductionsByIdStarShip() {

        var actual = repository.findProductionBySpaceShipId("2");
        assertEquals(3, actual.size());
        assertTrue(actual.stream().anyMatch(prod -> switch (prod) {
            case Production.Serie serie ->
                serie.name().equals("Star Trek: The Next Generation");
            case Production.Movie movie ->
                false;
        }
        ));
    }

    @Test
    @Order(3)
    public void testSaveProductions() {
        List<Production> productions = List.of(
                new Production.Movie("Star Trek: The Motion Close", (short) 1979),
                new Production.Serie("Star Trek: The Next Motion", (short) 7),
                new Production.Serie("Star Trek: The Next Advanced", (short) 9)
        );

        repository.saveProductions(productions, "2");

        var actual = repository.findProductionBySpaceShipId("2");
        assertEquals(6, actual.size());
        assertTrue(actual.stream().anyMatch(prod -> switch (prod) {
            case Production.Serie serie ->
                serie.name().equals("Star Trek: The Next Motion");
            case Production.Movie movie ->
                false;
        }
        ));
    }

}
