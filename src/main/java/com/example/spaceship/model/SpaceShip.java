package com.example.spaceship.model;

/**
 *
 * @author santi
 */
public record SpaceShip(String id, String name, String appearance) {

    public SpaceShip   {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be null or empty");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        if (appearance == null || appearance.isEmpty()) {
            throw new IllegalArgumentException("appearance cannot be null or empty");
        }
    }
}
