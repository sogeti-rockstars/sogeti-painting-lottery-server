package com.sogetirockstars.sogetipaintinglotteryserver.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    private final Path uploadPath;

    public FileUploadUtil(String uploadPath) throws IOException {
        try {
            this.uploadPath = Paths.get(uploadPath);
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
