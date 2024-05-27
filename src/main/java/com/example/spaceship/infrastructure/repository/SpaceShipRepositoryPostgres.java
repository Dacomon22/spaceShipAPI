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

    private static final String SQL_INSERT_SPACESHIP = "INSERT INTO spaceship (id, name, type, last_captain, faction) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_SPACESHIP = "UPDATE spaceship SET name = ?, type = ?, last_captain = ?, faction = ? WHERE id = ?";

    private static final String SQL_DELETE_SPACESHIP = "DELETE FROM spaceship WHERE id = ?";
    
    private static final String SQL_DELETE_SPACESHIP_PRODUCTION = "DELETE FROM spaceship_production WHERE spaceship_id = ?";
    
    @Override
    public List<SpaceShip> findAll(int offset, int limit) {
        return template.query(SQL_FIND_ALL_SPACESHIPS, new SpaceShipRowMapper(productionRepository), limit, offset);
    }

    @Override
    public Optional<SpaceShip> findById(String id) {
        List<SpaceShip> spaceships = template.query(SQL_FIND_SPACESHIP_BY_ID, new SpaceShipRowMapper(productionRepository), id);
        return spaceships.stream().findFirst();
    }

    @Override
    public List<SpaceShip> findByNameContaining(String name) {
        String searchPattern = "%" + name + "%";
        return template.query(SQL_FIND_SPACESHIP_BY_NAME_CONTAINING, new SpaceShipRowMapper(productionRepository), searchPattern);
    }

    @Override
    public void create(SpaceShip ship) {
        template.update(SQL_INSERT_SPACESHIP, ship.id(), ship.name(), ship.type(), ship.lastCaptain(), ship.faction());
        productionRepository.saveProductions(ship.appearances(), ship.id());
    }

    @Override
    public void update(SpaceShip ship) {
        template.update(SQL_UPDATE_SPACESHIP, ship.name(), ship.type(), ship.lastCaptain(), ship.faction(), ship.id());
        template.update("DELETE FROM spaceship_production WHERE spaceship_id = ?", ship.id());
        productionRepository.saveProductions(ship.appearances(),ship.id());
    }

    @Override
    public void delete(String id) {
        
        template.update(SQL_DELETE_SPACESHIP_PRODUCTION, id);
    
        template.update(SQL_DELETE_SPACESHIP, id);
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
