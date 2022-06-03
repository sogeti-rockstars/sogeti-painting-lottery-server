package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.WinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WinnerService {
    private final WinnerRepository repository;
    private final LotteryRepository lotteryRepo;

    @Autowired
    public WinnerService(WinnerRepository repository, LotteryRepository lotteryRepo) {
        this.repository = repository;
        this.lotteryRepo = lotteryRepo;
    }

    public List<Winner> getAll() {
        return repository.findAll();
    }

    public Winner get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public Winner add(Winner item) {
        item.setId(null);
        return repository.saveAndFlush(item);
    }

    public Winner update(Winner newWinner) throws IdException {
        assertExists(newWinner.getId());
        Winner origWinner = repository.findById(newWinner.getId()).get();
        return repository.save(mergeWinners(origWinner, newWinner));
    }

    public boolean delete(Long id) throws IdException {
        assertExists(id);
        Winner winner = repository.findById(id).get();
        lotteryRepo.findAll().stream().forEach(lott -> lott.getWinners().remove(winner));
        repository.deleteById(id);
        return true;
    }

    private void assertExists(Long id) throws IdException {
        if (!repository.existsById(id))
            throw new IdException("Winner with id " + id + " doesn't exist.");
    }

    private Winner mergeWinners(Winner origWinner, Winner newWinner) {
        if (newWinner.getContestant() != null)
            origWinner.setContestant(newWinner.getContestant());
        if (newWinner.getPlacement() != null)
            origWinner.setPlacement(newWinner.getPlacement());
        return origWinner;
    }
}
