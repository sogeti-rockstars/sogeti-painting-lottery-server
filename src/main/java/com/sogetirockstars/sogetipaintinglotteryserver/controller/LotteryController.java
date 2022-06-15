package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.AllContestantsTakenException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.EmptyLotteryWinnerAssignmentException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.service.LotteryService;
import com.sogetirockstars.sogetipaintinglotteryserver.service.PhotoService;

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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/lottery")
public class LotteryController {
    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService lotteryService, PhotoService photoService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping
    public List<Lottery> getAll() {
        return lotteryService.getAll();
    }

    /**
     * Get all lotteries without lists. Used by the sidebar in the frontend
     */
    @GetMapping(value = "summary")
    public ResponseEntity<?> getLotteryList() {
        List<Lottery> resp = new LinkedList<>();

        for (Lottery lottery : lotteryService.getAll()) {
            Lottery newLott = new Lottery(lottery.getId(), lottery.getTitle());
            resp.add(newLott);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            Lottery item = lotteryService.get(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}/winners")
    public ResponseEntity<?> getWinners(@PathVariable Long id) {
        try {
            Set<Winner> items = lotteryService.getWinners(id);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}/items")
    public ResponseEntity<?> getLotteryItems(@PathVariable Long id) {
        try {
            Set<LotteryItem> items = lotteryService.getLotteryItems(id);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}/available-items")
    public ResponseEntity<?> getAvailableLotteryItems(@PathVariable Long id) {
        try {
            Set<LotteryItem> unavailableItems = lotteryService.getWinners(id).stream().map(win -> win.getLotteryItem()).collect(Collectors.toSet());
            Set<LotteryItem> items = lotteryService.getLotteryItems(id);
            items.removeAll(unavailableItems);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "{id}/spin")
    public ResponseEntity<?> spinTheWheelNoItem(@PathVariable Long id) {
        try {
            Lottery lottery = lotteryService.get(id);
            Winner winner = lotteryService.spinTheWheelNoItem(lottery);
            return new ResponseEntity<>(winner, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AllContestantsTakenException | EmptyLotteryWinnerAssignmentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(lotteryService.delete(id), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Lottery> addNew(@RequestBody Lottery lottery) {
        lottery.setId(null);
        return ResponseEntity.ok().body(lotteryService.add(lottery));
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
