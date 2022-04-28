package com.sogetirockstars.sogetipaintinglotteryserver.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        String srcBase = "mock-photos";
        String dstBase = "src/main/resources/cache";

        List<LotteryItem> items = List.of(
            new LotteryItem(1, dstBase + "/0.jpg", "Guernica", "Picasso", "10x10m", "wood", "999.999.999kr", "oil"),
            new LotteryItem(2, dstBase + "/1.jpg", "The burning giraffe", "Dali", "10x10m", "wood", "999.999.999kr", "oil"),
            new LotteryItem(3, dstBase + "/2.jpg", "View of Toledo", "El Greco", "10x10m", "wood", "999.999.999kr", "oil"),
            new LotteryItem(4, dstBase + "/3.jpg", "David", "Michael Angelo", "10x10m", "wood", "999.999.999kr", "oil"),
            new LotteryItem(5, dstBase + "/4.jpg", "Mona lisa", "Da vinci", "10x10m", "wood", "999.999.999kr", "oil")
        );

        for ( int i=0; i<items.size(); i++ ){
            Path src = Paths.get(srcBase + "/" + i + ".jpg" );
            Path dst = Paths.get(dstBase + "/" + i + ".jpg" );
            Files.copy(src, dst);
        }

        // Files.copy(src, dst);
        return (String[] args) -> { repo.saveAll( items ); };
    }
}
