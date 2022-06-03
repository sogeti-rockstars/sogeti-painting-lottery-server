package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.AllContestantsTakenException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Lottery addAllContestantsToLottery(Lottery newLottery) throws IdException {
        newLottery.setContestants(contestantService.getAll());
        assertExists(newLottery.getId());
        Lottery originalLottery = repository.getById(newLottery.getId());
        return repository.save(mergeLotterys(originalLottery, newLottery));
    }

    public Lottery addContestantToLottery(Long id, Contestant contestant) throws IdException {
        Lottery newLottery = repository.getById(id);
        List<Contestant> newContestants = newLottery.getContestants();
        newContestants.add(contestant);
        newLottery.setContestants(newContestants);
        assertExists(newLottery.getId());
        Lottery originalLottery = repository.getById(newLottery.getId());
        return repository.save(mergeLotterys(originalLottery, newLottery));
    }

    public Lottery addItemToLottery(Long id, LotteryItem lotteryItem) throws IdException {
        Lottery newLottery = repository.getById(id);
        List<LotteryItem> newItem = newLottery.getLotteryItems();
        newItem.add(lotteryItem);
        newLottery.setLotteryItems(newItem);
        assertExists(newLottery.getId());
        Lottery originalLottery = repository.getById(newLottery.getId());
        lotteryItem.setLottery(originalLottery);
        lotteryItemService.add(lotteryItem);
        return repository.save(mergeLotterys(originalLottery, newLottery));
    }

    public Lottery editItemToLottery(Long id, LotteryItem lotteryItem) throws IdException {
        Lottery newLottery = get(id);
        lotteryItem.setLottery(newLottery);
        lotteryItemService.save(lotteryItem);
        return newLottery;
    }

//    public Winner spinTheWheelSpecificItem(Lottery lottery, LotteryItem lotteryItem) throws IdException {
//        if (lottery.getContestants().size() == 0)
//            lottery = this.addAllContestantsToLottery(lottery);
//        List<Contestant> contestants = lottery.getContestants();
//        Contestant winner = contestants.get((int) (Math.random() * (contestants.size() - 1 + 1) + 1));
//        Winner newWinner = new Winner(lottery, winner,
//                winnerService.getAllByLotteryId(lottery.getId()).size(),
//                lotteryItem);
//        return newWinner;
//    }

    public Winner spinTheWheelRandomItem(Lottery lottery) throws IdException {
        if (lottery.getContestants().size() == 0)
            lottery = this.addAllContestantsToLottery(lottery);

        List<Contestant> contestants = lottery.getContestants();
        Contestant winner = contestants.get((int) (Math.random() * (contestants.size())));
//        Winner newWinner = new Winner(lottery, winner,
//                winnerService.getAllByLotteryId(lottery.getId()).size(),
//                lotteryItemService.getRandomItem());
        Winner newWinner = new Winner(winner, getAllWinnersByLotteryId(lottery.getId()).size());
        return winnerService.add(newWinner);
    }

    public Winner spinTheWheelNoItem(Lottery lottery) throws IdException, AllContestantsTakenException {
        if (lottery.getContestants().size() == 0)
            lottery = this.addAllContestantsToLottery(lottery);

        List<Contestant> contestants = lottery.getContestants();

        boolean checkWinners = false;
        int randomNumber = 0;
        while (checkWinners == false) {
            randomNumber = (int) (Math.random() * (contestants.size()));
            List<Winner> currentWinners = getAllWinnersByLotteryId(lottery.getId());
            List<Long> winnerContestantIds = new ArrayList<>();
            for (Winner winner :
                    currentWinners) {
                winnerContestantIds.add(winner.getContestantId());
            }
            if (!winnerContestantIds.contains(contestants.get(randomNumber).getId())) {
                checkWinners = true;
            }
            if (currentWinners.size() >= contestants.size()) {
                throw new AllContestantsTakenException("There are no contestants in lottery " + lottery.getId() + " which do not have a winner");
            }
        }
        Contestant winner = contestants.get(randomNumber);
//        Winner newWinner = new Winner(lottery, winner,
//                winnerService.getAllByLotteryId(lottery.getId()).size());
        Winner newWinner = new Winner(winner, getAllWinnersByLotteryId(lottery.getId()).size());
        return winnerService.add(newWinner);
    }

    public List<Lottery> getAll() {
        return repository.findAll();
    }

    public List<Winner> getAllWinnersByLotteryId(Long id) {
        Lottery lottery = repository.getById(id);
        return lottery.getWinners();
    }

    public Lottery get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public List<LotteryItem> getLotteryItems(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get().getLotteryItems();
    }

    public void addWinner(Long id, Winner winner) throws IdException {
        assertExists(id);
        Lottery lott = repository.findById(id).get();
        lott.getWinners().add(winner);
        repository.saveAndFlush(lott);
    }

    public List<Contestant> getContestants(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get().getContestants();
    }

    public List<Winner> getWinners(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get().getWinners();
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
