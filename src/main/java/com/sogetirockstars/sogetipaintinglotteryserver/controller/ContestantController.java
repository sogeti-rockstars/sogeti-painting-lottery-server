package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import java.util.List;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.service.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ContestantController
 */
@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/v1/contestant")
public class ContestantController {
    private final ContestantService service;

    @Autowired
    public ContestantController(ContestantService service) {
        this.service = service;
    }

    /**
     * Returns all lottery items
     */
    @GetMapping
    public List<Contestant> getAll() {
        return service.getAll();
    }

    /**
     * Get item with id /{id}
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            System.out.println("Sending painting with id " + id);
            Contestant cont = service.get(id);
            return new ResponseEntity<>(cont, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete item with id /{id}
     */
    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Add new item
     */
    @PostMapping
    public ResponseEntity<Contestant> addNew(@RequestBody Contestant cont) {
        cont.setId(null);
        return ResponseEntity.ok().body(service.add(cont));
    }

    /**
     * Update item with id /{id}
     */
    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Contestant cont) {
        try {
            cont.setId(id);
            return new ResponseEntity<>(service.update(cont), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
