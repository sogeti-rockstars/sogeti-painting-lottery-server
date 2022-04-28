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
