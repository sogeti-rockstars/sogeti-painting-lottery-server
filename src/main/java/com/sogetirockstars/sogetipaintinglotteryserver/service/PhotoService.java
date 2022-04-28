package com.sogetirockstars.sogetipaintinglotteryserver.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PhotoService {
    private final static Path uploadPath = Paths.get("src/main/resources/cache/photos");

    public PhotoService() throws IOException {
        try {
            if (!Files.exists(uploadPath))
                Files.createDirectories(uploadPath);
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
