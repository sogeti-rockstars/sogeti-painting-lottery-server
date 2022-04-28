package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.ContestantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * ContestantService
 */
@Service
public class ContestantService {
    private final ContestantRepository repository;

    @Autowired
    public ContestantService(ContestantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Contestant> getAllContestants() {
        return repository.findAll();
    }

    @GetMapping
    public Contestant getContestant(Long id) {
        return repository.findById(id).get();
    }
}
