package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.ContestantRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContestantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContestantService.class);

    private final ContestantRepository repository;

    @Autowired
    public ContestantService(ContestantRepository repository, ServiceManager serviceManager) {
        this.repository = repository;
        serviceManager.addService(this);
    }

    public List<Contestant> getAll() {
        return repository.findAll();
    }

    public Contestant get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    // BUG: deleting a contestant who has a chosen art item deletes the art item too.
    public boolean delete(Long id) throws IdException {
        Contestant cont = get(id);
        // serviceManager.removeReferences(cont.get
        repository.deleteById(id);
        LOGGER.info("delete: " + cont.toString());
        return true;
    }

    public Contestant add(Contestant cont) {
        cont.setId(null);
        LOGGER.info("add: " + cont.toString());
        return repository.save(cont);
    }

    public Contestant update(Contestant cont) throws IdException {
        Contestant origCont = get(cont.getId());
        LOGGER.info("update: " + cont.toString());
        return repository.save(merge(origCont, cont));
    }

    public void assertExists(Long id) throws IdException {
        if (repository.existsById(id))
            return;
        else
            LOGGER.info("assertExists: " + id);
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
        if (newCont.getWinner() != null)
            origCont.setWinner(newCont.getWinner());

        return origCont;
    }
}
