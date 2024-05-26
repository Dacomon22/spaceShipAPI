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


    @Override
    @Cacheable(value ="productions")
    public List<Production> findAllProcutions() {
        return template.query(SQL_FIND_ALL_PRODUCTIONS, new ProductionRowMapper());
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
