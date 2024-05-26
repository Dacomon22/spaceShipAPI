
package com.examples.spaceship.domain.repository;

import com.example.spaceship.domain.model.SpaceShip;
import java.util.List;
import java.util.Optional;


/**
 *
 * @author santi
 */
public interface SpaceShipRepository{
    
    List<SpaceShip> findAll(int offset, int limit);
    
    Optional<SpaceShip> findById(String id);
    
    List<SpaceShip> findByNameContaining(String name);
    
    void create(SpaceShip ship);
    
    void update(SpaceShip ship);
    
    void delete(String id);
    
}
