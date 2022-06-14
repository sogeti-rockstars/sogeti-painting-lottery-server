package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;

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

    public LotteryService getLotteryService() {
        return lotteryService;
    }

    public LotteryItemService getLotteryItemService() {
        return lotteryItemService;
    }

    public ContestantService getContestantService() {
        return contestantService;
    }

    public WinnerService getWinnerService() {
        return winnerService;
    }

    public void removeAllWinnerOccurances(Contestant contestant) {
        this.lotteryService.removeAllWinnerOccurances(contestant);
    }

    public List<Contestant> getAllContestants() {
        return this.contestantService.getAll();
    }

    public Contestant getContestantById(Long id) throws IdException {
        return this.contestantService.get(id);
    }

    public LotteryItem getLotteryItem(Long id) throws IdException {
        return this.lotteryItemService.getItem(id);
    }

    public LotteryItem updateLotteryItem(LotteryItem lotteryItem) throws IdException {
        return this.lotteryItemService.update(lotteryItem);
    }

    public Winner addWinner(long contestantId, Lottery lottery, int placement) throws IdException {
        return winnerService.addNew(contestantId, lottery, placement);
    }

    public void addLotteryItem(LotteryItem item) {
        lotteryItemService.add(item);
    }
}
