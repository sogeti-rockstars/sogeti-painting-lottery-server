package com.sogetirockstars.sogetipaintinglotteryserver.service;

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
    public List<com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem> getAllPaintings() {
        return repository.findAll();
    }

    @GetMapping
    public com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem getPainting(Long id) {
        return repository.findById(id).get();
    }

    public com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem save(com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem lotteryItem) {
        return repository.save(lotteryItem);
    }

    @Transactional
    public void updatePainting(Long paintingId, String artist, String itemName, String pictureUrl) {
        com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem lotteryItem = repository.findById(paintingId).orElseThrow(() -> new IllegalStateException(
                "painting with id " + paintingId + " does not exist"));

        lotteryItem.setArtistName(artist);
        lotteryItem.setItemName(itemName);
        lotteryItem.setPictureUrl(pictureUrl);
    }
}
