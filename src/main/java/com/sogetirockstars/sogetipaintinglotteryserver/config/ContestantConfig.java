package com.sogetirockstars.sogetipaintinglotteryserver.config;

import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.ContestantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * ContestantConfig
 */
@Configuration
public class ContestantConfig {
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
}
