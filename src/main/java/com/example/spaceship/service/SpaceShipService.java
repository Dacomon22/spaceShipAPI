package com.example.spaceship.service;

import com.example.spaceship.domain.model.SpaceShip;
import com.examples.spaceship.domain.repository.SpaceShipRepository;
import java.util.List;
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

    public List<SpaceShip> getAllSpaceShip(int offset, int limit) {
        return repository.findAll(offset, limit);
    }
    
    public Optional<SpaceShip> getSpaceShipById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El parametro es invalido");
        }
        
        return repository.findById(id);
    }
    
    public List<SpaceShip> getSpaceShipByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("El parametro es invalido");
        }
        return repository.findByNameContaining(name);
    }
    
    public void saveSpaceShip(SpaceShip ship) {
        repository.create(ship);
    }
    
    public void updateSpaceShip(SpaceShip ship) {
        repository.update(ship);
    }
    
    public void deleteSpaceShipById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El parametro es invalido");
        }
        repository.delete(id);
    }
}
