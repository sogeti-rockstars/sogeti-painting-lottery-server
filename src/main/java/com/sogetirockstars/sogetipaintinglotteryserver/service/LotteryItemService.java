package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ContestantService
 */
@Service
public class LotteryItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryItemService.class);

    private final LotteryItemRepository repository;
    private final ServiceManager serviceManager;

    @Autowired
    public LotteryItemService(LotteryItemRepository repository, ServiceManager serviceManager) {
        this.repository = repository;
        serviceManager.addService(this);
        this.serviceManager = serviceManager;
    }

    public List<LotteryItem> getAll() {
        return repository.findAll();
    }

    public LotteryItem getItem(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public LotteryItem add(LotteryItem item) {
        repository.save(item);
        LOGGER.info("add: " + item.toString());
        return repository.save(item);
    }

    public LotteryItem update(LotteryItem newItem) throws IdException {
        LotteryItem origItem = getItem(newItem.getId());
        mergeItems(origItem, newItem);
        origItem = repository.saveAndFlush(origItem);
        LOGGER.info("update:\norigItem: " + origItem.toString() + "\nnewItem: " + newItem.toString());
        return origItem;
    }

    public boolean delete(Long id) throws IdException {
        LotteryItem item = getItem(id);
        // Maybe it's possible to do this with pure hibernate, couldn't get it to work. This does.
        // The goal is to remove a winners chosen item wthout removing the winner.
        serviceManager.removeReferences(item);
        repository.deleteById(id);
        LOGGER.info("delete: " + item.toString());
        return true;
    }

    private void assertExists(Long id) throws IdException {
        if (repository.existsById(id))
            return;
        else
            LOGGER.info("assertExists: " + id);
        throw new IdException("Item with id " + id + " doesn't exist.");
    }

    // Todo: detta borde kunna göras snyggare?? Vi kanske skulle ha DTO:s ändå, det fanns tydligen sätt att skapa JSON
    // objekt och bara skriva över värden som har ett värde och inte NULL;
    private LotteryItem mergeItems(LotteryItem origItem, LotteryItem newItem) {
        if (newItem.getItemName() != null)
            origItem.setItemName(newItem.getItemName());
        if (newItem.getArtistName() != null)
            origItem.setArtistName(newItem.getArtistName());
        if (newItem.getSize() != null)
            origItem.setSize(newItem.getSize());
        if (newItem.getFrameDescription() != null)
            origItem.setFrameDescription(newItem.getFrameDescription());
        if (newItem.getValue() != null)
            origItem.setValue(newItem.getValue());
        if (newItem.getTechnique() != null)
            origItem.setTechnique(newItem.getTechnique());
        if (newItem.getWinner() != null)
            origItem.setWinner(newItem.getWinner());
        if (newItem.getLottery() != null)
            origItem.setLottery(newItem.getLottery());

        return origItem;
    }
}
