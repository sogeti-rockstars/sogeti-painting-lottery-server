package com.sogetirockstars.sogetipaintinglotteryserver.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "lotteryItems")
    private Collection<LotteryItem> lotteryItems = new ArrayList<>();

    @OneToMany(mappedBy = "lotteryContestants")
    @JsonManagedReference
    private Collection<Contestant> contestants = new ArrayList<>();


    private String title;


    public Collection<Contestant> getContestants() {
        return contestants;
    }

    public Collection<LotteryItem> getLotteryItems() {
        return lotteryItems;
    }

    public Lottery() {
    }

    public Lottery(String title, Collection<Contestant> contestants, Collection<LotteryItem> lotteryItems) {
        this.title = title;
        this.contestants = contestants;
        this.lotteryItems = lotteryItems;
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

    public void setContestants(Collection<Contestant> contestants) {
        this.contestants = contestants;
    }

    public void setLotteryItems(Collection<LotteryItem> lotteryItems) {
        this.lotteryItems = lotteryItems;
    }

    public void addContestants(Contestant contestant) {
        this.contestants.add(contestant);
    }

    public void addLotteryItems(LotteryItem lotteryItem) {
        this.lotteryItems.add(lotteryItem);
    }
}
