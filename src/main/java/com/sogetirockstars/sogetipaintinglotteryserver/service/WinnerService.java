package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.WinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WinnerService {
    private final WinnerRepository repository;

    @Autowired
    public WinnerService(WinnerRepository repository) {
        this.repository = repository;
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
        return repository.save(item);
    }

    public Winner update(Winner newWinner) throws IdException {
        assertExists(newWinner.getId());
        Winner origWinner = repository.getById(newWinner.getId());
        return repository.save(mergeWinners(origWinner, newWinner));
    }


    public boolean delete(Long id) throws IdException {
        assertExists(id);
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
        if (newWinner.getLottery() != null)
            origWinner.setLottery(newWinner.getLottery());
        if (newWinner.getLotteryItem() != null)
            origWinner.setLotteryItem(newWinner.getLotteryItem());
        if (newWinner.getPlacement() != null)
            origWinner.setPlacement(newWinner.getPlacement());
        return origWinner;
    }
}
