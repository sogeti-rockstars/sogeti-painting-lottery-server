package com.sogetirockstars.sogetipaintinglotteryserver.config;

import com.sogetirockstars.sogetipaintinglotteryserver.model.*;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.*;
import com.sogetirockstars.sogetipaintinglotteryserver.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class MockDataConfig {

    private String mockPhotosSrc = "src/main/resources/mock-photos";
    private final PhotoService photoService;
    private List<LotteryItem> lotteryItems;
    private List<Address> addresses;
    private List<Contestant> contestants;
    private List<Lottery> lotteries;
    private List<Winner> winners;

    @Autowired
    public MockDataConfig(PhotoService photoService) {
        this.photoService = photoService;
    }


    @Bean
    CommandLineRunner mockData(AddressRepository addrRepo, ContestantRepository contRepo, LotteryItemRepository lottRepo, LotteryRepository lotteryRepo, WinnerRepository winnerRepo) {
        return (String[] args) -> {
            lotteries = fakeLotteries();
            lotteryItems = fakeLotteryItems(lotteries);
            addresses = fakeAddresses();
            contestants = fakeContestants(addresses);
            winners = fakeWinners(contestants, lotteryItems, lotteries);

            lotteryRepo.saveAllAndFlush(lotteries);
            lottRepo.saveAllAndFlush(lotteryItems).stream().forEach(i -> updatePictureUrl(i, lottRepo));
            addrRepo.saveAllAndFlush(addresses);
            contRepo.saveAllAndFlush(contestants);
            winnerRepo.saveAllAndFlush(winners);
        };
    }

    private List<Winner> fakeWinners(List<Contestant> contestants, List<LotteryItem> lotteryItems, List<Lottery> lotterys) {
        List<Winner> winners = List.of(
                new Winner(),
                new Winner(),
                new Winner()
        );
        for (int i = 0; i < winners.size(); i++) {
            winners.get(i).setLottery(lotterys.get(i));
            winners.get(i).setLotteryItem(lotteryItems.get(i));
            winners.get(i).setContestant(contestants.get(i));
            winners.get(i).setPlacement(i);
        }
        return winners;
    }

    private List<Lottery> fakeLotteries() {
        List<Lottery> lotteries = List.of(
                new Lottery(),
                new Lottery(),
                new Lottery()
        );
        for (int i = 0; i < lotteries.size(); i++) {
            lotteries.get(i).setTitle("test" + i);
        }
        return lotteries;
    }

    private List<Contestant> fakeContestants(List<Address> fakeAddresses) {
        List<Contestant> contestants = List.of(
                new Contestant("Alice Alisson", null, "00001", "070 - 0001 123", "email01@example.com"),
                new Contestant("Bob Bobsson", null, "00002", "070 - 0001 124", "email02@example.com"),
                new Contestant("Charlie Charlston", null, "00003", "070 - 0001 125", "email03@example.com"),
                new Contestant("Dorothea Dotesson", null, "00004", "070 - 0001 126", "email04@example.com"),
                new Contestant("Dorothea Abc", null, "00005", "070 - 0001 127", "email05@example.com"),
                new Contestant("Dorothea Foo", null, "00006", "070 - 0001 128", "email06@example.com"),
                new Contestant("Dorothea Bar", null, "00007", "070 - 0001 129", "email07@example.com"),
                new Contestant("Dorothea Oloffson", null, "00008", "070 - 0001 130", "email08@example.com")
        );

        for (int i = 0; i < contestants.size(); i++)
            contestants.get(i).setAddress(fakeAddresses.get(i % fakeAddresses.size()));
        return contestants;
    }

    private List<LotteryItem> fakeLotteryItems(List<Lottery> lotteries) {
        return List.of(
                new LotteryItem("", "Guernica", "Picasso", "10x10m", "wood", "999.999.999kr", "oil", lotteries.get(1)),
                new LotteryItem("", "The burning giraffe", "Dali", "10x10m", "wood", "999.999.999kr", "oil", lotteries.get(0)),
                new LotteryItem("", "View of Toledo", "El Greco", "10x10m", "wood", "999.999.999kr", "oil", lotteries.get(2)),
                new LotteryItem("", "David", "Michael Angelo", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "Mona lisa", "Da vinci", "10x10m", "wood", "999.999.999kr", "oil")
        );
    }

    private List<Address> fakeAddresses() {
        return List.of(
                new Address("Streetenus", "1b", "00001", "Göteborg"),
                new Address("Gatilius", "2a", "00002", "Sundsvall"),
                new Address("Gatunamn", "3c", "00003", "Stockholm"),
                new Address("Storgatan", "4f", "00004", "Malmö"),
                new Address("Lillgatan", "5b", "00005", "Umeå"),
                new Address("Högatan", "6e", "00006", "Borås"),
                new Address("Gullgatan", "7d", "00007", "Jönköping"),
                new Address("Fingatan", "8g", "00008", "Köping"),
                new Address("Sogetigatan", "55A", "123 45", "Haparanda"),
                new Address("Exempelvägen", "55A", "123 45", "Haparanda"),
                new Address("Testgatan", "55A", "123 45", "Haparanda")
        );
    }

    /*
     * Called by Spring when running the CommandLineRunner above. We need to save the items to the
     * database to get a valid id from SQL so we copy the mockdata-photos after we've gotten an id...
     */
    private int lastMockId = 0;

    private void updatePictureUrl(LotteryItem item, LotteryItemRepository repo) {
        try {
            Path src = Paths.get(mockPhotosSrc + "/" + lastMockId++ + ".jpg");
            photoService.savePhoto(item.getId(), new FileInputStream(src.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Mock data failed to be created");
            System.exit(1);
        }
    }
}
