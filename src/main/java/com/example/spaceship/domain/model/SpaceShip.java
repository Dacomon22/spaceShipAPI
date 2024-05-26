package com.example.spaceship.domain.model;

import java.util.List;

/**
 *
 * @author santi
 */
public record SpaceShip(String id, String name, List<Production> appearances,String type,String lastCaptain,String faction) {
    
}
