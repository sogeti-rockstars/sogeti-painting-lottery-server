package com.sogetirockstars.sogetipaintinglotteryserver.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Lottery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<LotteryItem> lotteryItems = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Contestant> contestants = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Winner> winners = new ArrayList<>();

    private Date date;
    private String title;

    public Lottery(String title) {
        this.title = title;
    }

    // Used to create a lottery summary list.
    public Lottery(Long id, String title, Date date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public Lottery(String title, List<Winner> winners) {
        this.title = title;
        this.winners = winners;
    }

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
