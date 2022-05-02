package com.sogetirockstars.sogetipaintinglotteryserver.controller;

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
@RequestMapping(path = "/api/v1/item") // JQ: ska vi köra på versioning till Api? Är detta bra?
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

    // JQ: är ResponseEntity<LotteryItem> bra??
    @GetMapping(value = "/{id}")
    public ResponseEntity<LotteryItem> getPainting(@PathVariable("id") Long id) {
        LotteryItem lotteryItem = lotteryItemService.getItem(id);
        System.out.println("Sending painting with id " + lotteryItem.getId());
        ResponseEntity<LotteryItem> resp = ResponseEntity.ok().body(lotteryItem);

        return resp;
    }

    // JQ: Vilken av de följande tre ska vi använda?? Spelar det nåpgon roll? Alla funkar...
    // bör det vara item/{id}/image eller item/image/{id} ?
    // - Ska det vara {id}/image eller image/{id}
    @GetMapping(value = "/image-raw/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPaintingImageRaw(@PathVariable Long id) throws IOException {
        LotteryItem reqLotteryItem = lotteryItemService.getItem(id);
        ClassPathResource imgFile = new ClassPathResource(reqLotteryItem.getPictureUrl());
        return StreamUtils.copyToByteArray(imgFile.getInputStream());
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPaintingImageRaw(HttpServletResponse response, @PathVariable Long id) throws IOException {
        LotteryItem reqLotteryItem = lotteryItemService.getItem(id);
        ClassPathResource imgFile = new ClassPathResource(reqLotteryItem.getPictureUrl());
        System.out.println("Serving file at: " + imgFile.getPath() );

        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    }

    @GetMapping(value = "/image2/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
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

    // JQ: Ska vi explicera i url:en vad det görs eller ska det bestämmas av vilken HTTP method man använder?
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id) {
        boolean isRemoved = lotteryItemService.delete(id);

        if (!isRemoved) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // JQ: Ska vi updatera objekt genom att skicka värden i HTTP body eller ska vi uppdatera dem genom HTTP parameters?
    @PutMapping(path = "update")
    public ResponseEntity<LotteryItem> update(@RequestBody LotteryItem item) {
        if ( ! lotteryItemService.existsById(item.getId() ) )
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // JQ: Är det här bra felhantering?
        return new ResponseEntity<>(lotteryItemService.update(item), HttpStatus.OK);
    }

    // @PutMapping(path = "update-fields")
    // public void update(
    // @PathVariable("id") Long id,
    // @RequestParam(required = false) Integer lotteryId,
    // @RequestParam(required = false) String pictureUrl,
    // @RequestParam(required = false) String itemName,
    // @RequestParam(required = false) String artistName,
    // @RequestParam(required = false) String size,
    // @RequestParam(required = false) String frameDescription,
    // @RequestParam(required = false) String value,
    // @RequestParam(required = false) String technique
    // ){
    // }
    // lotteryItemService.updatePainting(id, lotteryId, pictureUrl, itemName, artistName, size, frameDescription, value, technique);
}

