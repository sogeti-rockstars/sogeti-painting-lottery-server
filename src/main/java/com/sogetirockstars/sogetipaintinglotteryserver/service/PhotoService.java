package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.PhotoMissingException;
import com.sogetirockstars.sogetipaintinglotteryserver.exception.PhotoWriteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoService.class);
    @Value("${photobucket.path}")
    private String configPhotosPath;

    private String resourcePath = "src/main/resources/photo-service/";
    private String noImagePath = resourcePath + "No-image-available.png";

    private Path photosPath;

    public PhotoService() {
    }

    public void savePhoto(Long id, InputStream photoInStream) throws PhotoWriteException {
        try {
            LOGGER.info("savePhoto:");
            ensurePathExists();
            Path filePath = photosPath.resolve(id.toString().trim());
            Files.copy(photoInStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("filePath: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PhotoWriteException("Previously existing path failed being written to. Contact your system administrator.");
        }
    }

    public InputStream getPhoto(Long id) throws PhotoMissingException {
        try {
            ensurePathExists();
            return new FileInputStream(photosPath.resolve(id.toString()).toFile());
        } catch (FileNotFoundException e) {
            return getPlaceholderPhoto();
        } catch (IOException | PhotoWriteException e) {
            e.printStackTrace();
            throw new PhotoMissingException("Photo directory does not exist.");
        }
    }

    private InputStream getPlaceholderPhoto() throws PhotoMissingException {
        Path placeholderPath = Paths.get(noImagePath).toAbsolutePath();
        try {
            return new FileInputStream(placeholderPath.toFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new PhotoMissingException("Photo directory does not exist.");
        }
    }

    private void ensurePathExists() throws IOException, PhotoWriteException {
        if (photosPath != null)
            return;
        if (configPhotosPath == null)
            throw new IOException("No path configured for saving photos!");

        if (configPhotosPath.equals("###TEMPDIR###")) // For production enviroment
            configPhotosPath = System.getProperty("java.io.tmpdir") + "/sogeti-lottery";

        photosPath = Paths.get(configPhotosPath);

        try {
            if (!Files.exists(photosPath))
                Files.createDirectories(photosPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PhotoWriteException("Failed to create directory " + configPhotosPath + ".");
        }
    }

}
