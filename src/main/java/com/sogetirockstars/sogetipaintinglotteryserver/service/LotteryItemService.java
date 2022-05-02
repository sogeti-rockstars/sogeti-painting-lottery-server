package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * ContestantService
 */
@Service
public class LotteryItemService {
    private final LotteryItemRepository repository;

    @Autowired
    public LotteryItemService(LotteryItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<LotteryItem> getAllPaintings() {
        return repository.findAll();
    }

    @GetMapping
    public LotteryItem getPainting(Long id) {
        return repository.findById(id).get();
    }

    public LotteryItem save(LotteryItem lotteryItem) {
        return repository.save(lotteryItem);
    }

    @Transactional
    public void updatePainting(Long id, Integer lotteryId,
    String pictureUrl,
    String itemName,
    String artistName,
    String size, String frameDescription,
    String value, String technique) {
        LotteryItem lotteryItem = repository.findById(id).orElseThrow(() -> new IllegalStateException(
                "painting with id " + id + " does not exist"));
        if(lotteryId!=null)
        lotteryItem.setLotteryId(lotteryId);
        if(pictureUrl!=null)
        lotteryItem.setPictureUrl(pictureUrl);
        if(itemName!=null)
        lotteryItem.setItemName(itemName);
        if(artistName!=null)
        lotteryItem.setArtistName(artistName);
        if(size!=null)
        lotteryItem.setSize(size);
        if(frameDescription!=null)
        lotteryItem.setFrameDescription(frameDescription);
        if(value!=null)
        lotteryItem.setValue(value);
        if(technique!=null)
        lotteryItem.setTechnique(technique);
    }
}
