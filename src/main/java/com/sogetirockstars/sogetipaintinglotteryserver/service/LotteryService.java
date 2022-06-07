package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.io.InputStream;
import java.util.List;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.AllContestantsTakenException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.EmptyLotteryWinnerAssignmentException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.ContestantRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryItemRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.WinnerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {
    private final LotteryRepository repository;
    private final ContestantRepository contestantRepo;
    private final WinnerRepository winnerRepo;
    private final LotteryItemRepository lotteryItemRepo;
    private final PhotoService photoService;

    @Autowired
    public LotteryService(PhotoService photoService, LotteryRepository repository, ContestantRepository contestantRepo, WinnerRepository winnerRepo,
            LotteryItemRepository lotteryItemRepo) {
        this.winnerRepo = winnerRepo;
        this.repository = repository;
        this.contestantRepo = contestantRepo;
        this.lotteryItemRepo = lotteryItemRepo;
        this.photoService = photoService;
    }

    public Lottery addNewContestantToLottery(Long id, Contestant contestant) throws IdException {
        assertExists(id);
        contestantRepo.save(contestant);
        Lottery lottery = repository.findById(id).get();
        lottery.getContestants().add(contestant);
        return repository.saveAndFlush(lottery);
    }

    public Lottery addExistingContestantToLottery(Long lottId, Long contestantId) throws IdException {
        assertExists(lottId);
        if (contestantRepo.existsById(contestantId))
            throw new IdException("Contestant with id " + contestantId + "does not exist");

        Lottery lottery = repository.findById(lottId).get();
        Contestant contestant = contestantRepo.findById(contestantId).get();

        lottery.getContestants().add(contestant);
        return repository.saveAndFlush(lottery);
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

    public Lottery get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public List<LotteryItem> getLotteryItems(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get().getLotteryItems();
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
        Lottery oldLottery = repository.findById(newLottery.getId()).get();
        return repository.saveAndFlush(mergeLotterys(oldLottery, newLottery));
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

    private Lottery mergeLotterys(Lottery origItem, Lottery newItem) {
        if (newItem.getTitle() != null)
            origItem.setTitle(newItem.getTitle());
        if (newItem.getLotteryItems().size() > 0)
            origItem.setLotteryItems(newItem.getLotteryItems());
        if (newItem.getContestants().size() > 0)
            origItem.setContestants(newItem.getContestants());
        if (newItem.getWinners().size() > 0)
            origItem.setContestants(newItem.getContestants());
        return origItem;
    }
}
