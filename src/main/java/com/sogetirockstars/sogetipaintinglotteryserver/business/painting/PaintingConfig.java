package com.sogetirockstars.sogetipaintinglotteryserver.business.painting;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ContestantConfig
 */
@Configuration
public class PaintingConfig {
    @Bean
    CommandLineRunner cmdLineRunnerPainting(PaintingRepository repo){
        return (String[] args) -> {
            repo.saveAll(
                List.of(
                      new Painting("Picasso")
                    , new Painting("Dali")
                    , new Painting("MichaelAngelo")
                    , new Painting("El Greco")
                )
            );
        };
    }
}
