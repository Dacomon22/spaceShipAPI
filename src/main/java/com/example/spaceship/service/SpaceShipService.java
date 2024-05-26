
package com.example.spaceship.service;

import com.example.spaceship.domain.model.SpaceShip;
import com.examples.spaceship.domain.repository.SpaceShipRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author santi
 */
@Service
public class SpaceShipService {
    
    private final SpaceShipRepository repository;
    
    public SpaceShipService(SpaceShipRepository repository) {
        this.repository = repository;
    }
    
    public Optional<SpaceShip> getSpaceShipById(String id){
        if (id == null || id.isEmpty()) 
            throw new IllegalArgumentException("El parametro es invalido");
        
        return Optional.empty();
    }
    
    public String getSpaceShipByName(String name){
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("El parametro es invalido");
        }
    return null;
    }
    
    public String saveSpaceShip(SpaceShip ship){
    return null;}
    
    
    public void deleteSpaceShipById(String id){
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El parametro es invalido");
        }
    }
}
