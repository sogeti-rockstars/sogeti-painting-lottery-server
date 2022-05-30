package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.AllContestantsTakenException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Component
@RestController
@RequestMapping("api/v1/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService service) throws IOException {
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
     * Get all lotteries without lists. To be used by the sidebar.
     */
    @GetMapping(value = "summary")
    public ResponseEntity<?> getLotteryList() {
        List<Map<String, ?>> resp = new LinkedList<>();

        for (Lottery lottery : lotteryService.getAll()) {
            Map<String, String> respItem = new HashMap<>();
            respItem.put("id", lottery.getId().toString());
            respItem.put("title", lottery.getTitle());
            Date date = lottery.getDate();
            String dateUnix = (date != null) ? String.valueOf(date.getTime()) : "0";
            respItem.put("date", dateUnix);
            resp.add(respItem);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
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

    @GetMapping(value = "spin-with-item/{id}")
    public ResponseEntity<?> spinTheWheelRandomItem(@PathVariable Long id) {
        try {
            System.out.println("Sending painting with id " + id);
            Lottery lottery = lotteryService.get(id);
            Winner winner = lotteryService.spinTheWheelRandomItem(lottery);
            return new ResponseEntity<>(winner, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "spin/{id}")
    public ResponseEntity<?> spinTheWheelNoItem(@PathVariable Long id) {
        try {
            System.out.println("Sending painting with id " + id);
            Lottery lottery = lotteryService.get(id);
            Winner winner = lotteryService.spinTheWheelNoItem(lottery);
            return new ResponseEntity<>(winner, HttpStatus.OK);
        } catch (IdException | AllContestantsTakenException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}/winners")
    public ResponseEntity<?> getWinners(@PathVariable Long id) {
        try {
            System.out.println("Sending painting with id " + id);
            List<Winner> items = lotteryService.getWinners(id);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}/items")
    public ResponseEntity<?> getLotteryItems(@PathVariable Long id) {
        try {
            System.out.println("Sending painting with id " + id);
            List<LotteryItem> items = lotteryService.getLotteryItems(id);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}/contestants")
    public ResponseEntity<?> getContestants(@PathVariable Long id) {
        try {
            System.out.println("Sending painting with id " + id);
            List<Contestant> items = lotteryService.getContestants(id);
            return new ResponseEntity<>(items, HttpStatus.OK);
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

    @PutMapping(value = "addContestant/{id}")
    public ResponseEntity<?> addContestantToLottery(@PathVariable Long id, @RequestBody Contestant contestant) {
        try {

            return new ResponseEntity<>(lotteryService.addContestantToLottery(id, contestant), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Lottery lottery) {
        try {
            lottery.setId(id);
            return new ResponseEntity<>(lotteryService.update(lottery), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
