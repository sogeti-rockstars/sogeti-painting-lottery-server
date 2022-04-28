package com.sogetirockstars.sogetipaintinglotteryserver.service;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Painting;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.PaintingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * ContestantService
 */
@Service
public class PaintingService {
    private final PaintingRepository repository;

    @Autowired
    public PaintingService(PaintingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Painting> getAllPaintings() {
        return repository.findAll();
    }

    @GetMapping
    public Painting getPainting(Long id) {
        return repository.findById(id).get();
    }

    public Painting save(Painting painting) {
        return repository.save(painting);
    }

    @Transactional
    public void updatePainting(Long paintingId, String artist, String description, String picture_url,
                               String title) {
        Painting painting = repository.findById(paintingId).orElseThrow(() -> new
                IllegalStateException("painting with id " + paintingId + " does not exist"));
        painting.setArtist(artist);
        painting.setDescription(description);
        painting.setUrl(picture_url);
        painting.setTitle(title);
    }
}
