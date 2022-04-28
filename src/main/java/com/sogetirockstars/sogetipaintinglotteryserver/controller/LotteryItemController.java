package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.service.LotteryItemService;
import com.sogetirockstars.sogetipaintinglotteryserver.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * PaintingController:
 */
@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/v1/painting")
public class LotteryItemController {
    private final LotteryItemService lotteryItemService;
    private final PhotoService photoService;

    @Autowired
    public LotteryItemController(LotteryItemService service, PhotoService photoService) throws IOException {
        this.lotteryItemService = service;
        this.photoService = photoService;
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<List<LotteryItem>> getAllPaintings() {
        List<LotteryItem> lotteryItems = lotteryItemService.getAllPaintings();
        return ResponseEntity.ok().body(lotteryItems);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<LotteryItem> getPainting(@PathVariable("id") Long id) {
        LotteryItem lotteryItem = lotteryItemService.getPainting(id);
        System.out.println("Sending painting with id " + lotteryItem.getId());
        ResponseEntity<LotteryItem> resp = ResponseEntity.ok().body(lotteryItem);

        return resp;
    }

    // Todo: Which one to use??
    @GetMapping(value = "/get-image-raw/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPaintingImageRaw(@PathVariable Long id) throws IOException {
        LotteryItem reqLotteryItem = lotteryItemService.getPainting(id);
        ClassPathResource imgFile = new ClassPathResource(reqLotteryItem.getPictureUrl());
        return StreamUtils.copyToByteArray(imgFile.getInputStream());
    }

    @GetMapping(value = "/get-image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPaintingImageRaw(HttpServletResponse response, @PathVariable Long id) throws IOException {
        LotteryItem reqLotteryItem = lotteryItemService.getPainting(id);
        ClassPathResource imgFile = new ClassPathResource(reqLotteryItem.getPictureUrl());
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    }

    @GetMapping(value = "/get-image2/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getPaintingImage2(@PathVariable Long id) throws IOException {
        LotteryItem reqLotteryItem = lotteryItemService.getPainting(id);
        ClassPathResource imgFile = new ClassPathResource(reqLotteryItem.getPictureUrl());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }
    // /Todo: Which one to use??

    @PutMapping("/update-image/{id}")
    public ResponseEntity<InputStreamResource> uploadPicture(@PathVariable Long id, @RequestPart("image") MultipartFile multipartFile) throws IOException {
        LotteryItem lotteryItem = lotteryItemService.getPainting(id);
        String filename = lotteryItem.getId() + ".jpg";
        String localUrl = photoService.saveFile(filename, multipartFile);

        lotteryItem.setPictureUrl(localUrl);
        lotteryItemService.save(lotteryItem);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/add") // Todo: Fix/ensure it's working, I don't think it is!
    public ResponseEntity<LotteryItem> addPainting(LotteryItem lotteryItem) {
        System.out.println("added painting " + lotteryItem.getItemName() + " id: " + lotteryItem.getId());
        lotteryItemService.save(lotteryItem);
        return ResponseEntity.ok().body(lotteryItem);
    }
}

