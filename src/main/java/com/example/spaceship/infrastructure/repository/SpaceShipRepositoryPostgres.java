package com.example.spaceship.infrastructure.repository;

import com.example.spaceship.domain.model.Production;
import com.example.spaceship.domain.model.SpaceShip;
import com.examples.spaceship.domain.repository.SpaceShipRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author santi
 */
@Repository
public class SpaceShipRepositoryPostgres implements SpaceShipRepository {

    @Autowired
    JdbcTemplate template;
    
    @Autowired
    ProductionRepositoryPostgres productionRepository;

    static final String SQL_FIND_ALL_SPACESHIPS = "SELECT id, name, type, last_captain, faction FROM spaceship LIMIT ? OFFSET ?";
    
    static final String SQL_FIND_SPACESHIP_BY_ID = "SELECT id, name, type, last_captain, faction FROM spaceship WHERE id = ?";
    
     static final String SQL_FIND_SPACESHIP_BY_NAME_CONTAINING = "SELECT id, name, type, last_captain, faction FROM spaceship WHERE name LIKE ?";

     
    @Override
    public List<SpaceShip> findAll(int offset, int limit) {
        return template.query(SQL_FIND_ALL_SPACESHIPS, new SpaceShipRowMapper(productionRepository),limit,offset);
    }

    @Override
    public Optional<SpaceShip> findById(String id) {
         List<SpaceShip> spaceships = template.query(SQL_FIND_SPACESHIP_BY_ID, new SpaceShipRowMapper(productionRepository), id);
        return spaceships.stream().findFirst();
    }

    @Override
    public List<SpaceShip> findByNameContaining(String name) {
        String searchPattern = "%" + name + "%";
        return template.query(SQL_FIND_SPACESHIP_BY_NAME_CONTAINING, new SpaceShipRowMapper(productionRepository),searchPattern);
    }

    @Override
    public void create(SpaceShip ship) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(SpaceShip ship) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
  private static class SpaceShipRowMapper implements RowMapper<SpaceShip> {

         private final ProductionRepositoryPostgres productionRepositoryPostgres;

        public SpaceShipRowMapper(ProductionRepositoryPostgres productionRepositoryPostgres) {
            this.productionRepositoryPostgres = productionRepositoryPostgres;
        }


        @Override
        public SpaceShip mapRow(ResultSet rs, int rowNum) throws SQLException {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String type = rs.getString("type");
            String lastCaptain = rs.getString("last_captain");
            String faction = rs.getString("faction");
            List<Production> appearances = productionRepositoryPostgres.findProductionBySpaceShipId(id);
            return new SpaceShip(id, name, appearances, type, lastCaptain, faction);
        }
    }
}
