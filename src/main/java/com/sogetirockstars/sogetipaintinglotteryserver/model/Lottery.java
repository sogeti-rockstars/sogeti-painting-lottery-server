package com.sogetirockstars.sogetipaintinglotteryserver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Lottery")
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "lottery")
    private Long id;

    @OneToMany(mappedBy = "lottery")
    private List<LotteryItem> lotteryItems = new ArrayList<>();

    //Det blir oändliga loopar av två klasser som refererar till varandra
    //och man kan inte lägga @JsonBackReference på Collections.
    //Rätt sätt att göra detta är att göra "JSON view profiles"
    //men det orkar jag inte just nu... Kolla på länken för mer info
    //https://stackoverflow.com/questions/67886252/spring-boot-jpa-infinite-loop-many-to-many
    @ManyToMany(mappedBy = "lotteries")
    private List<Contestant> contestants = new ArrayList<>();

    @OneToMany(mappedBy = "lottery")
    private List<Winner> winners = new ArrayList<>();

    private Date date;
    private String title;

    public List<Contestant> getContestants() {
        return contestants;
    }

    public List<LotteryItem> getLotteryItems() {
        return lotteryItems;
    }

    public Lottery() {
    }


    public List<Winner> getWinners() {
        return winners;
    }

    public void setWinners(List<Winner> winners) {
        this.winners = winners;
    }

    public void addWinners(Winner winner) {
        this.winners.add(winner);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Lottery(String title) {
        this.title = title;
    }

    public Lottery(String title, List<Winner> winners) {
        this.title = title;
        this.winners = winners;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContestants(List<Contestant> contestants) {
        this.contestants = contestants;
    }

    public void setLotteryItems(List<LotteryItem> lotteryItems) {
        this.lotteryItems = lotteryItems;
    }

    public void addContestants(Contestant contestant) {
        this.contestants.add(contestant);
    }

    public void addLotteryItems(LotteryItem lotteryItem) {
        this.lotteryItems.add(lotteryItem);
    }
}
