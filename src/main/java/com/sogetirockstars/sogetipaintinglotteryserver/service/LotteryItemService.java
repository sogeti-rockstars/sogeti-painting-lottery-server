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
    public void updatePainting(Long id, int lotteryId,
    String pictureUrl,
    String itemName,
    String artistName,
    String size, String frameDescription,
    String value, String technique) {
        LotteryItem lotteryItem = repository.findById(id).orElseThrow(() -> new IllegalStateException(
                "painting with id " + id + " does not exist"));
        lotteryItem.setLotteryId(lotteryId);
        lotteryItem.setPictureUrl(pictureUrl);
        lotteryItem.setItemName(itemName);
        lotteryItem.setArtistName(artistName);
        lotteryItem.setSize(size);
        lotteryItem.setFrameDescription(frameDescription);
        lotteryItem.setValue(value);
        lotteryItem.setTechnique(technique);
    }
}
