package com.sogetirockstars.sogetipaintinglotteryserver.business.painting;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping(value="/getAll")
    public ResponseEntity<List<Painting>> getAllPaintings() {
        List<Painting> paintings = service.getAllPaintings();

        ResponseEntity<List<Painting>> resp = ResponseEntity.ok().body( paintings );

        return resp;
        // return service.getAllPaintings();
    }

    @GetMapping(value="/get/{id}")
    public ResponseEntity<Painting> getPainting(@PathVariable("id") Long id){
        Painting painting = service.getPainting(id);
        System.out.println("Sending painting with id " + painting.getId() );
        ResponseEntity<Painting> resp = ResponseEntity.ok().body( painting );

        return resp;
    }

    @GetMapping(value="/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getPaintingImage(@RequestParam Long id){
        Painting reqPainting = service.getPainting(id);
        try {
			return Files.readAllBytes( Path.of( reqPainting.getUrl() ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
        return new byte[0];
    }

    @PostMapping("/upload")
    public RedirectView uploadPicture(@RequestPart("image") MultipartFile multipartFile, @RequestParam Long id) throws IOException {
        // Painting painting = service.save(new Painting("hello world")); // Save new painting on sql so we get an id from sql.
        Painting painting = service.getPainting( id );

        String filename   = painting.getId() + ".jpg";

        // 15.jpg => /home/len/dev/sogeti-rockstars/hello-spring/src/main/resources/cache/photos/15.jpg
        String localUrl = fileUploadUtil.saveFile(filename, multipartFile);

        painting.setUrl( localUrl );
        service.save(painting);

        return new RedirectView("getAll", true);
    }

    @PutMapping(value="/add")
    public ResponseEntity<Painting> addPainting(
        @RequestParam(defaultValue="")                   String title,
        @RequestParam(defaultValue="")                   String artist,
        @RequestParam(defaultValue="")                   String description

    ) {
        Painting painting = service.save(new Painting(title, artist, description)); // Save new painting on sql so we get an id from sql.
        System.out.println("added painting " + painting.getTitle() );
        return ResponseEntity.ok().body( painting );
    }

    @PutMapping(value="/addNew")
    public ResponseEntity<Painting> addPainting( Painting painting) {
        service.save(painting); // Save new painting on sql so we get an id from sql.
        System.out.println("added painting " + painting.getTitle() + " id: " + painting.getId() );
        return ResponseEntity.ok().body( painting );
    }

    @PutMapping(path = "{paintingId}")
    public void updatePainting(
            @PathVariable("paintingId") Long paintingId,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String picture_url,
            @RequestParam(required = false) String title){
        service.updatePainting(paintingId, artist, description, picture_url, title);
    }

}

class FileUploadUtil {
    private final Path uploadPath;

    public FileUploadUtil(String uploadPath) throws IOException{
        try {
            this.uploadPath=Paths.get(uploadPath);;
            if (!Files.exists(this.uploadPath))
                Files.createDirectories(this.uploadPath);
        } catch (IOException ioe) {
            throw new IOException("Could not create upload directory: " + uploadPath, ioe);
        }
    }

    public String saveFile(String filename, MultipartFile multipartFile) throws IOException {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + filename, ioe);
        }
    }
}
