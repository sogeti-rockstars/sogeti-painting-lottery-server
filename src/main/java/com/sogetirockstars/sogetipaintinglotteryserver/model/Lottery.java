package com.sogetirockstars.sogetipaintinglotteryserver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "lottery")
    private List<LotteryItem> lotteryItems = new ArrayList<>();

    @OneToMany(mappedBy = "lottery")
    private List<Winner> winners = new ArrayList<>();
    private String title;

    public List<LotteryItem> getLotteryItems() {
        return lotteryItems;
    }

    public Lottery() {
    }

    public Lottery(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Lottery(String title) {
        this.title = title;
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

    public void setLotteryItems(List<LotteryItem> lotteryItems) {
        this.lotteryItems = lotteryItems;
    }

    public void addLotteryItems(LotteryItem lotteryItem) {
        this.lotteryItems.add(lotteryItem);
    }

    @Override
    public String toString() {
        return "[title=" + (title != null ? title : "null") + ",id=" + (id != null ? id : "null") + "]";
    }
}
