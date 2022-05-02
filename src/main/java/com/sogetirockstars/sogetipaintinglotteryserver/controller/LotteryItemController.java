package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.IdException;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.service.LotteryItemService;
import com.sogetirockstars.sogetipaintinglotteryserver.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
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
@RequestMapping(path = "/api/v1/item") // JQ: ska vi köra på versioning till Api? Är detta bra?
public class LotteryItemController {
    private final LotteryItemService lotteryItemService;
    private final PhotoService photoService;

    @Autowired
    public LotteryItemController(LotteryItemService service, PhotoService photoService) throws IOException {
        this.lotteryItemService = service;
        this.photoService = photoService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<LotteryItem>> getAllPaintings() {
        List<LotteryItem> lotteryItems = lotteryItemService.getAllPaintings();
        return ResponseEntity.ok().body(lotteryItems);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LotteryItem> getPainting(@PathVariable("id") Long id) {
        LotteryItem lotteryItem = lotteryItemService.getItem(id);
        System.out.println("Sending painting with id " + lotteryItem.getId());
        ResponseEntity<LotteryItem> resp = ResponseEntity.ok().body(lotteryItem);

        return resp;
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getPaintingImage2(@PathVariable Long id) throws IOException {
        LotteryItem reqLotteryItem = lotteryItemService.getItem(id);
        ClassPathResource imgFile = new ClassPathResource(reqLotteryItem.getPictureUrl());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }

    @PutMapping("/update-image/{id}")
    public ResponseEntity<InputStreamResource> uploadPicture(@PathVariable Long id, @RequestPart("image") MultipartFile multipartFile) throws IOException {
        LotteryItem lotteryItem = lotteryItemService.getItem(id);
        String filename = lotteryItem.getId() + ".jpg";
        String localUrl = photoService.saveFile(filename, multipartFile);

        lotteryItem.setPictureUrl(localUrl);
        lotteryItemService.save(lotteryItem);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/add-new")
    public ResponseEntity<LotteryItem> addNew(@RequestBody LotteryItem lotteryItem) {
        System.out.println("added painting " + lotteryItem.getItemName() + " id: " + lotteryItem.getId());
        return ResponseEntity.ok().body(lotteryItemService.add(lotteryItem));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id) {
        boolean isRemoved = lotteryItemService.delete(id);

        if (!isRemoved) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping(path = "update")
    public ResponseEntity<?> update(@RequestBody LotteryItem item) {
        try {
            return new ResponseEntity<>(lotteryItemService.update(item), HttpStatus.OK);
        } catch (IdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

