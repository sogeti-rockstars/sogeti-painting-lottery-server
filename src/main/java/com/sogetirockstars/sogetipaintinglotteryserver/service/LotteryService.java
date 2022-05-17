package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotteryService {
    private final LotteryRepository repository;

    @Autowired
    public LotteryService(LotteryRepository repository) {
        this.repository = repository;
    }

    public List<Lottery> getAll() {
        return repository.findAll();
    }

    public Lottery get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public Lottery add(Lottery lottery) {
        lottery.setId(null);
        return repository.save(lottery);
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
