/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.spaceship.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;


/**
 *
 * @author santi
 */
public class SpaceShipTest {
    
     @Test
    public void shouldThrowExceptionWhenIdIsNull() {
        assertThatThrownBy(() -> new SpaceShip(null, "name", "appearance"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("id cannot be null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenNameIsNull() {
        assertThatThrownBy(() -> new SpaceShip("id", null, "appearance"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("name cannot be null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenAppearanceIsNull() {
        assertThatThrownBy(() -> new SpaceShip("id", "name", null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("appearance cannot be null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenIdIsEmpty() {
        assertThatThrownBy(() -> new SpaceShip("", "name", "appearance"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("id cannot be null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenNameIsEmpty() {
        assertThatThrownBy(() -> new SpaceShip("id", "", "appearance"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("name cannot be null or empty");
    }

    @Test
    public void shouldThrowExceptionWhenAppearanceIsEmpty() {
        assertThatThrownBy(() -> new SpaceShip("id", "name", ""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("appearance cannot be null or empty");
    }
}
   
