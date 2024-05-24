package com.example.spaceship.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author santi
 */
public class SpaceShipServiceTest {

    SpaceShipService service;

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
