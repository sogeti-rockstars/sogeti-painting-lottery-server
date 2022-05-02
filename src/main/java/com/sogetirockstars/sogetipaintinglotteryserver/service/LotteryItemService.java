package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ContestantService
 */
@Service
public class LotteryItemService implements SqlService<LotteryItem> {
    private final LotteryItemRepository repository;

    @Autowired
    public LotteryItemService(LotteryItemRepository repository) {
        this.repository = repository;
    }

    public List<LotteryItem> getAllPaintings() {
        return repository.findAll();
    }

    public LotteryItem getItem(Long id) {
        return repository.findById(id).get();
    }

    public LotteryItem save(LotteryItem lotteryItem) {
        return repository.save(lotteryItem);
    }

    public boolean delete(Long id){
        if ( repository.findById(id).isEmpty() )
            return false;
        repository.deleteById(id);
        return true;
    }

    public LotteryItem add(LotteryItem item){
        item.setId(null);
        return save(item);
    }

    public LotteryItem update(LotteryItem newItem){
        LotteryItem origItem = repository.getById( newItem.getId() );
        return save( mergeItems( origItem, newItem) );
    }

    public boolean existsById(Long id){
        return repository.existsById(id);
    }

    // Todo: detta borde kunna göras snyggare?? Vi kanske skulle ha DTO:s ändå, det fanns tydligen sätt att skapa JSON
    //       objekt och bara skriva över värden som har ett värde och inte NULL;
    private LotteryItem mergeItems(LotteryItem origItem, LotteryItem newItem){
        if (newItem.getLotteryId() != null)
            origItem.setLotteryId(newItem.getLotteryId());
        if (newItem.getPictureUrl()!=null)
            origItem.setPictureUrl(newItem.getPictureUrl());
        if (newItem.getItemName()!=null)
            origItem.setItemName(newItem.getItemName());
        if (newItem.getArtistName()!=null)
            origItem.setArtistName(newItem.getArtistName());
        if (newItem.getSize()!=null)
            origItem.setSize(newItem.getSize());
        if (newItem.getFrameDescription()!=null)
            origItem.setFrameDescription(newItem.getFrameDescription());
        if (newItem.getValue()!=null)
            origItem.setValue(newItem.getValue());
        if (newItem.getTechnique()!=null)
            origItem.setTechnique(newItem.getTechnique());

        return origItem;
    }
}
