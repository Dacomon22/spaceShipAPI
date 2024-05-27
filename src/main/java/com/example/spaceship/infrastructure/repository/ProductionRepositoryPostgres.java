package com.example.spaceship.infrastructure.repository;

import com.example.spaceship.domain.model.Production;
import com.examples.spaceship.domain.repository.ProductionRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author santi
 */
@Repository
public class ProductionRepositoryPostgres implements ProductionRepository {
    
    @Autowired
    JdbcTemplate template;
    
    static final String SQL_FIND_ALL_PRODUCTIONS = "SELECT id, type, name, launched_year, seasons FROM production";

    static final String SQL_FIND_PRODUCTIONS_BY_SPACECHIP_ID = "SELECT id, type, name, launched_year, seasons FROM production p INNER JOIN spaceship_production sp ON p.id = sp.production_id WHERE sp.spaceship_id = ?";

    @Override
    @Cacheable(value ="productions")
    public List<Production> findAllProcutions() {
        return template.query(SQL_FIND_ALL_PRODUCTIONS, new ProductionRowMapper());
    }

    @Override
    public List<Production> findProductionBySpaceShipId(String id) {
        
        return template.query(SQL_FIND_PRODUCTIONS_BY_SPACECHIP_ID, new ProductionRowMapper(),id);
    }

    @Override
    public void saveProductions(List<Production> productions, String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    static class ProductionRowMapper implements RowMapper<Production> {
        
        
        
        @Override
        public Production mapRow(ResultSet rs, int rowNum) throws SQLException {
            String type = rs.getString("type");
            String name = rs.getString("name");
            if ("Movie".equalsIgnoreCase(type)) {
                short launchedYear = rs.getShort("launched_year");
                return new Production.Movie(name, launchedYear);
            } else if ("Serie".equalsIgnoreCase(type)) {
                short seasons = rs.getShort("seasons");
                return new Production.Serie(name, seasons);
            }
            throw new SQLException("Unknown production type: " + type);
        }
    }
    
    
}
