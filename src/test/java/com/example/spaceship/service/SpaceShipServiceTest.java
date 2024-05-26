package com.example.spaceship.service;

import com.example.spaceship.SpaceShipApiApplication;
import com.example.spaceship.entity.SpaceShipEntity;
import com.examples.spaceship.repository.SpaceShipRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author santi
 */
@SpringBootTest(classes = SpaceShipApiApplication.class)
public class SpaceShipServiceTest {

    SpaceShipService service;

    @Mock
    SpaceShipRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new SpaceShipService(repository);
    }

    @Test
    public void getSpaceShipByIdTest_nullParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getSpaceShipByID(null);
        });
    }

    @Test
    public void getSpaceShipByIdTest_emptyParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getSpaceShipByID("");
        });
    }

    @Test
    public void getSpaceShipByIdTest_foundShip(){
        SpaceShipEntity entity = new SpaceShipEntity("1", "Apollo", "Sleek");
        when(repository.findById("1")).thenReturn(Optional.of(entity));

        }

    @Test
    public void getSpaceShipByNameTest_nullParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getSpaceShipByName(null);
        });
    }

    @Test
    public void getSpaceShipByNameTest_emptyParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getSpaceShipByName("");
        });
    }

    @Test
    public void deleteSpaceShipById_nullParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteSpaceShipById(null);
        });
    }

    @Test
    public void deleteSpaceShipById_emptyParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteSpaceShipById("");
        });
    }

}
