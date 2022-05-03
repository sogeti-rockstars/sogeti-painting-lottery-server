package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.ContestantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ContestantService
 */
@Service
public class ContestantService { // JQ: Det blev mycket kod-duplicering här. Ska vi köra på abstract class + extends istället?
    private final ContestantRepository repository;

    @Autowired
    public ContestantService(ContestantRepository repository) {
        this.repository = repository;
    }

    public List<Contestant> getAll() {
        return repository.findAll();
    }

    public Contestant get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public boolean delete(Long id) throws IdException{
        assertExists(id);
        repository.deleteById(id);
        return true;
    }

    public Contestant add(Contestant cont){
        cont.setId(null);
        return repository.save(cont);
    }

    public Contestant update(Contestant cont) throws IdException {
        assertExists(cont.getId());
        Contestant origCont = repository.getById( cont.getId() );
        return repository.save( merge( origCont, cont) );
    }

    private void assertExists(Long id) throws IdException {
        if ( !repository.existsById( id ) )
            throw new IdException("Item with id " + id + " doesn't exist.");
    }

    // Todo: detta borde kunna göras snyggare?? Vi kanske skulle ha DTO:s ändå, det fanns tydligen sätt att skapa JSON
    //       objekt och bara skriva över värden som har ett värde och inte NULL; Alt. skulle vi kuna köra reflection :)
    private Contestant merge(Contestant origCont, Contestant newCont){
        if (newCont.getId() != null)
            origCont.setId(newCont.getId());
        if (newCont.getName()!=null)
            origCont.setName(newCont.getName());
        if (newCont.getEmail()!=null)
            origCont.setEmail(newCont.getEmail());
        if (newCont.getAddress()!=null)
            origCont.setAddress(newCont.getAddress());
        if (newCont.getEmployeeId()!=null)
            origCont.setEmployeeId(newCont.getEmployeeId());
        if (newCont.getTeleNumber()!=null)
            origCont.setTeleNumber(newCont.getTeleNumber());

        return origCont;
    }
}
