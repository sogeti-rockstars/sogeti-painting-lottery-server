package com.sogetirockstars.sogetipaintinglotteryserver.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.ContestantRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockDataConfig {
    private String mockPhotosSrc = "src/main/resources/mock-photos";
    private String photosDst = "src/main/resources/cache/photos";

    @Bean
    CommandLineRunner cmdLineRunnerContestant(ContestantRepository repo) {
        return (String[] args) -> {
            repo.saveAll(
                    List.of(
                            new Contestant("Alice Alisson", "123L Streetenus")
                            , new Contestant("Bob Bobsson", "123L Streetenus")
                            , new Contestant("Charlie Charlston", "123L Streetenus")
                            , new Contestant("Dorothea Dotesson", "123L Streetenus")
                    )
            );
        };
    }

    @Bean
    CommandLineRunner cmdLineRunnerArtItem(LotteryItemRepository repo) throws IOException {
        List<LotteryItem> items = List.of(
            new LotteryItem(1, "", "Guernica", "Picasso", "10x10m", "wood", "999.999.999kr", "oil"),
            new LotteryItem(2, "", "The burning giraffe", "Dali", "10x10m", "wood", "999.999.999kr", "oil"),
            new LotteryItem(3, "", "View of Toledo", "El Greco", "10x10m", "wood", "999.999.999kr", "oil"),
            new LotteryItem(4, "", "David", "Michael Angelo", "10x10m", "wood", "999.999.999kr", "oil"),
            new LotteryItem(5, "", "Mona lisa", "Da vinci", "10x10m", "wood", "999.999.999kr", "oil")
        );

        return (String[] args) -> { repo.saveAll( items ).stream().forEach(i-> updatePictureUrl( i, repo ) ); };
    }

    /*
     * Called by Spring when running the CommandLineRunner above.
     * We need to save the items to the database to get a valid id from SQL so we copy the mockdata-photos after we've
     * gotten an id...
     */
    private void updatePictureUrl(LotteryItem item, LotteryItemRepository repo) {
        try {
            Path src = Paths.get(mockPhotosSrc + "/" + item.getId() + ".jpg" );
            Path dst = Paths.get(photosDst + "/" + item.getId() + ".jpg" );
            String nPath = "cache/photos/" + item.getId() + ".jpg" ;
            System.out.println( "Setting photo path:" + dst.toString() );
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
            item.setPictureUrl(nPath);
            repo.save(item);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Mock data failed to be created");
        }
    }

}
