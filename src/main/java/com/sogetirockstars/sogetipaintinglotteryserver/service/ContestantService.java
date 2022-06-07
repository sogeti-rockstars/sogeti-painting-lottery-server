package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.ContestantRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.WinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestantService {
    private final ContestantRepository repository;
    private final LotteryRepository lotteryRepo;
    private final WinnerRepository winnerRepo;

    @Autowired
    public ContestantService(ContestantRepository repository, LotteryRepository lotteryRepo, WinnerRepository winnerRepo) {
        this.repository = repository;
        this.lotteryRepo = lotteryRepo;
        this.winnerRepo = winnerRepo;
    }

    public List<Contestant> getAll() {
        return repository.findAll();
    }

    public Contestant get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public boolean delete(Long id) throws IdException {
        assertExists(id);

        Contestant cont = repository.findById(id).get();
        lotteryRepo.findAll().stream().filter(lott -> lott.getContestants().remove(cont)).forEach(lott -> lotteryRepo.save(lott));
        lotteryRepo.findAll().stream().filter(lott -> lott.getWinners().removeIf(win -> win.getContestant() == cont)).forEach(lott -> lotteryRepo.save(lott));
        winnerRepo.findAll().stream().filter(winner -> winner.getContestant().equals(cont)).forEach(winner -> winnerRepo.delete(winner));

        repository.deleteById(id);
        return true;
    }

    public Contestant add(Contestant cont) {
        cont.setId(null);
        return repository.save(cont);
    }

    public Contestant update(Contestant cont) throws IdException {
        assertExists(cont.getId());
        Contestant origCont = repository.getById(cont.getId());
        return repository.save(merge(origCont, cont));
    }

    public void assertExists(Long id) throws IdException {
        if (!repository.existsById(id))
            throw new IdException("Item with id " + id + " doesn't exist.");
    }

    // Todo: detta borde kunna göras snyggare?? Vi kanske skulle ha DTO:s ändå, det fanns tydligen sätt
    // att skapa JSON
    // objekt och bara skriva över värden som har ett värde och inte NULL;
    private Contestant merge(Contestant origCont, Contestant newCont) {
        if (newCont.getId() != null)
            origCont.setId(newCont.getId());
        if (newCont.getName() != null)
            origCont.setName(newCont.getName());
        if (newCont.getEmail() != null)
            origCont.setEmail(newCont.getEmail());
        if (newCont.getEmployeeId() != null)
            origCont.setEmployeeId(newCont.getEmployeeId());
        if (newCont.getTeleNumber() != null)
            origCont.setTeleNumber(newCont.getTeleNumber());

        return origCont;
    }
}
