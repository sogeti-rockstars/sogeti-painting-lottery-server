package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.AssociationInfo;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.AssociationInfoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AssociationInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssociationInfoService.class);
    private final AssociationInfoRepository repository;

    public AssociationInfoService(AssociationInfoRepository repo) {
        this.repository = repo;
    };

    public List<AssociationInfo> getAll() {
        return repository.findAll();
    }

    public AssociationInfo get(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public AssociationInfo findByField(String field) throws IdException {
        return repository.findByField(field);
    }

    public boolean delete(Long id) throws IdException {
        assertExists(id);
        repository.deleteById(id);
        return true;
    }

    public AssociationInfo add(AssociationInfo infoItem) {
        infoItem.setId(null);
        return repository.save(infoItem);
    }

    public AssociationInfo update(AssociationInfo infoItem) throws IdException {
        assertExists(infoItem.getId());
        AssociationInfo origInfoItem = repository.getById(infoItem.getId());
        return repository.save(merge(origInfoItem, infoItem));
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
    private AssociationInfo merge(AssociationInfo origInfoItem, AssociationInfo newInfoItem) {
        if (newInfoItem.getId() != null)
            origInfoItem.setId(newInfoItem.getId());
        if (newInfoItem.getData() != null)
            origInfoItem.setData(newInfoItem.getData());

        return origInfoItem;
    }
}
