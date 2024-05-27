
package com.example.spaceship.controller;

import com.example.spaceship.domain.model.SpaceShip;
import com.example.spaceship.service.SpaceShipService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author santi
 */

@RestController
@RequestMapping("/api/spaceships")
public class SpaceShipController {
    
    

    @Autowired
    private SpaceShipService spaceShipService;

    @GetMapping
    public ResponseEntity<List<SpaceShip>> getAllSpaceShips(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
        List<SpaceShip> spaceShips = spaceShipService.getAllSpaceShip(offset, limit);
        return new ResponseEntity<>(spaceShips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceShip> getSpaceShipById(@PathVariable String id) {
        Optional<SpaceShip> spaceShip = spaceShipService.getSpaceShipById(id);
        return spaceShip.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SpaceShip>> getSpaceShipByName(@RequestParam String name) {
        List<SpaceShip> spaceShips = spaceShipService.getSpaceShipByName(name);
        return new ResponseEntity<>(spaceShips, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> saveSpaceShip(@RequestBody SpaceShip spaceShip) {
        spaceShipService.saveSpaceShip(spaceShip);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSpaceShip(@PathVariable String id, @RequestBody SpaceShip spaceShip) {
        spaceShipService.updateSpaceShip(spaceShip);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceShipById(@PathVariable String id) {
        spaceShipService.deleteSpaceShipById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
