package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Lottery")
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "lottery")
    private List<LotteryItem> lotteryItems = new ArrayList<>();

    @OneToMany(mappedBy = "lottery")
    private List<Contestant> contestants = new ArrayList<>();

    @OneToMany(mappedBy = "lottery")
    private List<Winner> winners = new ArrayList<>();

    private Date date;
    private String title;

    @JsonManagedReference
    public List<Contestant> getContestants() {
        return contestants;
    }

    @JsonManagedReference
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Lottery(String title) {
        this.title = title;
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
