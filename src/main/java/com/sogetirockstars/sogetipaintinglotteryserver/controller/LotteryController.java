package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.AllContestantsTakenException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.EmptyLotteryWinnerAssignmentException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/lottery")
public class LotteryController {
    private final LotteryService lotteryService;

    @Autowired
    public LotteryController(LotteryService service) throws IOException {
        this.lotteryService = service;
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
            Lottery newLott = new Lottery(lottery.getId(), lottery.getTitle(), lottery.getDate());
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

    @GetMapping(value = "{id}/winners")
    public ResponseEntity<?> getWinners(@PathVariable Long id) {
        try {
            List<Winner> items = lotteryService.getWinners(id);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}/items")
    public ResponseEntity<?> getLotteryItems(@PathVariable Long id) {
        try {
            List<LotteryItem> items = lotteryService.getLotteryItems(id);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "{id}/contestants")
    public ResponseEntity<?> getContestants(@PathVariable Long id) {
        try {
            List<Contestant> items = lotteryService.getContestants(id);
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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

    @PutMapping(value = "addItem/{id}")
    public ResponseEntity<?> addItemToLottery(@PathVariable Long id, @RequestBody LotteryItem lotteryItem) {
        try {
            return new ResponseEntity<>(lotteryService.addItemToLottery(id, lotteryItem), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "editItem/{id}")
    public ResponseEntity<?> editItemToLottery(@PathVariable Long id, @RequestBody LotteryItem lotteryItem) {
        System.out.println(id + "++-" + lotteryItem.toString());
        try {
            return new ResponseEntity<>(lotteryService.editItemToLottery(id, lotteryItem), HttpStatus.OK);
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
