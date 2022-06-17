package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;
import java.util.Set;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;

import org.springframework.stereotype.Service;

/**
 * Mediator class to facilitte inter-service interaction.
 */
@Service
public class ServiceManager {
    private LotteryService lotteryService;
    private LotteryItemService lotteryItemService;
    private ContestantService contestantService;
    private WinnerService winnerService;

    public ServiceManager() {
    }

    public void addService(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    public void addService(LotteryItemService lotteryItemService) {
        this.lotteryItemService = lotteryItemService;
    }

    public void addService(ContestantService contestantService) {
        this.contestantService = contestantService;
    }

    public void addService(WinnerService winnerService) {
        this.winnerService = winnerService;
    }

    public void removeReferences(LotteryItem item) {
        if (item != null)
            winnerService.removeReferences(item);
    }

    public void ensureConsecutivePlacements(Set<Winner> winners) {
        winnerService.ensureConsecutivePlacements(winners);
    }

    public void removeAllWinnerOccurances(Contestant contestant) {
        this.lotteryService.removeAllWinnerOccurances(contestant);
    }

    public List<Contestant> getAllContestants() {
        return this.contestantService.getAll();
    }

    public Set<Winner> getWinnersByLotteryId(Long lottId) throws IdException {
        return lotteryService.get(lottId).getWinners();
    }

    public Contestant getContestantById(Long id) throws IdException {
        return this.contestantService.get(id);
    }

    public LotteryItem getLotteryItem(Long id) throws IdException {
        return this.lotteryItemService.getItem(id);
    }

    // Only used by config/mock-data, potentialy remove
    public Winner addWinner(long contestantId, Lottery lottery, int placement) throws IdException {
        return winnerService.addNew(contestantId, lottery, placement);
    }

    public void addLotteryItem(LotteryItem item) {
        lotteryItemService.add(item);
    }

    public void removeItemFromLottery(LotteryItem lotteryItem) throws IdException {
        lotteryService.removeItemFromLottery(lotteryItem.getLottery().getId(), lotteryItem);
    }
}
