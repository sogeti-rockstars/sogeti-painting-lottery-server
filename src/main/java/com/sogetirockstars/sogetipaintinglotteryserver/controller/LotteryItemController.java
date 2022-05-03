package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import java.io.IOException;
import java.util.List;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.PhotoMissingException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.service.LotteryItemService;
import com.sogetirockstars.sogetipaintinglotteryserver.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * PaintingController:
 */
@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/v1/item")
public class LotteryItemController {
    private final LotteryItemService lotteryItemService;
    private final PhotoService photoService;

    @Autowired
    public LotteryItemController(LotteryItemService service, PhotoService photoService) throws IOException {
        this.lotteryItemService = service;
        this.photoService = photoService;
    }

    /**
     * Returns all lottery items
     */
    @GetMapping
    public ResponseEntity<List<LotteryItem>> getAllItems() {
        List<LotteryItem> lotteryItems = lotteryItemService.getAll();
        return ResponseEntity.ok().body(lotteryItems);
    }

    /**
     * Get item with id /{id}
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            System.out.println("Sending painting with id " + id);
            LotteryItem item = lotteryItemService.getItem(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete item with id /{id}
     */
    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(lotteryItemService.delete(id), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Add new item
     */
    @PostMapping
    public ResponseEntity<LotteryItem> addNew(@RequestBody LotteryItem lotteryItem) {
        System.out.println("Adding painting " + lotteryItem.getItemName() + " id: " + lotteryItem.getId());
        lotteryItem.setId(null);
        return ResponseEntity.ok().body(lotteryItemService.add(lotteryItem));
    }

    /**
     * Update item with id /{id}
     */
    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LotteryItem item) {
        try {
            item.setId(id);
            return new ResponseEntity<>(lotteryItemService.update(item), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable Long id) throws IOException {
        try {
            lotteryItemService.getItem(id); // Ensure the requested resource exists.
            return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource( photoService.getPhoto(id) ) );
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PhotoMissingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
    }

    @PutMapping("/update-image/{id}")
    public ResponseEntity<?> uploadPicture(@PathVariable Long id, @RequestPart("image") MultipartFile multipartFile) throws IOException {
        try {
            LotteryItem item = lotteryItemService.getItem(id); // Ensure the requested resource exists.
            photoService.savePhoto(id, multipartFile.getInputStream() );
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

