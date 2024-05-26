package com.example.spaceship.domain.model;

/**
 *
 * @author santi
 */
public sealed interface Production {
    
    public record Movie(String name, short launchedYear)implements Production{
        
    }
    
    public record Serie(String name, short seasons) implements Production{
        
    }
}
