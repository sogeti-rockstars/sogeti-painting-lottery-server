package com.sogetirockstars.sogetipaintinglotteryserver.config;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Painting;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.PaintingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * ContestantConfig
 */
@Configuration
public class PaintingConfig {
    @Bean
    CommandLineRunner cmdLineRunnerPainting(PaintingRepository repo) {

        // int lotteryId, String pictureUrl, String itemName, String artistName, String size, String
        // frameDescription, String value, String technique
        return (String[] args) -> {
            repo.saveAll(List.of(
                    new Painting(1, "", "Guernica", "Picasso", "10x10m", "wood", "999.999.999kr", "oil"),
                    new Painting(1, "", "The burning giraffe", "Dali", "10x10m", "wood", "999.999.999kr", "oil"),
                    new Painting(1, "", "View of Toledo", "El Greco", "10x10m", "wood", "999.999.999kr", "oil"),
                    new Painting(1, "", "David", "Michael Angelo", "10x10m", "wood", "999.999.999kr", "oil"),
                    new Painting(1, "", "Mona lisa", "Da vinci", "10x10m", "wood", "999.999.999kr", "oil")
                )
            );
        };
    }
}
