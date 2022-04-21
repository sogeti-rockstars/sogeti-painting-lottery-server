package com.sogetirockstars.sogetipaintinglotteryserver.business.contestant;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ContestantConfig
 */
@Configuration
public class ContestantConfig {
    @Bean
    CommandLineRunner cmdLineRunnerContestant(ContestantRepository repo){
        return ( String[] args ) -> {
            repo.saveAll(
                List.of(
                      new Contestant("Alice Alisson",       "123L Streetenus")
                    , new Contestant("Bob Bobsson",         "123L Streetenus")
                    , new Contestant("Charlie Charlston",   "123L Streetenus")
                    , new Contestant("Dorothea Dotesson",   "123L Streetenus")
                )
            );
        };
    }
}
