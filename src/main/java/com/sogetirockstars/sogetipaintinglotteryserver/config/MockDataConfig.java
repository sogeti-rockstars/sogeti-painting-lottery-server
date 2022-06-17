package com.sogetirockstars.sogetipaintinglotteryserver.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.sogetirockstars.sogetipaintinglotteryserver.exception.PhotoWriteException;
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
import com.sogetirockstars.sogetipaintinglotteryserver.service.ServiceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockDataConfig {
    @Value("${mockdata.create}")
    private boolean createMockdata;
    @Value("${mockdata.mock-photos-path}")
    private String mockPhotosSrc;

    private final PhotoService photoService;
    private List<Contestant> contestants;
    private List<Lottery> lotteries;
    private final Random rand = new Random();

    @Autowired
    public MockDataConfig(PhotoService photoService, LotteryService lotteryService) {
        this.photoService = photoService;
    }

    @Bean
    CommandLineRunner mockData(ServiceManager serviceManager, ContestantRepository contRepo, LotteryItemRepository lottItemsRepo, LotteryRepository lotteryRepo,
            WinnerRepository winnerRepo, AssociationInfoRepository infoRepo) {
        if (!createMockdata)
            return (String[] args) -> {
            };

        return (String[] args) -> {
            infoRepo.save(new AssociationInfo("title", "Om föreningen"));
            infoRepo.saveAndFlush(new AssociationInfo("info",
                    "För att vara med hör av dig till Ylva på ylva@sogeti.se, Medlemskap kostar 50kr i månaden som kommer dras av din lön."
                            + "Vi är den bästa föreningen för konst och inom konstbaserade saker, utmärkt CSR och non-profit förening för "
                            + "alla människors lika värde och nytta."));

            contestants = fakeContestants(50);
            lotteries = fakeLotteries();

            lotteryRepo.saveAll(lotteries);
            contRepo.saveAll(contestants);

            for (int i = 0; i < lotteries.size() - 1; i++) {
                Lottery curLottery = lotteries.get(i);
                List<LotteryItem> curItems = fakeLotteryItems();

                for (int u = 0; u <= lotteries.size() - i; u++) {
                    LotteryItem curItem = curItems.get(u);

                    Winner nWinner = serviceManager.addWinner(contestants.get(u).getId(), curLottery, u + 1);
                    curLottery.getWinners().add(nWinner);
                    lotteryRepo.saveAndFlush(curLottery);

                    curItem.setLottery(curLottery);
                    lottItemsRepo.save(curItem);
                    updatePictureUrl(curItem);
                    curLottery.addLotteryItems(curItem);
                    lotteryRepo.save(curLottery);

                }
            }

            winnerRepo.flush();
            contRepo.flush();
            lotteryRepo.flush();
            lottItemsRepo.flush();
            infoRepo.flush();
        };
    }

    private List<Lottery> fakeLotteries() {
        return List.of(22, 21, 20, 19, 18, 17, 16, 15).stream().map(ye -> new Lottery("Norrkonst 20" + ye)).toList();
    }

    private List<Contestant> fakeContestants(int length) {
        List<Contestant> contestants = new LinkedList<>();

        IntStream.range(0, length).forEach(i -> contestants.add(gnrtRandomEmployee()));

        return contestants;
    }

    private List<LotteryItem> fakeLotteryItems() {
        return List.of(new LotteryItem("", "Guernica", "Picasso", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "The burning giraffe", "Dali", "10x10m", "wood", "999.999.999kr", "oil"),
                new LotteryItem("", "View of Toledo", "El Greco", "10x10m", "wood", "999.999.999kr", "oil"),
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
     * Called by Spring when running the CommandLineRunner above. We need to save the items to the database to get a valid id from SQL so we copy the
     * mockdata-photos after we've gotten an id...
     */
    private int lastMockId = 0;
    private final int numFakePhotos = 16;

    private void updatePictureUrl(LotteryItem item) {
        try {
            String photoPath = mockPhotosSrc + "/" + lastMockId++ % numFakePhotos + ".jpg";
            Path src = Paths.get(photoPath);
            photoService.savePhoto(item.getId(), new FileInputStream(src.toFile()));
        } catch (IOException | PhotoWriteException e) {
            e.printStackTrace();
            System.err.println("Mock data failed to be created");
            System.exit(1);
        }
    }

    private Contestant gnrtRandomEmployee() {
        String name = getRandomName();
        return new Contestant(name, getRandomEmployeeId(), getRandomTeleNr(), name + "@example.com", "Sundvall");
    }

    private String getRandomEmployeeId() {
        Integer randPart1 = rand.nextInt(1000000);
        return String.format("%06d", randPart1);
    }

    private String getRandomTeleNr() {
        Integer randPart1 = rand.nextInt(10000);
        Integer randPart2 = rand.nextInt(1000);
        return String.format("070-%04d-%03d", randPart1, randPart2);

    }

    private String getRandomName() {
        String fname = randFirstNames[rand.nextInt(randFirstNames.length - 1)];
        String lname = randLastNames[rand.nextInt(randLastNames.length - 1)];
        return fname + " " + lname;
    }

    private final String[] randLastNames = { "Seivertsen", "Conquer", "Stoop", "Gatch", "Eck", "Iddon", "Forst", "Bernette", "Ambrus", "Winfield", "Coughtrey",
            "Beadel", "Bonney", "Longcake", "MacEveley", "Sey", "Headford", "Le Surf", "Tree", "Vicent", "Lampens", "Devall", "Need", "Norquay", "Mioni",
            "Moreside", "Lehemann", "Bilborough", "Claxson", "Conti", "McAlpine", "Ferschke", "Tretwell", "Davioud", "Hallwell", "McGonagle", "Freda",
            "Palphramand", "Shreve", "Mardell", "Scholard", "De Banke", "Feifer", "Dinan", "O'Conor", "Minshull", "Faulconer", "Burnand", "Bellenger",
            "Hamfleet", "Stathor", "Pykett", "Makiver", "Loffill", "Schankelborg", "Fryd", "Helling", "Laflin", "Walasik", "Voase", "Quilleash", "Cleever",
            "Fillgate", "Dibdale", "O'Cooney", "Ogers", "Flecknell", "Shermar", "Gonnin", "Connell", "Patient", "Fassum", "Skeats", "Jolliff", "Govett",
            "Chavrin", "Trevascus", "Norcutt", "Zimmermeister", "Cluelow", "Binnie", "Wensley", "Kerwin", "Kubicek", "Southan", "Whitefoot", "Elizabeth",
            "Bowne", "Baggelley", "Chiplen", "Ordish", "Rois", "Chrestien", "Suatt", "Seppey", "Asquez", "Ible", "Casford", "Spurrett", "De Maria", };

    private final String[] randFirstNames = { "Remington", "Vaughan", "Kira", "Arnie", "Dawna", "Claudian", "Cal", "Giovanna", "Katey", "Fleming", "Lillian",
            "Chaddie", "Wilmar", "Gwen", "Gerhardine", "Adrienne", "Dur", "Krispin", "Roarke", "Kandy", "Bird", "Betteann", "Janot", "Myrle", "Antoinette",
            "Charline", "Etienne", "Adamo", "Chicky", "Elga", "Shaylynn", "Therine", "Janessa", "Yvon", "Anna-diana", "Trever", "Philly", "Lorrin", "Jillian",
            "Brook", "Joshuah", "Angie", "Chrystel", "Diane-marie", "Tammie", "Dieter", "Eberto", "Sheilakathryn", "Wyatan", "Joey", "Nicola", "Yvon",
            "Gerianne", "Cal", "Charisse", "Hobie", "Trixy", "Bendite", "Gabie", "Elena", "Sean", "Kessiah", "Jorgan", "Nydia", "Sybilla", "Indira", "Kala",
            "Rogers", "Michell", "Rebe", "Tiertza", "Orton", "Gan", "Rodrick", "Ephraim", "Stanford", "Paddie", "Antoni", "Marnia", "Amerigo", "Felicle",
            "Germana", "Marney", "Odette", "Terrence", "Rudolf", "Harcourt", "Theressa", "Philippa", "Johnathan", "Colby", "Odille", "Johan", "Dolley",
            "Jefferson", "Zia", "Caitrin", "Angeline", "Winn", "Morley", };
}
