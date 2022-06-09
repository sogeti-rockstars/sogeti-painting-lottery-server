package com.sogetirockstars.sogetipaintinglotteryserver.config;

import com.sogetirockstars.sogetipaintinglotteryserver.model.AssociationInfo;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Contestant;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Lottery;
import com.sogetirockstars.sogetipaintinglotteryserver.model.LotteryItem;
import com.sogetirockstars.sogetipaintinglotteryserver.model.Winner;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.AssociationInfoRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.ContestantRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryItemRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.LotteryRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.repository.WinnerRepository;
import com.sogetirockstars.sogetipaintinglotteryserver.service.LotteryService;
import com.sogetirockstars.sogetipaintinglotteryserver.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Configuration
public class MockDataConfig {

    private String mockPhotosSrc = "src/main/resources/mock-photos";
    private final PhotoService photoService;
    private List<Contestant> contestants;
    private List<Lottery> lotteries;
    private List<Winner> winners;

    @Autowired
    public MockDataConfig(PhotoService photoService, LotteryService lotteryService) {
        this.photoService = photoService;
    }

    @Bean
    CommandLineRunner mockData(ContestantRepository contRepo, LotteryItemRepository lottItemsRepo,
            LotteryRepository lotteryRepo, WinnerRepository winnerRepo, AssociationInfoRepository infoRepo) {
        return (String[] args) -> {
            infoRepo.save(new AssociationInfo("title", "Om föreningen"));
            infoRepo.saveAndFlush(new AssociationInfo("info",
                    "För att vara med hör av dig till Ylva på ylva@sogeti.se, Medlemskap kostar 50kr i månaden som kommer dras av din lön."
                            + "Vi är den bästa föreningen för konst och inom konstbaserade saker, utmärkt CSR och non-profit förening för "
                            + "alla människors lika värde och nytta."));

            contestants = fakeContestants();
            lotteries = fakeLotteries();
            List<LotteryItem> lotteryItems0 = fakeLotteryItems(lotteries);
            List<LotteryItem> lotteryItems1 = fakeLotteryItems(lotteries);
            List<LotteryItem> lotteryItems2 = fakeLotteryItems(lotteries).stream().limit(5).toList();
            winners = fakeWinners(contestants, lotteryItems0, lotteries);

            lotteryRepo.saveAllAndFlush(lotteries);
            lottItemsRepo.saveAllAndFlush(lotteryItems0).stream().forEach(i -> updatePictureUrl(i, lottItemsRepo));
            lottItemsRepo.saveAllAndFlush(lotteryItems1).stream().forEach(i -> updatePictureUrl(i, lottItemsRepo));
            lottItemsRepo.saveAllAndFlush(lotteryItems2).stream().forEach(i -> updatePictureUrl(i, lottItemsRepo));
            contRepo.saveAllAndFlush(contestants);
            winnerRepo.saveAllAndFlush(winners);

            contRepo.saveAllAndFlush(contestants);
            lotteries.get(0).setLotteryItems(lotteryItems0);
            lotteries.get(1).setLotteryItems(lotteryItems1);
            lotteries.get(2).setLotteryItems(lotteryItems2);
            lotteries.get(0).setWinners(winners);
            lotteryItems0.get(0).setLottery(lotteries.get(0));
            lotteryItems1.get(1).setLottery(lotteries.get(1));
            lottItemsRepo.saveAllAndFlush(lotteryItems0);
            lottItemsRepo.saveAllAndFlush(lotteryItems1);

            lotteryRepo.saveAllAndFlush(lotteries);
        };
    }

    private List<Winner> fakeWinners(List<Contestant> contestants, List<LotteryItem> lotteryItems,
            List<Lottery> lotterys) {
        List<Winner> winners = List.of(new Winner(), new Winner(), new Winner());
        for (int i = 0; i < winners.size(); i++) {
            winners.get(i).setLottery(lotterys.get(i));
            // winners.get(i).setLotteryItem(lotteryItems.get(i));
            winners.get(i).setContestant(contestants.get(i));
            winners.get(i).setPlacement(i);
        }
        return winners;
    }

    private List<Lottery> fakeLotteries() {
        return List.of(22, 21, 20, 19, 18, 17, 16, 15).stream().map(ye -> new Lottery("Norrkonst 20" + ye)).toList();
    }

    private List<Contestant> fakeContestants() {
        List<Contestant> contestants = new LinkedList<>();

        // IntStream
        // .range(0, 100)
        // .forEach(i -> {
        contestants.addAll(List.of(new Contestant("Alice Alisson", "00001", "070 - 0001 123", "email01@example.com"),
                new Contestant("Bob Bobsson", "00002", "070 - 0001 124", "email02@example.com"),
                new Contestant("Charlie Charlston", "00003", "070 - 0001 125", "email03@example.com"),
                new Contestant("Dorothea Dotesson", "00004", "070 - 0001 126", "email04@example.com"),
                new Contestant("Dorothea Abc", "00005", "070 - 0001 127", "email05@example.com"),
                new Contestant("Dorothea Foo", "00006", "070 - 0001 128", "email06@example.com"),
                new Contestant("Dorothea Bar", "00007", "070 - 0001 129", "email07@example.com"),
                new Contestant("Dorothea Oloffson", "00008", "070 - 0001 130", "email08@example.com"),
                new Contestant(getRandomName(), "00007", "070 - 0001 129", "email07@example.com"),
                new Contestant(getRandomName(), "00007", "070 - 0001 129", "email07@example.com"),
                new Contestant(getRandomName(), "00007", "070 - 0001 129", "email07@example.com"),
                new Contestant(getRandomName(), "00007", "070 - 0001 129", "email07@example.com"),
                new Contestant(getRandomName(), "00007", "070 - 0001 129", "email07@example.com"),
                new Contestant(getRandomName(), "00007", "070 - 0001 129", "email07@example.com"),
                new Contestant(getRandomName(), "00007", "070 - 0001 129", "email07@example.com"),
                new Contestant(getRandomName(), "00007", "070 - 0001 129", "email07@example.com")));
        // });
        return contestants;
    }

    private List<LotteryItem> fakeLotteryItems(List<Lottery> lotteries) {
        return List.of(
                new LotteryItem("", "Guernica", "Picasso", "10x10m", "wood", "999.999.999kr", "oil", lotteries.get(1)),
                new LotteryItem("", "The burning giraffe", "Dali", "10x10m", "wood", "999.999.999kr", "oil",
                        lotteries.get(0)),
                new LotteryItem("", "View of Toledo", "El Greco", "10x10m", "wood", "999.999.999kr", "oil",
                        lotteries.get(2)),
                new LotteryItem("", "David", "Michael Angelo", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "Mikael Blomqkvist", "Michael Angelo", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "Mona lisa", "Da vinci", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "Mikael Abc", "Artist Artisson", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "Mikael Foo", "Artist Artisson", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "Johanna Abc", "Artist Artisson", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "Ylva Ylvesson", "Artist Artisson", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "Eleonora Abc", "Artist Artisson", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 1", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 2", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 2", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 3", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 4", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 3", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 4", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 5", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 6", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", getRandomName(), "Artist Artisson 7", "10x10m", "wood", "999.999.999kr", "oil"));
    }

    /*
     * Called by Spring when running the CommandLineRunner above. We need to save the items to the database to get a
     * valid id from SQL so we copy the mockdata-photos after we've gotten an id...
     */
    private int lastMockId = 0;
    private final int numFakePhotos = 16;

    private void updatePictureUrl(LotteryItem item, LotteryItemRepository repo) {
        try {
            String photoPath = mockPhotosSrc + "/" + lastMockId++ % numFakePhotos + ".jpg";
            System.out.println("Using photo: " + photoPath);
            Path src = Paths.get(photoPath);
            photoService.savePhoto(item.getId(), new FileInputStream(src.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Mock data failed to be created");
            System.exit(1);
        }
    }

    private final Random rand = new Random();

    private String getRandomName() {
        String fname = randFirstNames[rand.nextInt(randFirstNames.length - 1)];
        String lname = randLastNames[rand.nextInt(randLastNames.length - 1)];
        return fname + " " + lname;
    }

    private final String[] randLastNames = { "Seivertsen", "Conquer", "Stoop", "Gatch", "Eck", "Iddon", "Forst",
            "Bernette", "Ambrus", "Winfield", "Coughtrey", "Beadel", "Bonney", "Longcake", "MacEveley", "Sey",
            "Headford", "Le Surf", "Tree", "Vicent", "Lampens", "Devall", "Need", "Norquay", "Mioni", "Moreside",
            "Lehemann", "Bilborough", "Claxson", "Conti", "McAlpine", "Ferschke", "Tretwell", "Davioud", "Hallwell",
            "McGonagle", "Freda", "Palphramand", "Shreve", "Mardell", "Scholard", "De Banke", "Feifer", "Dinan",
            "O'Conor", "Minshull", "Faulconer", "Burnand", "Bellenger", "Hamfleet", "Stathor", "Pykett", "Makiver",
            "Loffill", "Schankelborg", "Fryd", "Helling", "Laflin", "Walasik", "Voase", "Quilleash", "Cleever",
            "Fillgate", "Dibdale", "O'Cooney", "Ogers", "Flecknell", "Shermar", "Gonnin", "Connell", "Patient",
            "Fassum", "Skeats", "Jolliff", "Govett", "Chavrin", "Trevascus", "Norcutt", "Zimmermeister", "Cluelow",
            "Binnie", "Wensley", "Kerwin", "Kubicek", "Southan", "Whitefoot", "Elizabeth", "Bowne", "Baggelley",
            "Chiplen", "Ordish", "Rois", "Chrestien", "Suatt", "Seppey", "Asquez", "Ible", "Casford", "Spurrett",
            "De Maria", };

    private final String[] randFirstNames = { "Remington", "Vaughan", "Kira", "Arnie", "Dawna", "Claudian", "Cal",
            "Giovanna", "Katey", "Fleming", "Lillian", "Chaddie", "Wilmar", "Gwen", "Gerhardine", "Adrienne", "Dur",
            "Krispin", "Roarke", "Kandy", "Bird", "Betteann", "Janot", "Myrle", "Antoinette", "Charline", "Etienne",
            "Adamo", "Chicky", "Elga", "Shaylynn", "Therine", "Janessa", "Yvon", "Anna-diana", "Trever", "Philly",
            "Lorrin", "Jillian", "Brook", "Joshuah", "Angie", "Chrystel", "Diane-marie", "Tammie", "Dieter", "Eberto",
            "Sheilakathryn", "Wyatan", "Joey", "Nicola", "Yvon", "Gerianne", "Cal", "Charisse", "Hobie", "Trixy",
            "Bendite", "Gabie", "Elena", "Sean", "Kessiah", "Jorgan", "Nydia", "Sybilla", "Indira", "Kala", "Rogers",
            "Michell", "Rebe", "Tiertza", "Orton", "Gan", "Rodrick", "Ephraim", "Stanford", "Paddie", "Antoni",
            "Marnia", "Amerigo", "Felicle", "Germana", "Marney", "Odette", "Terrence", "Rudolf", "Harcourt", "Theressa",
            "Philippa", "Johnathan", "Colby", "Odille", "Johan", "Dolley", "Jefferson", "Zia", "Caitrin", "Angeline",
            "Winn", "Morley", };
}
