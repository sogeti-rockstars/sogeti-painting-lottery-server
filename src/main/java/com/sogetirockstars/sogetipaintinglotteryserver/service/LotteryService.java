package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotteryService {
    private final LotteryRepository repository;
    private final ContestantService contestantService;
    private final WinnerService winnerService;
    private final LotteryItemService lotteryItemService;

    @Autowired
    public LotteryService(LotteryRepository repository, ContestantService contestantService, WinnerService winnerService, LotteryItemService lotteryItemService) {
        this.repository = repository;
        this.contestantService = contestantService;
        this.winnerService = winnerService;
        this.lotteryItemService = lotteryItemService;
    }

    public Lottery addAllContestantsToLottery(Lottery lottery) {
        lottery.setContestants(contestantService.getAll());
        return lottery;
    }

    public Winner spinTheWheelSpecificItem(Lottery lottery, LotteryItem lotteryItem) {
        if (lottery.getContestants().size() == 0)
            lottery = this.addAllContestantsToLottery(lottery);
        List<Contestant> contestants = lottery.getContestants();
        Contestant winner = contestants.get((int) (Math.random() * (contestants.size() - 1 + 1) + 1));
        Winner newWinner = new Winner(lottery, winner,
                winnerService.getAllByLotteryId(lottery.getId()).size(),
                lotteryItem);
        return newWinner;
    }

    public Winner spinTheWheelRandomItem(Lottery lottery) throws IdException {
        if (lottery.getContestants().size() == 0)
            lottery = this.addAllContestantsToLottery(lottery);

        List<Contestant> contestants = lottery.getContestants();
        Contestant winner = contestants.get((int) (Math.random() * (contestants.size())));
        Winner newWinner = new Winner(lottery, winner,
                winnerService.getAllByLotteryId(lottery.getId()).size(),
                lotteryItemService.getRandomItem());
        return winnerService.add(newWinner);
    }

    public Winner spinTheWheelNoItem(Lottery lottery) throws IdException {
        if (lottery.getContestants().size() == 0)
            lottery = this.addAllContestantsToLottery(lottery);

        List<Contestant> contestants = lottery.getContestants();
        Contestant winner = contestants.get((int) (Math.random() * (contestants.size())));
        Winner newWinner = new Winner(lottery, winner,
                winnerService.getAllByLotteryId(lottery.getId()).size());
        return winnerService.add(newWinner);
    }

    public List<Lottery> getAll() {
        return repository.findAll();
    }

    public class LotterySmall {
        @JsonManagedReference
        private Long id;
        @JsonManagedReference
        private String title;
        @JsonManagedReference
        private Date date;
        public LotterySmall() {
        }
        public void setId(Long id) {
            this.id = id;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public void setDate(Date date) {
            this.date = date;
        }
    }
    public List<LotterySmall> getAllSmall() {
        return repository.findAll().stream().
                map(lottery -> {
                    LotterySmall smallLottery = new LotterySmall();
                    smallLottery.setId(lottery.getId());
                    smallLottery.setTitle(lottery.getTitle());
                    smallLottery.setDate(lottery.getDate());
                    return smallLottery;
                }).collect(Collectors.toList());
    }

    public Lottery get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }


    public Lottery add(Lottery lottery) {
        lottery.setId(null);
        return repository.save(lottery);
    }

    public Lottery update(Lottery newLottery) throws IdException {
        assertExists(newLottery.getId());
        Lottery originalLottery = repository.getById(newLottery.getId());
        return repository.save(mergeLotterys(originalLottery, newLottery));
    }

    private Lottery mergeLotterys(Lottery origItem, Lottery newItem) {
        if (newItem.getTitle() != null)
            origItem.setTitle(newItem.getTitle());
        if (newItem.getLotteryItems() != null)
            origItem.setLotteryItems(newItem.getLotteryItems());
        if (newItem.getContestants() != null)
            origItem.setContestants(newItem.getContestants());
        return origItem;
    }

    public boolean delete(Long id) throws IdException {
        assertExists(id);
        repository.deleteById(id);
        return true;
    }

    private void assertExists(Long id) throws IdException {
        if (!repository.existsById(id))
            throw new IdException(" with id " + id + " doesn't exist.");
    }
}
