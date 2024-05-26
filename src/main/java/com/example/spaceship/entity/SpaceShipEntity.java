package com.example.spaceship.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author santi
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SpaceShipEntity {

    @Id
    private String id;
    private String name;
    private String appearance;

}
