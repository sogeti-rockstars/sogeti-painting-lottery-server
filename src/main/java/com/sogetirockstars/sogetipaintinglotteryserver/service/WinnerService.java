package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;
import java.util.Optional;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.WinnerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WinnerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WinnerService.class);

    private final WinnerRepository repository;
    private final ServiceManager serviceManager;

    @Autowired
    public WinnerService(WinnerRepository repository, LotteryRepository lotteryRepo, ServiceManager serviceManager) {
        this.repository = repository;
        this.serviceManager = serviceManager;
        serviceManager.addService(this);
    }

    public List<Winner> getAll() {
        return repository.findAll();
    }

    public Winner get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public Winner addNew(long contestantId, Lottery lottery, int placement) throws IdException {
        Contestant contestant = serviceManager.getContestantById(contestantId);
        Winner nWinner = new Winner(contestant, placement);
        nWinner.setLottery(lottery);
        return repository.saveAndFlush(nWinner);
    }

    public Winner update(Winner newWinner) throws IdException {
        assertExists(newWinner.getId());
        Winner origWinner = repository.findById(newWinner.getId()).get();
        return repository.save(mergeWinners(origWinner, newWinner));
    }

    public boolean delete(Long id) throws IdException {
        Winner winner = get(id);
        serviceManager.removeAllWinnerOccurances(winner);
        repository.deleteById(id);
        return true;
    }

    public boolean delete(Contestant contestant) {
        Optional<Winner> matchingWinners = repository.findAll().stream().filter(winner -> winner.getContestant().equals(contestant)).findFirst();
        if (matchingWinners.isPresent()) {
            repository.delete(matchingWinners.get());
            return true;
        }
        return false;
    }

    private void assertExists(Long id) throws IdException {
        if (repository.existsById(id))
            return;
        else
            LOGGER.info("assertExists: " + id);
        throw new IdException("Item with id " + id + " doesn't exist.");
    }

    private Winner mergeWinners(Winner origWinner, Winner newWinner) {
        if (newWinner.getContestant() != null)
            origWinner.setContestant(newWinner.getContestant());
        if (newWinner.getPlacement() != null)
            origWinner.setPlacement(newWinner.getPlacement());
        if (newWinner.getLotteryItem() != null)
            origWinner.setLotteryItem(newWinner.getLotteryItem());
        return origWinner;
    }
}
