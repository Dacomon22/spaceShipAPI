package com.example.spaceship.infrastructure.repository;

import com.example.spaceship.domain.model.Production;
import com.examples.spaceship.domain.repository.ProductionRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 *
 * @author santi
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductionRepositoryPostgresTest {

    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("ships")
            .withUsername("myuser")
            .withPassword("mypassword");

   
    static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.redis.host", redisContainer::getHost);
        registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    static {
        postgreSQLContainer.start();
        redisContainer.start();
    }

    @AfterAll
    public static void tearDownClass() {
        postgreSQLContainer.stop();
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
    public void testFindAllProcutions() {

        var actual = repository.findAllProcutions();
        assertEquals(2, actual.size());
        assertTrue(actual.stream().anyMatch(prod -> switch (prod) {
            case Production.Serie serie ->
                serie.name().equals("Star Trek: The Next Generation");
            case Production.Movie movie ->
                false;
        }
        ));
        
        var cached = repository.findAllProcutions();
        assertEquals(2, cached.size());
        verify(jdbcTemplate, Mockito.times(1)).query(Mockito.anyString(), Mockito.any(ProductionRepositoryPostgres.ProductionRowMapper.class));
    }

}
