
package com.examples.spaceship.repository;

import com.example.spaceship.entity.SpaceShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author santi
 */
public interface SpaceShipRepository extends JpaRepository<SpaceShipEntity, String>{
    
}
