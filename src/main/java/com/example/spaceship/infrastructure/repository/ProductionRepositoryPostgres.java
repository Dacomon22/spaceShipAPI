package com.example.spaceship.infrastructure.repository;

import com.example.spaceship.domain.model.Production;
import com.examples.spaceship.domain.repository.ProductionRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    static final String SQL_INSERT_PRODUCTION = "INSERT INTO production (type, name, launched_year, seasons) VALUES (?, ?, ?, ?) RETURNING id";
    static final String SQL_INSERT_SPACESHIP_PRODUCTION = "INSERT INTO spaceship_production (spaceship_id, production_id) VALUES (?, ?)";
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
    public void saveProductions(List<Production> productions, String spaceShipId) {
        
        for (Production production : productions) {
       
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PRODUCTION, Statement.RETURN_GENERATED_KEYS);
                if (production instanceof Production.Movie movie) {
                    ps.setString(1, "Movie");
                    ps.setString(2, movie.name());
                    ps.setShort(3, movie.launchedYear());
                    ps.setNull(4, java.sql.Types.SMALLINT);
                } else if (production instanceof Production.Serie serie) {
                    ps.setString(1, "Serie");
                    ps.setString(2, serie.name());
                    ps.setNull(3, java.sql.Types.SMALLINT);
                    ps.setShort(4, serie.seasons());
                }
                return ps;
            }, keyHolder);

            int productionId = Objects.requireNonNull(keyHolder.getKey()).intValue();
            template.update(SQL_INSERT_SPACESHIP_PRODUCTION, spaceShipId, productionId);
       }
        
    
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
