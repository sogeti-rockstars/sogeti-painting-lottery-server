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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    @Value("${photobucket.path}")
    private String configPhotosPath;

    private Path photosPath;

    public PhotoService() {
    }

    private void ensurePathExists() throws IOException, PhotoWriteException {
        if (photosPath != null)
            return;
        if (configPhotosPath == null)
            throw new IOException("No path configured for saving photos!");

        photosPath = Paths.get(configPhotosPath);
        try {
            if (!Files.exists(photosPath))
                Files.createDirectories(photosPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PhotoWriteException("Failed to create directory " + configPhotosPath + ".");
        }
    }

    public void savePhoto(Long id, InputStream photoInStream) throws PhotoWriteException {
        try {
            ensurePathExists();
            Path filePath = photosPath.resolve(id.toString().trim());
            System.out.println("Saving photo to " + filePath.toString());
            Files.copy(photoInStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(this.getClass().toString() + "Previously existing path failed being written to.");
        }
    }

    public InputStream getPhoto(Long id) throws PhotoMissingException {
        try {
            return new FileInputStream(photosPath.resolve(id.toString()).toFile());
        } catch (FileNotFoundException e) {
            throw new PhotoMissingException("Photo for item with id " + id + " does not exist");
        }
    }
}
