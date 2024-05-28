package com.example.spaceship.service;

import com.example.spaceship.SpaceShipApiApplication;
import com.example.spaceship.domain.model.SpaceShip;
import com.examples.spaceship.domain.repository.SpaceShipRepository;
import java.util.List;
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
    public void getAllSpaceShip_Found(){
    when(repository.findAll(0, 0)).thenReturn(List.of(new SpaceShip("1", "USS", List.of(), "Explorer", "Rambo", "Alliance"),new SpaceShip("2", "USS EX", List.of(), "Scout", "Chris", "Horde")));
    var actual = service.getAllSpaceShip(0,0);
        assertFalse(actual.isEmpty());
        assertEquals("USS", actual.get(0).name());
        assertEquals("Horde", actual.get(1).faction());
    }
    
   
    
    @Test
    public void getSpaceShipByIdTest_nullParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getSpaceShipById(null);
        });
    }

    @Test
    public void getSpaceShipByIdTest_emptyParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.getSpaceShipById("");
        });
    }
    
    @Test
    public void getSpaceShipByIdTest_notFoundId() {
        when(repository.findById("notFound")).thenReturn(Optional.empty());
        var actual = service.getSpaceShipById("notFound");
        assertTrue(actual.isEmpty());
    }
    
    @Test
    public void getSpaceShipByIdTest_foundId() {
        when(repository.findById("found")).thenReturn(Optional.of(new SpaceShip("found", "Found", List.of(), "Warship", "Last", "Alliance")));
        var actual = service.getSpaceShipById("found");
        assertTrue(actual.isPresent());
        assertEquals("Found", actual.get().name());
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
    public void getSpaceShipByNameTest_notNameFound() {
        when(repository.findByNameContaining("notFound")).thenReturn(List.of());
        var actual = service.getSpaceShipByName("notFound");
        assertTrue(actual.isEmpty());
    }
    
    @Test
    public void getSpaceShipByNameTest_foundName() {
        when(repository.findByNameContaining("USS")).thenReturn(List.of(new SpaceShip("1", "USS", List.of(), "Explorer", "Rambo", "Alliance"),new SpaceShip("2", "USS EX", List.of(), "Scout", "Chris", "Horde")));
        var actual = service.getSpaceShipByName("USS");
        assertFalse(actual.isEmpty());
        assertEquals("USS", actual.get(0).name());
        assertEquals("Scout", actual.get(1).type());
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
