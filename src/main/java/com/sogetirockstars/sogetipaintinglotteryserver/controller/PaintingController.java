package com.sogetirockstars.sogetipaintinglotteryserver.controller;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Painting;
import com.sogetirockstars.sogetipaintinglotteryserver.service.PaintingService;
import com.sogetirockstars.sogetipaintinglotteryserver.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * PaintingController:
 */
@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/v1/painting")
public class PaintingController {
    private final PaintingService service;
    private final FileUploadUtil fileUploadUtil;

    @Autowired
    public PaintingController(PaintingService service) throws IOException {
        this.service = service;
        this.fileUploadUtil = new FileUploadUtil("src/main/resources/cache/photos");
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Painting>> getAllPaintings() {
        List<Painting> paintings = service.getAllPaintings();

        ResponseEntity<List<Painting>> resp = ResponseEntity.ok().body(paintings);

        return resp;
        // return service.getAllPaintings();
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Painting> getPainting(@PathVariable("id") Long id) {
        Painting painting = service.getPainting(id);
        System.out.println("Sending painting with id " + painting.getId());
        ResponseEntity<Painting> resp = ResponseEntity.ok().body(painting);

        return resp;
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPaintingImage(@RequestParam Long id) {
        Painting reqPainting = service.getPainting(id);
        try {
            return Files.readAllBytes(Path.of(reqPainting.getPictureUrl()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @PostMapping("/upload")
    public RedirectView uploadPicture(@RequestPart("image") MultipartFile multipartFile, @RequestParam Long id) throws IOException {
        Painting painting = service.getPainting(id);

        String filename = painting.getId() + ".jpg";

        String localUrl = fileUploadUtil.saveFile(filename, multipartFile);

        painting.setPictureUrl(localUrl);
        service.save(painting);

        return new RedirectView("getAll", true);
    }

    @PutMapping(value = "/add")
    public ResponseEntity<Painting> addPainting(@RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String artist,
            @RequestParam(defaultValue = "") String description

    ) {
        Painting painting = service.save(new Painting(title, artist)); // Save new painting on sql
                                                                       // so we get an id from sql.
        System.out.println("added painting " + painting.getItemName());
        return ResponseEntity.ok().body(painting);
    }

    @PutMapping(value = "/addNew")
    public ResponseEntity<Painting> addPainting(Painting painting) {
        service.save(painting); // Save new painting on sql so we get an id from sql.
        System.out.println("added painting " + painting.getItemName() + " id: " + painting.getId());
        return ResponseEntity.ok().body(painting);
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

