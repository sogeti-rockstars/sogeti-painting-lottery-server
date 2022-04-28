package com.sogetirockstars.sogetipaintinglotteryserver.config;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.ContestantRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    CommandLineRunner cmdLineRunnerArtItem(LotteryItemRepository repo) {
        return (String[] args) -> {
            repo.saveAll(List.of(
                            new LotteryItem(1, "mock-photos/0.jpg", "Guernica", "Picasso", "10x10m", "wood", "999.999.999kr", "oil"),
                            new LotteryItem(1, "mock-photos/1.jpg", "The burning giraffe", "Dali", "10x10m", "wood", "999.999.999kr", "oil"),
                            new LotteryItem(1, "mock-photos/2.jpg", "View of Toledo", "El Greco", "10x10m", "wood", "999.999.999kr", "oil"),
                            new LotteryItem(1, "mock-photos/3.jpg", "David", "Michael Angelo", "10x10m", "wood", "999.999.999kr", "oil"),
                            new LotteryItem(1, "mock-photos/4.jpg", "Mona lisa", "Da vinci", "10x10m", "wood", "999.999.999kr", "oil")
                    )
            );
        };
    }
}
