package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.AllContestantsTakenException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.EmptyLotteryWinnerAssignmentException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class LotteryService {
    private final LotteryRepository repository;
    private final ContestantService contestantService;
    private final WinnerService winnerService;
    private final LotteryItemService lotteryItemService;
    private final PhotoService photoService;

    @Autowired
    public LotteryService(LotteryRepository repository, ContestantService contestantService, WinnerService winnerService, LotteryItemService lotteryItemService, PhotoService photoService) {
        this.repository = repository;
        this.contestantService = contestantService;
        this.winnerService = winnerService;
        this.lotteryItemService = lotteryItemService;
        this.photoService = photoService;
    }

    public Lottery addAllContestantsToLottery(Lottery newLottery) throws IdException {
        assertExists(newLottery.getId());
        Lottery originalLottery = repository.getById(newLottery.getId());
        return repository.save(mergeLotterys(originalLottery, newLottery));
    }

    public Lottery addNewContestantToLottery(Long id, Contestant contestant) throws IdException {
        assertExists(id);
        contestantService.save(contestant);
        Lottery lottery = repository.getById(id);
        return repository.saveAndFlush(lottery);
    }

    public Lottery addExistingContestantToLottery(Long lottId, Long contestantId) throws IdException {
        assertExists(lottId);
        contestantService.assertExists(contestantId);

        Lottery lottery = repository.getById(lottId);
        Contestant contestant = contestantService.get(contestantId);

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

    public Winner spinTheWheelNoItem(Lottery lottery) throws IdException, AllContestantsTakenException, EmptyLotteryWinnerAssignmentException {
        if (contestantService.getAll().size() == 0)
            throw new EmptyLotteryWinnerAssignmentException("No contestants in lottery " + lottery.getId());

        List<Long> winnerIds = lottery.getWinners().stream().map(c -> c.getContestantId()).toList();
        List<Long> nonWinnerIds = contestantService.getAll().stream().map(c -> c.getId()).filter(id -> !winnerIds.contains(id)).toList();

        if (nonWinnerIds.size() == 0)
            throw new AllContestantsTakenException("No non-winning contestants in lottery with id " + lottery.getId() + ".");

        int randomContestantIdx = (int) (Math.random() * (nonWinnerIds.size()));
        Long winnerContestantId = nonWinnerIds.get(randomContestantIdx);

        Winner nWinner = new Winner(contestantService.get(winnerContestantId), winnerIds.size());
        nWinner.setLottery(lottery);
        winnerService.save(nWinner);

        lottery.addWinners(nWinner);
        repository.saveAndFlush(lottery);
        return nWinner;
    }

    public Lottery addNewItemToLottery(Long id, LotteryItem lotteryItem) throws IdException {
        assertExists(id);
        Lottery lottery = repository.findById(id).get();
        lotteryItemService.save(lotteryItem);
        lottery.getLotteryItems().add(lotteryItem);
        photoService.savePhoto(lotteryItem.getId(), InputStream.nullInputStream());
        return repository.saveAndFlush(lottery);
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
        return contestantService.getAll();
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