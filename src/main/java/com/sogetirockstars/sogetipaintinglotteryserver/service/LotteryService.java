package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.AllContestantsTakenException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.EmptyLotteryWinnerAssignmentException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryService.class);

    private final LotteryRepository repository;
    private final ServiceManager serviceManager;

    public LotteryService(LotteryRepository repository, ServiceManager serviceManager) {
        this.repository = repository;
        this.serviceManager = serviceManager;
        serviceManager.addService(this);
    }

    public List<Lottery> getAll() {
        return repository.findAll();
    }

    public Lottery get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    // This is an ugly work-around for misconfigured hibernate stuff...Please fix
    public Set<LotteryItem> getLotteryItems(Long id) throws IdException {
        Set<LotteryItem> allItems = get(id).getLotteryItems();
        Set<LotteryItem> unavailableItems = getWinners(id).stream().map(win -> {
            LotteryItem item = win.getLotteryItem();
            if (item != null)
                item.setWinner(win);
            return item;
        }).filter(item -> item != null).collect(Collectors.toSet());
        allItems.addAll(unavailableItems);
        return allItems;
    }

    public Set<Winner> getWinners(Long id) throws IdException {
        return get(id).getWinners();
    }

    public Lottery add(Lottery lottery) {
        lottery.setId(null);
        return repository.save(lottery);
    }

    public Lottery update(Lottery newLottery) throws IdException {
        Lottery originalLottery = get(newLottery.getId());
        LOGGER.info("update: " + originalLottery.toString());
        return repository.save(mergeLotterys(originalLottery, newLottery));
    }

    public Lottery addItemToLottery(Long id, LotteryItem lotteryItem) throws IdException {
        Lottery lottery = get(id);
        lotteryItem.setLottery(lottery);
        serviceManager.addLotteryItem(lotteryItem);
        lottery.addLotteryItems(lotteryItem);
        LOGGER.info("addItemToLottery: Lottery:" + lottery.toString() + ", item:" + lotteryItem.toString());
        return lottery;
    }

    public Lottery removeItemFromLottery(Long id, LotteryItem lotteryItem) throws IdException {
        Lottery lottery = get(id);
        lotteryItem.setLottery(null);
        lottery.getLotteryItems().remove(lotteryItem);
        LOGGER.info("removeItemFromLottery: Lottery:" + lottery.toString() + ", item:" + lotteryItem.toString());
        repository.save(lottery);
        return lottery;
    }

    public Winner spinTheWheelNoItem(Lottery lottery) throws IdException, AllContestantsTakenException, EmptyLotteryWinnerAssignmentException {
        List<Contestant> contestants = serviceManager.getAllContestants();
        if (contestants.size() == 0)
            throw new EmptyLotteryWinnerAssignmentException("No contestants in lottery " + lottery.getId());

        serviceManager.ensureConsecutivePlacements(lottery.getWinners());
        List<Long> winnerIds = lottery.getWinners().stream().map(c -> c.getContestantId()).toList();
        List<Long> nonWinnerIds = contestants.stream().map(c -> c.getId()).filter(id -> !winnerIds.contains(id)).toList();

        if (nonWinnerIds.size() == 0)
            throw new AllContestantsTakenException("No non-winning contestants in lottery with id " + lottery.getId() + ".");

        int randomContestantIdx = (int) (Math.random() * (nonWinnerIds.size()));
        Long winnerContestantId = nonWinnerIds.get(randomContestantIdx);

        Winner nWinner = serviceManager.addWinner(winnerContestantId, lottery, winnerIds.size());
        lottery.getWinners().add(nWinner);
        repository.saveAndFlush(lottery);

        LOGGER.info("spinTheWheelNoItem: Lottery:" + lottery.toString() + ", winner:" + nWinner.toString());
        return nWinner;
    }

    public void setGuaranteePrize(Long id, LotteryItem guaranteeItem) throws IdException {
        Lottery lott = get(id);
        lott.setGuaranteePrize(guaranteeItem);
        serviceManager.addLotteryItem(guaranteeItem);
        repository.save(lott);
    }

    public LotteryItem getGuaranteePrize(Long id) throws IdException {
        return get(id).getGuaranteePrize();
    }

    // BUG: deleting a contestant who has a chosen art item deletes the art item too.
    public void removeAllWinnerOccurances(Contestant cont) {
        getAll().stream().filter(lott -> {
            return lott.getWinners().removeIf(win -> win.getContestant() == cont);
        }).forEach(lott -> repository.save(lott));
    }

    public boolean delete(Long id) throws IdException {
        assertExists(id);
        repository.deleteById(id);
        LOGGER.info("delete: " + id);
        return true;
    }

    private Lottery mergeLotterys(Lottery origItem, Lottery newItem) {
        if (newItem.getTitle() != null)
            origItem.setTitle(newItem.getTitle());
        if (newItem.getLotteryItems().size() > 0)
            origItem.setLotteryItems(newItem.getLotteryItems());
        if (newItem.getWinners().size() > 0)
            origItem.setWinners(newItem.getWinners());
        return origItem;
    }

    private void assertExists(Long id) throws IdException {
        if (repository.existsById(id))
            return;
        else
            LOGGER.info("assertExists: " + id);
        throw new IdException("Item with id " + id + " doesn't exist.");
    }
}
