package com.examples.spaceship.domain.repository;

import com.example.spaceship.domain.model.Production;
import java.util.List;

/**
 *
 * @author santi
 */
public interface ProductionRepository {
    
    List<Production> findAllProcutions();
    
    List<Production> findProductionBySpaceShipId(String id);
    
    void saveProductions(List<Production> productions,String id);
    
}
