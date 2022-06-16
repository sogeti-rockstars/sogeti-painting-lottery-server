package com.sogetirockstars.sogetipaintinglotteryserver.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "lottery", cascade = CascadeType.REMOVE)
    private Set<LotteryItem> lotteryItems = new HashSet<>();

    @OneToOne
    private LotteryItem guaranteePrize;

    @OneToMany(mappedBy = "lottery", cascade = CascadeType.REMOVE)
    private Set<Winner> winners = new HashSet<>();

    private String title;

    public Lottery() {
    }

    public Lottery(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Lottery(String title) {
        this.title = title;
    }

    public Set<Winner> getWinners() {
        return winners;
    }

    public void setWinners(Set<Winner> winners) {
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

    public void setLotteryItems(Set<LotteryItem> lotteryItems) {
        this.lotteryItems = lotteryItems;
    }

    public Set<LotteryItem> getLotteryItems() {
        return lotteryItems;
    }

    public void addLotteryItems(LotteryItem lotteryItem) {
        this.lotteryItems.add(lotteryItem);
    }

    public LotteryItem getGuaranteePrize() {
        return guaranteePrize;
    }

    public void setGuaranteePrize(LotteryItem guaranteePrize) {
        this.guaranteePrize = guaranteePrize;
    }

    @Override
    public String toString() {
        return "[" + "title=" + (title != null ? title : "null") + " ,id=" + (id != null ? id : "null") + " ,lotteryItems.size()=" + lotteryItems.size()
                + " ,winners.size()=" + winners.size() + "]";
    }
}
