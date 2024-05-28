package com.example.spaceship.domain.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 *
 * @author santi
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Production.Movie.class, name = "movie"),
        @JsonSubTypes.Type(value = Production.Serie.class, name = "serie")
})
public sealed interface Production {
    
    public record Movie(String name, short launchedYear)implements Production{
        
    }
    
    public record Serie(String name, short seasons) implements Production{
        
    }
}
