package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryItemRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ContestantService
 */
@Service
public class LotteryItemService {
    private final LotteryItemRepository repository;
    private final LotteryRepository lotteryRepo;

    @Autowired
    public LotteryItemService(LotteryItemRepository repository, LotteryRepository lotteryRepo) {
        this.repository = repository;
        this.lotteryRepo = lotteryRepo;
    }

    public List<LotteryItem> getAll() {
        return repository.findAll();
    }

    public LotteryItem getItem(Long id) throws IdException {
        assertExists(id);
        return repository.findById(id).get();
    }

    public LotteryItem getRandomItem() throws IdException {
        int size = this.getAll().size();
        long id = (long) (Math.random() * (size));
        List<LotteryItem> lotteryItems = this.getAll();
        assertExists(lotteryItems.get((int) id).getId());
        return lotteryItems.get((int) id);
    }

    public LotteryItem add(LotteryItem item) {
        item.setId(null);
        return repository.save(item);
    }

    public LotteryItem update(LotteryItem newItem) throws IdException {
        assertExists(newItem.getId());
        LotteryItem origItem = repository.getById(newItem.getId());
        return repository.save(mergeItems(origItem, newItem));
    }

    public boolean delete(Long id) throws IdException {
        assertExists(id);
        LotteryItem item = repository.findById(id).get();
        lotteryRepo.findAll().stream().forEach(lott -> lott.getLotteryItems().remove(item));
        repository.deleteById(id);
        return true;
    }

    private void assertExists(Long id) throws IdException {
        if (!repository.existsById(id))
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

        return origItem;
    }
}
