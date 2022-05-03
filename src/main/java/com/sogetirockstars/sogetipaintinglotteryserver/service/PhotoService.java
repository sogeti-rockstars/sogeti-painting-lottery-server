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
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    private String cachePath = "cache/photos";
    private Path cache;

    public PhotoService() {
        String cacheFullPath = this.getClass().getClassLoader().getResource("").getPath()+cachePath;
        try {
            cache = Paths.get(cacheFullPath);
            System.err.println("Cache dir: "+cacheFullPath);

            if (!Files.exists(cache))
                Files.createDirectories(cache);
        } catch (IOException e){
            e.printStackTrace(); // This should never happen on a normally functioning machine, so let's discover it quickly if it were.
            System.err.println("Failed to create photo directory " + cacheFullPath + ".");
            System.exit(1);
        }
    }

    public void savePhoto(Long id, InputStream photoInStream) {
        Path filePath = cache.resolve(id.toString());
        System.out.println("Saving photo to "+ filePath.toString() );
        try {
            Files.copy(photoInStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();  // This should never happen on a normally functioning machine, so let's discover it quickly if it were.
                                  // Maybe if we run out of diskspace?  // JQ: Vad vore bra error handling??
            System.err.println("Failed to save photo " + filePath + ".");
		}
    }

    public InputStream getPhoto(Long id) throws PhotoMissingException{
        try {
			return new FileInputStream(cache.resolve(id.toString()).toFile());
		} catch (FileNotFoundException e) {
            throw new PhotoMissingException( "Photo for item with id " + id + " does not exist");
		}
    }
}
