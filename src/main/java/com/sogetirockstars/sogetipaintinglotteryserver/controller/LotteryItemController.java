package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.service.LotteryItemService;
import com.sogetirockstars.sogetipaintinglotteryserver.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

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
    private final LotteryItemService service;
    private final FileUploadUtil fileUploadUtil;

    @Autowired
    public LotteryItemController(LotteryItemService service) throws IOException {
        this.service = service;
        this.fileUploadUtil = new FileUploadUtil("src/main/resources/cache/photos");
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<LotteryItem>> getAllPaintings() {
        List<LotteryItem> lotteryItems = service.getAllPaintings();

        ResponseEntity<List<LotteryItem>> resp = ResponseEntity.ok().body(lotteryItems);

        return resp;
        // return service.getAllPaintings();
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<LotteryItem> getPainting(@PathVariable("id") Long id) {
        LotteryItem lotteryItem = service.getPainting(id);
        System.out.println("Sending painting with id " + lotteryItem.getId());
        ResponseEntity<LotteryItem> resp = ResponseEntity.ok().body(lotteryItem);

        return resp;
    }

    // Todo: Which one to use??
    @GetMapping(value = "/get-image-raw/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPaintingImageRaw(@PathVariable Long id) throws IOException {
        LotteryItem reqLotteryItem = service.getPainting(id);
        ClassPathResource imgFile = new ClassPathResource(reqLotteryItem.getPictureUrl());
        return StreamUtils.copyToByteArray(imgFile.getInputStream());
    }

    @GetMapping(value = "/get-image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPaintingImageRaw(HttpServletResponse response, @PathVariable Long id) throws IOException {
        LotteryItem reqLotteryItem = service.getPainting(id);
        ClassPathResource imgFile = new ClassPathResource(reqLotteryItem.getPictureUrl());
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    }

    @GetMapping(value = "/get-image2/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getPaintingImage2(@PathVariable Long id) throws IOException {
        LotteryItem reqLotteryItem = service.getPainting(id);
        ClassPathResource imgFile = new ClassPathResource(reqLotteryItem.getPictureUrl());

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }
    // /Todo: Which one to use??

    @PostMapping("/upload")
    public RedirectView uploadPicture(@RequestPart("image") MultipartFile multipartFile, @RequestParam Long id) throws IOException {
        LotteryItem lotteryItem = service.getPainting(id);

        String filename = lotteryItem.getId() + ".jpg";

        String localUrl = fileUploadUtil.saveFile(filename, multipartFile);

        lotteryItem.setPictureUrl(localUrl);
        service.save(lotteryItem);

        return new RedirectView("getAll", true);
    }

    @PutMapping(value = "/add")
    public ResponseEntity<LotteryItem> addPainting(@RequestParam(defaultValue = "") String title,
                                                   @RequestParam(defaultValue = "") String artist,
                                                   @RequestParam(defaultValue = "") String description

    ) {
        LotteryItem lotteryItem = service.save(new LotteryItem(title, artist)); // Save new painting on sql
        // so we get an id from sql.
        System.out.println("added painting " + lotteryItem.getItemName());
        return ResponseEntity.ok().body(lotteryItem);
    }

    @PutMapping(value = "/addNew")
    public ResponseEntity<LotteryItem> addPainting(LotteryItem lotteryItem) {
        service.save(lotteryItem); // Save new painting on sql so we get an id from sql.
        System.out.println("added painting " + lotteryItem.getItemName() + " id: " + lotteryItem.getId());
        return ResponseEntity.ok().body(lotteryItem);
    }

    @PutMapping(path = "{paintingId}")
    public void updatePainting(@PathVariable("paintingId") Long paintingId,
                               @RequestParam(required = false) String artist,
                               @RequestParam(required = false) String description,
                               @RequestParam(required = false) String picture_url,
                               @RequestParam(required = false) String title) {
        service.updatePainting(paintingId, artist, description, picture_url);
    }

}

