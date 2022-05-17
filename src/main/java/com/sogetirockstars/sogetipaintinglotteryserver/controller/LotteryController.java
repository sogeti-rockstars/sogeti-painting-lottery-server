package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.service.LotteryService;
import com.sogetirockstars.sogetipaintinglotteryserver.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Component
@RestController
@RequestMapping("api/v1/lottery")
public class LotteryController {
    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService service, PhotoService photoService) throws IOException {
        this.lotteryService = service;
    }

    /**
     * Returns all lottery
     */
    @GetMapping
    public List<Lottery> getAll() {
        return lotteryService.getAll();
    }

    /**
     * Get item with id /{id}
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            System.out.println("Sending painting with id " + id);
            Lottery item = lotteryService.get(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
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
            return new ResponseEntity<>(lotteryService.delete(id), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Add new item
     */
    @PostMapping
    public ResponseEntity<Lottery> addNew(@RequestBody Lottery lottery) {
        System.out.println("Adding painting " + lottery.getTitle() + " id: " + lottery.getId());
        lottery.setId(null);
        return ResponseEntity.ok().body(lotteryService.add(lottery));
    }
}
